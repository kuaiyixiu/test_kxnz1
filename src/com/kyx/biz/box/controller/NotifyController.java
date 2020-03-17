package com.kyx.biz.box.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kyx.biz.box.form.BoxForm;

@RestController
@RequestMapping("/notify")
public class NotifyController {

	@RequestMapping("/callback")
	public String callback(HttpServletRequest httpServletRequest,
			@RequestBody BoxForm boxForm) {
		JSONObject json = new JSONObject();
		if ("login".equals(boxForm.getCommand())) {
			json.put("code", "1001");
			json.put("deviceId", boxForm.getData());
			json.put("content", "");
		} else {
			json.put("code", "1000");
			json.put("deviceId", boxForm.getDeviceId());
			json.put("content", "");
		}
		return json.toString();
	}

	/**
	 * BBC校验
	 * 
	 * @param data
	 *            传入16进制
	 * @return
	 */
	public static String getBCC(byte[] data) {

		String ret = "";
		byte BCC[] = new byte[1];
		for (int i = 0; i < data.length; i++) {
			BCC[0] = (byte) (BCC[0] ^ (byte) Integer.parseInt(data[i] + "", 16));
		}
		String hex = Integer.toHexString(BCC[0] & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		ret += hex.toUpperCase();
		return ret;
	}

	public static void main(String[] args) {
		byte[] data = { 80, 01, 01, 33 };

		System.out.println(getBCC(data));
		/*
		 * JSONObject json = new JSONObject(); json.put("code", "1000");
		 * json.put("deviceId", "9CA5259CCE61"); json.put("content", "");
		 */

	}
}
