/*
 * FileName：ImgResourceCtrl.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.kyx.biz.wechatpublic.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.mp.api.WxMpService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.wechatpublic.model.ImgResource;
import com.kyx.biz.wechatpublic.service.ImgResourceService;
import com.kyx.biz.wxutil.PropertiesUtil;

/**
 * 
 * @author 严大灯
 * @data 2019-7-31 上午8:56:17
 * @Descripton
 */
@Controller
@RequestMapping("managerImg")
public class ImgResourceController extends BaseController {

	@Resource
	private ImgResourceService imgResourceService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {

		return "wechatpublic/materiallist";
	}

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(ImgResource imgResource,Pager pager) {
    	GrdData grdData=imgResourceService.getImgListByPage(imgResource,pager);
    	return JSON.toJSONString(grdData);
    }

	/**
	 * 上传图片(富文本)
	 *
	 * @return
	 */
   
	@ResponseBody
	@RequestMapping("uploadFile")
	public Map uploadFile(MultipartFile file,HttpServletRequest request) throws Exception {
		//原文件名称
		String trueName = file.getOriginalFilename();
		//文件后缀名
		String ext = FilenameUtils.getExtension(trueName);

		//系统生成的文件名
		String fileName = file.getOriginalFilename();
		fileName = System.currentTimeMillis() + new Random().nextInt(10000) + "." + ext;
		//文件上传路径
//				String resURL = PropertiesUtil.getString("res.upload.url").toString();
		String filePath = request.getSession().getServletContext().getRealPath("/");

		//读取配置文上传件的路径
		filePath = filePath + "/upload/" + fileName;

		File saveFile = new File(filePath);

		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		file.transferTo(saveFile);
		//构造返回参数
		Map<String, Object> map = new HashMap();
		Map<String, Object> mapData = new HashMap();
		map.put("code", 0);//0表示成功，1失败
		map.put("msg", "上传成功");//提示消息
		map.put("data", mapData);//提示消息
		mapData.put("src","upload/"+fileName);//图片url
		mapData.put("title", fileName);//图片名称，这个会显示在输入框里
		return map;
	}
	
	/**
	 *  删除图片
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("delMediaImg")
	public String delMediaImg(Integer id,HttpServletRequest request)throws Exception {
		ImgResource img = imgResourceService.getImg(id);
		WxMpService wxService=getWxMapService(request);
		wxService.getMaterialService().materialDelete(img.getMediaId());
		int count=imgResourceService.delImg(id);
		RetInfo ret=RetInfo.Error("删除失败");
		if(count>0)
			ret=RetInfo.Success("删除成功");
		return AppUtils.getReturnInfo(ret);
	}
}
