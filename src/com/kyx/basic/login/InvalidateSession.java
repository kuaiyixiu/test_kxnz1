package com.kyx.basic.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("public")
public class InvalidateSession {
	@RequestMapping(value = "/invalidate")
	@ResponseBody
	public String invalidateSession(HttpServletRequest reqeust,
			HttpServletResponse response) {
		System.out.println("Session失效");
		String ajaxHeader = reqeust.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
		if (isAjax) {
			return "invalidSession";
		} else {
			PrintWriter out = null;
			try {
				response.setContentType("text/html");  
	        	response.setCharacterEncoding("utf-8");  
	        	out = response.getWriter();    
	        	StringBuilder builder = new StringBuilder();    
	        	builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");    
	        	builder.append("top.location.href=\"login.do\";"); //返回到首页  
	         	builder.append("</script>");    
	        	out.print(builder.toString());    
//	        	out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(out!=null)
					out.close();
			}
		}
		

		return "";
	}

}
