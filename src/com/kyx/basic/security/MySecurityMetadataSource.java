package com.kyx.basic.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.kyx.basic.db.Dbs;
import com.kyx.basic.resource.service.ResourceService;

/**
 * 加载资源与权限的对应关系
 * 
 * @author 严大灯
 * @data 2019-3-28 下午4:45:28
 * @Descripton
 */
@Service("securityMetadataSource")
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	@Resource
	private ResourceService resourceService;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * @PostConstruct是Java EE 5引入的注解， Spring允许开发者在受管Bean中使用它。当DI容器实例化当前受管Bean时，
	 * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，
	 * 
	 *                                        //加载所有资源与权限的关系
	 */
	// @PostConstruct
	private void loadResourceDefine() {
		// System.err.println("-----------MySecurityMetadataSource loadResourceDefine ----------- ");
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			// 第一步切换主账号去查询资源表
			System.out.println(Dbs.getMainDbName());
			// Dbs.setDbName(Dbs.getMainDbName());
			List<com.kyx.basic.resource.model.Resource> resources = this.resourceService
					.findAll();
			for (com.kyx.basic.resource.model.Resource resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				// TODO: 通过资源名称来表示具体的权限 注意：必须"ROLE_"开头
				// 关联代码：applicationContext-security.xml
				// 关联代码：MyUserDetailServiceImpl#obtionGrantedAuthorities
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_"
						+ resource.getResKey());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getResUrl(), configAttributes);
			}
		}
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// System.err.println("-----------MySecurityMetadataSource getAttributes ----------- ");
		FilterInvocation invocation = ((FilterInvocation) object);
		String requestUrl = invocation.getRequestUrl();
		// System.out.println("requestUrl is " + requestUrl);
		HttpSession session = invocation.getRequest().getSession(true);
		if (session.getAttribute("currentDBName") != null)
			return null;
		if (resourceMap == null) {
			loadResourceDefine();
		}
		// System.err.println("resourceMap.get(requestUrl); "+resourceMap.get(requestUrl));
		if (requestUrl.indexOf("?") > -1) {
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		}
		Collection<ConfigAttribute> configAttributes = resourceMap
				.get(requestUrl);
		// if(configAttributes == null){
		// Collection<ConfigAttribute> returnCollection = new
		// ArrayList<ConfigAttribute>();
		// returnCollection.add(new SecurityConfig("ROLE_NO_USER"));
		// return returnCollection;
		// }
		return configAttributes;
	}

	public void loadResource() {
		resourceMap = null;
		loadResourceDefine();
	}

	public void setNullResourceMap() {
		resourceMap = null;
	}

}