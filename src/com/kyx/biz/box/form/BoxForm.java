package com.kyx.biz.box.form;

public class BoxForm {
	private String deviceId;

	private String data;

	private String command;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return "deviceId=" + deviceId + ",data=" + data + ",command=" + command;
	}

}
