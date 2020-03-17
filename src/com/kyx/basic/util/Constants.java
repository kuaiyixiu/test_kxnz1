package com.kyx.basic.util;

import java.util.Arrays;
import java.util.List;

public class Constants {

	/**
	 * 图片资源状态：0：未引用	1：已被引用
	 */
	public static final Integer IMG_FLAG0 = 0;
	public static final Integer IMG_FLAG1 = 1;

	/**
	 * Token加密密钥
	 */
	public static final String TOKEN_KEY = "_GUyFjkigkjgUKgkjg_h$hjh^JKGGF4542";

	/********************************* 操作信息常量 ***********************************/
	/**
	 * 成功提示
	 **/
	public static final String MSG_SUCCESS = "信息操作成功!";

	/**
	 * 保存成功提示
	 **/
	public static final String MSG_SUCCESS_SAVE = "信息添加成功!";

	/**
	 * 修改成功提示
	 **/
	public static final String MSG_SUCCESS_UPDATE = "信息修改成功!";

	/**
	 * 删除成功提示
	 **/
	public static final String MSG_SUCCESS_DELETE = "信息删除成功!";

	/**
	 * 操作失败提示
	 **/
	public static final String MSG_ERROR = "服务器异常，操作失败!";

	/**
	 * 删除失败提示
	 **/
	public static final String MSG_ERROR_DELETE = "该节点含有子节点";

	/**
	 * 菜单类型-click
	 **/
	public static final String MENU_CLICK = "click";
	/**
	 * 菜单类型-view
	 **/
	public static final String MENU_VIEW = "view";
	/**
	 * 菜单类型-scancode_push
	 **/
	public static final String MENU_SCANCODE_PUSH = "scancode_push";
	/**
	 * 菜单类型-scancode_waitmsg
	 **/
	public static final String MENU_SCANCODE_WAITMSG = "scancode_waitmsg";
	/**
	 * 菜单类型-pic_sysphoto
	 **/
	public static final String MENU_PIC_SYSPHOTO = "pic_sysphoto";
	/**
	 * 菜单类型-pic_photo_or_album
	 **/
	public static final String MENU_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
	/**
	 * 菜单类型-pic_weixin
	 **/
	public static final String MENU_PIC_WEIXIN = "pic_weixin";
	/**
	 * 菜单类型-pic_location_selectn
	 **/
	public static final String MENU_LOCATION_SELECT = "location_select";
	/**
	 * 菜单类型-需要设置key的菜单类型
	 **/
	public static final List<String> MENU_NEED_KEY =  Arrays.asList("click", "scancode_push", "scancode_waitmsg", "pic_sysphoto", "pic_photo_or_album", "pic_weixin", "location_select");
	/**
	 * 图文类型-1单图文2多图
	 **/
	public static final Integer MULT_TYPE_SINGLE =  1;

	/**
	 * 图文类型-1单图文2多图
	 **/
	public static final Integer MULT_TYPE_MORE =  2;


}
