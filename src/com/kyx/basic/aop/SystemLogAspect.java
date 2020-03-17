package com.kyx.basic.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.fifter.XssHttpServletRequestWrapper;
import com.kyx.basic.log.model.Log;
import com.kyx.basic.log.service.LogService;
import com.kyx.basic.util.AppUtils;

/**
 * 
 * @ClassName: SystemLogAspect
 * @Description: TODO(日志切点类)
 * @author tyg
 */
@Aspect
@Component
public class SystemLogAspect {
	
	@Resource
	private LogService logService;

	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	//所有controller
	@Pointcut("execution(* com.kyx..controller.*.*(..))")
	public void controllerAspect() {
		
	}
//	
//	//添加controller
//	@Pointcut("execution(* com.itonghui.web..controller..add*(..))")
//	public void addControllerAspect() {
//		
//	}
//	
//	//修改controller
//	@Pointcut("execution(* com.itonghui.web..controller..update*(..))")
//	public void updateControllerAspect() {
//		
//	}
//	
//	//删除controller
//	@Pointcut("execution(* com.itonghui.web..controller..delete*(..))")
//	public void deleteControllerAspect() {
//		
//	}
//	
//	@Pointcut("addControllerAspect() || updateControllerAspect() || deleteControllerAspect()")
//	public void addUpdateDeleteControllerAspect(){
//		
//	}
	
//	// Service层切点
//	@Pointcut("execution(* com.itonghui.biz.*.service.impl..*(..))")
//	public void serviceAspect() {
//
//	}  
	
	/**
	 * 
	 * @Title: doBefore 
	 * @Description: TODO(前置通知 用于拦截Controller层记录用户的操作) 
	 * @param @param joinPoint    切点 
	 * @return void    返回类型 
	 * @throws
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		operationLogsService = (OperationLogsService) SpringApplicationContextUtil.getBean("operationLogsService",com.itonghui.biz.operationlogs.service.OperationLogsService.class); 
		String params = ""; 
        try {
  
        	//*========控制台输出=========*//  
        	SystemControllerLog scl = getControllerMethodDescription(joinPoint);
        	String module="",description="",author="";
        	if (!ObjectUtils.isEmpty(scl)) {
            	//获取用户请求方法的参数并序列化为JSON格式字符串    
            	if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
            		for ( int i = 0; i < joinPoint.getArgs().length; i++) { 
            			Object obj = joinPoint.getArgs()[i];
            			if(obj != null && !(obj instanceof XssHttpServletRequestWrapper)){
            				params += JSON.toJSONString(obj) + ";" ;    
            			}
            		}    
            	}  
        		
            	module = scl.module();
            	description = scl.description();
        		Log log = new Log();
        		log.setModule(module);	
        		log.setDescription(description);
        		log.setClassName(joinPoint.getTarget().getClass().getName());
        		log.setMethodName(joinPoint.getSignature().getName());
        		log.setParams(params);
        		logService.saveLog(request, log);
			}
//			logger.info("请求方法:{} 模块描述:{} 对应的模块名称:{} 方法描述{} 作者是:{} 备注:{} 参数值:{}",
//					joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()",
//					logtype,
//					logconstants,
//					typename,
//					author,
//			//		SessionUtil.getLogMDC(request),
//					params);
		} catch (Exception e) {
            logger.error("异常信息:{}", e);  
		}  
	}
