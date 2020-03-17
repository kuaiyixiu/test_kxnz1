package com.kyx.basic.util;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kyx.basic.user.model.User;

public class VerifyUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(RSAUtil.class);
	  
	private static String privateKey = PropertiesUtil.getPropertiesValue("PRIVATE_KEY",1);
	
	private static String publicKey = PropertiesUtil.getPropertiesValue("PUBLIC_KEY",1);
	
	

	private static String[] attr={"signature"};
	
	/**
	 * 生成最终的参数string,传入RPC的参数中
	 */
	public static String assembleParamStr(Object vo){
		String json =  JSON.toJSONString(vo);
		Map map  = JSON.parseObject(json,TreeMap.class);
		//Map map  = JSON.parseObject(json, new TypeReference<Map<String, String>>() {});
		map.put("signature", VerifyUtils.generateSignature(map));	//签名
		return JSON.toJSONString(map);
	}
	
	/**
	 * 生成签名的方法
	 */
    public static String generateSignature(Map<String, String> requestMap) {
  //      System.out.println("开始生成签名");
        List<String> excudeKeylist = new ArrayList<String>();
        String[] excudeKey = new String[excudeKeylist.size()];
  //      TreeMap<String, String> treeMap = Digest.treeMap(requestMap, excudeKeylist.toArray(excudeKey));
   //     System.out.println("生成摘要的字符串：" + Digest.mapToString(treeMap));
        String digest = Digest.digest(requestMap, excudeKeylist.toArray(excudeKey));
  //      System.out.println("digest : " + digest);
        String sign = null;
        try {
            //sign = RSAUtil.sign(digest, RSAUtil.getPrivateKey(PRIVATE_KEY));
        	sign = RSAUtil.sign(digest, RSAUtil.getPrivateKey(privateKey));
        } catch (Throwable e) {
        	LOG.error("生成签名失败", e);
            e.printStackTrace();
        }
//        System.out.println("sign : " + sign);
        return sign;
    }
    /**
     * 验签
     * @param requestMap
     * @param signature
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verifySignature(Map<String, String> requestMap,String[] attrList, String signature, String publicKey) throws Exception {
   // 	System.out.println("开始生成易付宝通知摘要");
        List<String> excudeKeylist = new ArrayList<String>();
        if(attrList!=null&&attrList.length>0){
        	for(int i=0;i<attrList.length;i++){
        		excudeKeylist.add(attrList[i]);
        	}
        }
        String[] excudeKey = new String[excudeKeylist.size()];
  //      TreeMap<String, String> treeMap = Digest.treeMap(requestMap, excudeKeylist.toArray(excudeKey));
  //      System.out.println("生成摘要的字符串：" + Digest.mapToString(treeMap));
        String signData = Digest.digest(requestMap, excudeKeylist.toArray(excudeKey));
   //     System.out.println("digest : " + signData);
    	// 参数检查
        if (signData == null || signData.length() == 0) {
            throw new Exception("原数据字符串为空");
        }
        if (signature == null || signature.length() == 0) {
            throw new Exception("签名字符串为空");
        }
        if (publicKey == null || publicKey.length() == 0) {
            throw new Exception("公钥字符串为空");
        }
        try {
            // 获取根据公钥字符串获取私钥
            PublicKey pubKey = RSAUtil.getPublicKey(publicKey);
            // 返回验证结果
            return RSAUtil.vertiy(signData, signature, pubKey);
        } catch (Exception e) {
            throw new Exception("验证签名异常", e);
        }
 }
    
    /**
     * 
     * 功能描述: <br>
     * base 64 编码
     * 
     * @param str
     * @return
     * @since 20150325
     */
//    public static String base64Encode(String str) {
//        byte[] encodeBase64 = null;
//        String result = null;
//        if (com.alibaba.druid.util.StringUtils.isEmpty(str)) {
//            return str;
//        }
//        try {
//            encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
//            result = new String(encodeBase64, "UTF-8");
//        } catch (Throwable e) {
//            System.err.println("Base64失败");
//        }
//
//        return result;
//    }
    /**
     * 解码
     * @param str
     * @return
     */
//    public static String base64Decode(String str) {
//        byte[] encodeBase64 = null;
//        String result = null;
//        if (StringUtils.isEmpty(str)) {
//            return str;
//        }
//        try {
//            encodeBase64 = Base64.decodeBase64(str.getBytes("UTF-8"));
//            result = new String(encodeBase64, "UTF-8");
//        } catch (Throwable e) {
//            System.err.println("Base64失败");
//        }
//
//        return result;
//    }
    
//    public static Map getMap(NameValuePair[] meta_list){
//    	Map map=new HashMap();
//    	for(int i=0;i<meta_list.length;i++){
//    		NameValuePair nameValuePair=meta_list[i];
//    		if(nameValuePair!=null)
//    			map.put(nameValuePair.getName(), nameValuePair.getValue());
//    	}
//		return map;
//    	
//    }
    
//    @SuppressWarnings("rawtypes")
//	public static Map<String, String> getParameterMap(HttpServletRequest request) {  
//        // 参数Map  
//        Map<?, ?> properties = request.getParameterMap();  
//        // 返回值Map  
//        Map<String, String> returnMap = new HashMap<String, String>();  
//        Iterator<?> entries = properties.entrySet().iterator();  
//		Map.Entry entry;  
//        String name = "";  
//        String value = "";  
//        while (entries.hasNext()) {  
//            entry = (Map.Entry) entries.next();  
//            name = (String) entry.getKey();  
//            Object valueObj = entry.getValue();  
//            if(null == valueObj){  
//                value = "";  
//            }else if(valueObj instanceof String[]){  
//                String[] values = (String[])valueObj;  
//                for(int i=0;i<values.length;i++){  
//                    value = values[i] + ",";  
//                }  
//                value = value.substring(0, value.length()-1);  
//            }else{  
//                value = valueObj.toString();  
//            }  
//            returnMap.put(name, value);  
//        }  
//        return returnMap;  
//    }
    
    public static Map jsonToMap(String jsonStr) throws Exception {
    	Map map  = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {});
//		JSONObject jsonObj = new JSONObject(jsonStr);
//		Iterator<String> nameItr = jsonObj.keys();
//		String name;
//		Map<String, String> outMap = new HashMap<String, String>();
//		while (nameItr.hasNext()) {
//			name = nameItr.next();
//			outMap.put(name, jsonObj.getString(name));
//		}
		return map;
	}
    
	public static Boolean verifySignature(String jsonStr) throws Exception{
		if(StringUtils.isEmpty(jsonStr)) return false;
		Map<String, String> map =  jsonToMap(jsonStr);
		return verifySignature(map, attr, map.get("signature"),publicKey);
	}

    
    
    public static void main(String[] args) throws Exception {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("k1", "v1");
    	String str = assembleParamStr(map);
    	if(verifySignature(str)){
    		User u1 = JSON.parseObject(str,User.class);
    		System.out.println(u1.getRelationPhone());
		}else
			System.out.println("111");	
	}

}
