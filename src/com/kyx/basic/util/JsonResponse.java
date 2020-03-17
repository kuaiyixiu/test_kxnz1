package com.kyx.basic.util;

/**
 * 返回app的json
 * 
 * @author daibin
 * @date 2019-4-29 上午11:22:55
 * 
 */
public class JsonResponse<T> {
	private boolean success = true;

	private T result;

	private String messages;

	public JsonResponse() {
	};

	public JsonResponse(boolean success) {
		this.success = true;
	}

	public JsonResponse(boolean success, T result) {
		this.success = success;
		this.result = result;
	}

	public JsonResponse(boolean success, String messages) {
		this.success = success;
		this.messages = messages;
	}

	public JsonResponse(boolean success, T result, String messages) {
		this.success = success;
		this.result = result;
		this.messages = messages;
	}

}