//	
//	/**
//	 * 
//	 * @Title: doAfterThrowing 
//	 * @Description: TODO(异常通知 用于拦截service层记录异常日志 ) 
//	 * @param @param joinPoint
//	 * @param @param e    设定文件 
//	 * @return void    返回类型 
//	 * @author liguoping@itonghui.org   
//	 * @date 2016-2-23 上午10:55:10 
//	 * @throws
//	 */
//     @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")  
//     public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {  
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
//        //读取session中的用户  
//        //获取请求ip  
//        String ip = request.getRemoteAddr();  
//        //获取用户请求方法的参数并序列化为JSON格式字符串  
//        String params = "";  
//         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {  
//             for ( int i = 0; i < joinPoint.getArgs().length; i++) {  
//                params += JSONUtil.toJson(joinPoint.getArgs()[i]) + ";";  
//            }  
//        }  
//         try {  
//              /*========控制台输出=========*/  
//			logger.error("方法描述:{} 异常方法:{} 异常代码:{} 异常信息:{} 参数:{} 请求IP:{} 请求人:{} 异常堆栈:",
//					getServiceMthodDescription(joinPoint), 
//					joinPoint.getTarget().getClass().getName() +"."+ joinPoint.getSignature().getName()+"()", 
//					e.getClass().getName(), e.getMessage(), params,ip,SessionUtil.getSessionStringValue(request,  Constant.SESSION_USER_NAME),e);
//        }  catch (Exception ex) {  
//            //记录本地异常日志  
//            logger.error("==异常通知异常==");  
//            logger.error("异常信息:{}", ex);  
//        }  
//         /*==========记录本地异常日志==========*/  
//  
//    }  
	
	/**
	 * 
	 * @Title: getControllerMethodDescription 
	 * @Description: TODO(获取注解中对方法的描述信息 用于Controller层注解 ) 
	 * @param @param joinPoint 切点
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return String    返回类型 
	 * @author liguoping@itonghui.org   
	 * @date 2016-2-23 上午10:02:57 
	 * @throws
	 */
    @SuppressWarnings("all")
	public static SystemControllerLog getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		SystemControllerLog slo =null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					slo = method.getAnnotation(SystemControllerLog.class);
					break;
				}
			}
		}
		return slo;
	}
    
//	/**
//	 * 
//	 * @Title: getServiceMthodDescription
//	 * @Description: TODO(获取注解中对方法的描述信息 用于service层注解 )
//	 * @param @param joinPoint 切点
//	 * @param @return
//	 * @param @throws Exception 设定文件
//	 * @return String 返回类型
//	 * @author liguoping@itonghui.org
//	 * @date 2016-2-23 上午10:50:27
//	 * @throws
//	 */
//	@SuppressWarnings("all")
//	public static String getServiceMthodDescription(JoinPoint joinPoint)
//			throws Exception {
//		String targetName = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] arguments = joinPoint.getArgs();
//		Class targetClass = Class.forName(targetName);
//		Method[] methods = targetClass.getMethods();
//		String description = "";
//		for (Method method : methods) {
//			if (method.getName().equals(methodName)) {
//				Class[] clazzs = method.getParameterTypes();
//				if (clazzs.length == arguments.length) {
//					SystemServiceLog slo = method.getAnnotation(SystemServiceLog.class);
//					if(!ObjectUtils.isNullObj(slo)){
//						description = slo.description();
//					}
//					break;
//				}
//			}
//		}
//		return description;
//	}  
	
//	/**
//	 * 
//	 * 方法描述:向OperationLogsVo中塞值
//	 *
//	 * @Author:tanghaifeng@itonghui.org
//	 * @date: 2014-12-15 下午4:25:58 
//	 * @param request 请求对象
//	 * @return operationLogsVo操作日志对象
//	 */
//	private static OperationLogsVo plugValue(HttpServletRequest request) {
//		OperationLogsVo operationLogsVo = new OperationLogsVo();
//		operationLogsVo.setCustId(SessionUtil.getSessionStringValue(request, Constant.SESSION_CUST_ID));
//		operationLogsVo.setCustName(SessionUtil.getSessionStringValue(request, Constant.SESSION_CUST_NAME));
//		operationLogsVo.setUserId(SessionUtil.getSessionStringValue(request, Constant.SESSION_USER_ID));
//		operationLogsVo.setUserName(SessionUtil.getSessionStringValue(request, Constant.SESSION_USER_NAME));
//		return operationLogsVo;
//	}
    

}
