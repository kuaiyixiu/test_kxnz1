package com.kyx.biz.wxutil;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class WechatUtil {
//	private static final String APPID = PropertiesUtil.getPropertiesValue("APPID");// 在基础配置中可查看自己APPID
//	private static final String APPSECRET = PropertiesUtil.getPropertiesValue("APPSECRET");// 在基础配置中可查看自己APPSECRET
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";// 获取token
	private static final String Send_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";// 推送信息

	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		System.out.println(jsonObject);
		return jsonObject;
	}
	
	public static JSONObject doPostStr(String url, Object obj) throws Exception {
		JSONObject jsonObject = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		StringEntity requestBody=new StringEntity(JSONObject.toJSONString(obj),Charset.forName("UTF-8"));
		httpPost.setEntity(requestBody);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			BasicManagedEntity entity = (BasicManagedEntity) response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("微信请求返回信息="+result);
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		System.out.println(jsonObject);
		return jsonObject;
	}

	/**
	 * 获取AccessToken
	 * @param APPSECRET 
	 * @param APPID 
	 * @Description: TODO 
	 * @param @return
	 * @return AccessToken
	 * @throws
	 * @author ydd
	 * @date 2017-12-18
	 */
	public static AccessToken getAccessToken(String APPID, String APPSECRET) throws Exception{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		System.out.println("获取tokenURL="+url);
		try {
			JSONObject jsonObject = doGetStr(url);
			System.out.println(jsonObject.toString());
			if (jsonObject != null) {
				token.setToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getIntValue("expires_in"));
			}
		} catch (Exception e) {
			System.out.println("获取token失败");
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 微信信息推送
	 * @param token
	 * @param mdlTemplate 
	 * 
	 * @param sendInfo
	 * @return
	 */
//	public static JSONObject sendPush(AccessToken token, MdlTemplate mdlTemplate) {
//		if (mdlTemplate == null) {
//			throw new IllegalStateException("mdlTemplate is null!");
//		}
//		String url = Send_URL.replace("ACCESS_TOKEN", token.getToken());
//		JSONObject jsonObj = null;
//		try {
//			jsonObj = doPostStr(url,mdlTemplate);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (jsonObj != null) {
//			System.out.print("SendTemplate返回json：" + jsonObj.toString());
//		}
//		return jsonObj;
//	}

}
