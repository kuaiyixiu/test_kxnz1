package com.kyx.basic.fifter;

import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.kyx.basic.util.CommonUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

//	public XssHttpServletRequestWrapper(HttpServletRequest request) {
//		super(request);
//	}
//
//	@Override
//	public String[] getParameterValues(String name) {
//		String[] values = super.getParameterValues(name);
//		if (values == null) {
//			return null;
//		}
//		int count = values.length;
//		String[] encodedValues = new String[count];
//		for (int i = 0; i < count; i++) {
//			encodedValues[i] = cleanXSS(values[i]);
//		}
//		return encodedValues;
//	}
//
//	@Override
//	public String getParameter(String parameter) {
//		String value = super.getParameter(parameter);
//		if (value == null) {
//			return null;
//		}
//		return cleanXSS(value);
//	}
//
//	@Override
//	public String getHeader(String name) {
//		String value = super.getHeader(name);
//		if (value == null)
//			return null;
//		return cleanXSS(value);
//	}
//
//	private String cleanXSS(String value) {
//		return HtmlUtils.htmlEscape(value);
//		
////		// You'll need to remove the spaces from the html entities below

////		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");

////		return value;
//	}
	

    private HttpServletRequest orgRequest;
 
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }
 
    @Override
    public String getRequestURI(){
    	return CommonUtils.xssEncode(super.getRequestURI());
    }
    
    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(CommonUtils.xssEncode(name));
        if (value != null) {
            value = CommonUtils.xssEncode(value);
        }
        return value;
    }
    
    @SuppressWarnings("all")
	@Override
    public Enumeration getParameterNames(){
    	Vector l = new Vector(super.getParameterMap().keySet());
		return l.elements();
    }
    
    @Override
    public String[] getParameterValues(String name) {
    	String[] value = super.getParameterValues(CommonUtils.xssEncode(name));
    	if (value!=null&&value.length>=0) {
    		String[] temp = new String[value.length];
    		for (int i = 0; i < temp.length; i++) {
    			temp[i] = CommonUtils.xssEncode(value[i]);
			}
    		return temp;
		}else{
			return null;
		}
	}
    
    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(CommonUtils.xssEncode(name));
        if (value != null) {
            value = CommonUtils.xssEncode(value);
        }
        return value;
    }
 
   
 
    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }
 
    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }
    
	
}
