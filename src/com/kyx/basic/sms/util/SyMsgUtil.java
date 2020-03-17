package com.kyx.basic.sms.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.sms.model.SmsSend;
import com.kyx.basic.util.ResourceUtil;

/**
 * 示远短信接口工具类
 * @author tyg
 *
 */
public class SyMsgUtil {
	
//	private static  String SMS_PLAT_TYPE = "qdTth452";
//	
//	private static  String password = "C3CW7ugK_k";
	
	private static String host = "https://platform.18sms.com/msg/send";
	
	/**
	 * 发送信息
	 * @param productType  发送短信
	 * @return 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static SmsSend sendMsg(String mobile,String content) throws ClientProtocolException, IOException{
		String configFileName = "sms.properties";
		String USER_NAME =  ResourceUtil.getConfigNoCache(configFileName,"USER_NAME"); 
		String PASSWORD =  ResourceUtil.getConfigNoCache(configFileName,"PASSWORD"); 
		content = content.replaceAll("【", "(").replaceAll("】", ")").replaceAll("\\[", "(").replaceAll("\\]", ")");
		CloseableHttpClient client = HttpClients.custom().build();
		HttpPost post = new HttpPost(host);
		post.addHeader("content-type", "application/json");
		JSONObject postData = new JSONObject();
		postData.put("user_name", USER_NAME);
		postData.put("password", PASSWORD);
		postData.put("send_model", 1); //用户普通短信发送接口
		postData.put("phone", mobile);
		postData.put("sms_detail", content);
		postData.put("product_type", 1); //通知类短信
		StringEntity param = new StringEntity(postData.toString(),StandardCharsets.UTF_8);
		param.setContentType("application/json");
		post.setEntity(param);
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity, "utf-8");
		
		JSONObject jo = JSONObject.parseObject(str);
		SmsSend message = new SmsSend();
		message.setCreateTime(new Date());
		message.setUpdateTime(message.getCreateTime());
		message.setContent(content);
		message.setMobile(mobile);
		message.setResponseContent(jo.getString("msg"));
		message.setMsgStatus(jo.getInteger("status"));
		message.setMsgId(jo.getString("msgid"));
		message.setPlatType(1);//示远
		return message;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		sendMsg("13024075608","尊敬的车主您好，呼呼呼呼呼");
		sendMsg("18155250406","尊敬的车主您好，吱吱吱吱吱吱吱吱吱吱做做做做做");
	}

}
