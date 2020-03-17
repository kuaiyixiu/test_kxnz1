package com.kyx.biz.wechatpublic.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialArticleUpdate;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews.WxMpMaterialNewsArticle;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customType.model.CustomType;
import com.kyx.biz.customType.service.CustomTypeService;
import com.kyx.biz.wechatpublic.model.GroupSend;
import com.kyx.biz.wechatpublic.model.MsgArticle;
import com.kyx.biz.wechatpublic.model.MsgNews;
import com.kyx.biz.wechatpublic.service.GroupSendService;
import com.kyx.biz.wechatpublic.service.MsgArticleService;
import com.kyx.biz.wechatpublic.service.MsgNewsService;

@Controller
@RequestMapping("wechatMessage")
public class WechatMessageController extends BaseController {

  @Resource
  private GroupSendService groupSendService;

  @Resource
  private MsgNewsService msgNewsService;

  @Resource
  private MsgArticleService articleService;

  @Resource
  private CustomTypeService customTypeService;

  @Resource
  private CustomService customService;


  @RequestMapping("/groupsendlist")
  public String groupsendlist(Model model) {
    return "wechatpublic/groupsendlist";
  }

  @RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
  @ResponseBody
  public String getList(HttpServletRequest request, GroupSend groupSend, Pager pager) {
    GrdData result = groupSendService.getList(groupSend, pager);
    return JSON.toJSONString(result);
  }



  @RequestMapping("groupSend")
  public String groupSend(HttpServletRequest request, Model model) throws Exception {
    WxMpService wxService = getWxMapService(request);
    List<WxUserTag> tagList = wxService.getUserTagService().tagGet();
    model.addAttribute("tagList", tagList);
    int fansCount = groupSendService.getAllFansCount(request);
    model.addAttribute("fansCount", fansCount);
    Shops shops = getShops(request);
    if (shops.getWechatId().intValue() == 1) {
      model.addAttribute("flag", "isTrue");
    } else {
      model.addAttribute("flag", "isFalse");
    }
    return "wechatpublic/groupsend";
  }



  @RequestMapping(value = "/sendMsg")
  @ResponseBody
  // @SystemControllerLog(module = "订单", description = "更改订单状态")
  public String sendMsg(HttpServletRequest request, GroupSend groupSend) throws Exception {
    RetInfo retInfo = groupSendService.sendMsg(request, groupSend);
    return AppUtils.getReturnInfo(retInfo);
  }



  /**
   * 图文管理列表
   * 
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("msgNewsList")
  public String msgNewsList(HttpServletRequest request, Model model) {
    return "wechatpublic/msgnewslist";
  }

  @RequestMapping("addmultiple")
  public String addmultiple(HttpServletRequest request, Model model) {
    return "wechatpublic/addmultiple";
  }

  @RequestMapping("editmultiple/{id}")
  public String editmultiple(HttpServletRequest request, Model model, @PathVariable("id") Integer id) {
    model.addAttribute("id", id);
    return "wechatpublic/editmultiple";
  }

  /**
   * 添加多图文
   * 
   * @param rows
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/addMoreNews", method = RequestMethod.POST)
  @ResponseBody
  public RetInfo addMoreNews(String rows, HttpServletRequest request) throws Exception {
    WxMpService wxService = getWxMapService(request);
    WxMpMaterialNews wxNews = new WxMpMaterialNews();
    MsgNews msgNew = new MsgNews();
    List<MsgArticle> listArts = new ArrayList<MsgArticle>();// 数据库所有图文集合
    String filePath = request.getSession().getServletContext().getRealPath("/");
    JSONArray arrays = JSONArray.parseArray(rows);
    for (int i = 0; i < arrays.size(); i++) {
      JSONObject obj = arrays.getJSONObject(i);
      WxMpMaterialNews.WxMpMaterialNewsArticle article =
          new WxMpMaterialNews.WxMpMaterialNewsArticle();
      article.setAuthor(obj.getString("author") == null ? "" : obj.getString("author"));
      article.setThumbMediaId(obj.getString("thumbMediaId") == null ? "" : obj
          .getString("thumbMediaId"));
      article.setTitle(obj.getString("title"));
      article.setContentSourceUrl(obj.getString("contentSourceUrl") == null ? "" : obj
          .getString("contentSourceUrl"));
      article.setDigest(obj.getString("digest") == null ? "" : obj.getString("digest"));
      article.setShowCoverPic(obj.getIntValue("showCoverPic") == 1);
      article.setNeedOpenComment(obj.getIntValue("needOpenComment") == 1);
      article.setOnlyFansCanComment(obj.getIntValue("onlyFansCanComment") == 1);

      MsgArticle art = new MsgArticle();
      art.setNewsIndex(i);
      art.setAuthor(article.getAuthor());
      art.setThumbMediaId(article.getThumbMediaId());
      art.setTitle(article.getTitle());
      art.setContentSourceUrl(article.getContentSourceUrl());
      art.setDigest(article.getDigest());
      art.setShowCoverPic(obj.getIntValue("showCoverPic"));
      art.setContent(obj.getString("content") == null ? "" : obj.getString("content"));
      art.setNeedOpenComment(obj.getIntValue("needOpenComment"));
      art.setOnlyFansCanComment(obj.getIntValue("onlyFansCanComment"));
      art.setPicUrl(obj.getString("picUrl") == null ? "" : obj.getString("picUrl"));
      if (i == 0) {
        msgNew.setAuthor(article.getAuthor());
        msgNew.setDigest(article.getDigest());
        msgNew.setDescription(article.getContent());
        msgNew.setFromurl(article.getContentSourceUrl());
        msgNew.setMultType(2);
        msgNew.setPicpath(obj.getString("picpath") == null ? "" : obj.getString("picpath"));
        msgNew.setShowpic(obj.getIntValue("showpic"));
        msgNew.setTitle(article.getTitle());
        msgNew.setThumbMediaId(article.getThumbMediaId());
      }

      // 注意这是图文正文部分
      String content = obj.getString("content") == null ? "" : obj.getString("content");
      content = content.replaceAll("'", "\"");

      // 去多个img的src值
      String subFilePath = "";
      String subOldFilePath = "";
      if (content.contains("img")) {
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
        Matcher m = p.matcher(content);

        while (m.find()) {
          String imgSrc = m.group(1);
          subOldFilePath += imgSrc + ",";
          String[] split = imgSrc.split("/");
          int k = imgSrc.indexOf(split[split.length - 2]);
          String subImgSrc = imgSrc.substring(k, imgSrc.length());
          subFilePath += filePath + subImgSrc + ",";
        }
      }
      if (StringUtils.isNotBlank(subFilePath)) {
        subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
        subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

        // 本地图片地址
        String[] imgPathArry = subFilePath.split(",");
        String[] imgOldPathArry = subOldFilePath.split(",");

        String[] newPathArry = new String[imgPathArry.length];
        for (int j = 0; j < imgPathArry.length; j++) {
          File saveFile = new File(imgPathArry[j]);
          WxMediaImgUploadResult res = wxService.getMaterialService().mediaImgUpload(saveFile);
          // 将图片上传到微信，返回url
          String wechatUrl = res.getUrl();
          newPathArry[j] = wechatUrl;
        }

        for (int j = 0; j < imgPathArry.length; j++) {
          content = content.replace(imgOldPathArry[j], newPathArry[j]);
        }
      }
      article.setContent(content);
      wxNews.addArticle(article);
      listArts.add(art);
    }
    msgNew.setArticles(listArts);
    WxMpMaterialUploadResult result = null;
    try {
      result = wxService.getMaterialService().materialNewsUpload(wxNews);
      msgNew.setMediaId(result.getMediaId());
    } catch (WxErrorException e) {
      if (StringUtils.isNotBlank(e.getMessage())) {
        JSONObject jb = JSONObject.parseObject(e.getMessage());
        String errCode = jb.getString("errcode");
        if ("88000".equals(errCode)) {
          return RetInfo.Error("公众号没有留言权限，请关闭留言功能");
        }
      }
    }

    WxMpMaterialNews wxMpMaterialNewsMultiple =
        wxService.getMaterialService().materialNewsInfo(result.getMediaId());
    List<WxMpMaterialNewsArticle> wxArtList = wxMpMaterialNewsMultiple.getArticles();
    for (int i = 0; i < wxArtList.size(); i++) {
      WxMpMaterialNewsArticle wxArt = wxArtList.get(i);
      MsgArticle ma = listArts.get(i);
      ma.setMediaId(result.getMediaId());
      ma.setUrl(wxArt.getUrl());
      if (i == 0) {
        msgNew.setUrl(wxArt.getUrl());
      }
    }

    RetInfo retInfo = msgNewsService.addMoreNews(msgNew);
    return retInfo;
  }

  @RequestMapping(value = "/list")
  @ResponseBody
  public String list(MsgNews searchEntity, Pager pager) {
    GrdData grdData = msgNewsService.getWebNewsListByPage(searchEntity, pager);
    return JSON.toJSONString(grdData);
  }

  @RequestMapping(value = "/toUpdateMoreNews")
  @ResponseBody
  public String toUpdateMoreNews(String id) {
    MsgNews newsObj = msgNewsService.getById(id);
    RetInfo retInfo = new RetInfo(RetInfo.SUCCESS, newsObj);
    return JSON.toJSONString(retInfo);
  }

  /**
   * 更新多图文
   * 
   * @param rows
   * @param request
   * @return
   */

  @RequestMapping(value = "/updateSubMoreNews", method = RequestMethod.POST)
  @ResponseBody
  public RetInfo updateMoreNews(String rows, HttpServletRequest request) throws Exception {
    WxMpService wxService = getWxMapService(request);
    String filePath = request.getSession().getServletContext().getRealPath("/");
    MsgArticle art = (MsgArticle) JSONObject.parseObject(rows, MsgArticle.class);
    String content = art.getContent();
    content = content.replaceAll("'", "\"");
    // 去多个img的src值
    String subFilePath = "";
    String subOldFilePath = "";
    if (content.contains("img")) {
      Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
      Matcher m = p.matcher(content);

      while (m.find()) {
        String imgSrc = m.group(1);
        subOldFilePath += imgSrc + ",";
        String[] split = imgSrc.split("/");
        int k = imgSrc.indexOf(split[split.length - 2]);
        String subImgSrc = imgSrc.substring(k, imgSrc.length());
        subFilePath += filePath + subImgSrc + ",";
      }
    }
    if (StringUtils.isNotBlank(subFilePath)) {

      subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
      subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

      // 本地图片地址
      String[] imgPathArry = subFilePath.split(",");
      String[] imgOldPathArry = subOldFilePath.split(",");

      String[] newPathArry = new String[imgPathArry.length];
      for (int i = 0; i < imgPathArry.length; i++) {
        String newFilePath = imgPathArry[i];
        // 添加永久图片
        File saveFile = new File(newFilePath);
        WxMediaImgUploadResult res = wxService.getMaterialService().mediaImgUpload(saveFile);
        String wechatUrl = res.getUrl();
        newPathArry[i] = wechatUrl;
      }

      for (int i = 0; i < imgPathArry.length; i++) {
        content = content.replace(imgOldPathArry[i], newPathArry[i]);
      }
    }
    // 内容保存
    WxMpMaterialNews.WxMpMaterialNewsArticle article =
        new WxMpMaterialNews.WxMpMaterialNewsArticle();

    // 上传图片素材
    article.setThumbMediaId(art.getThumbMediaId());
    if (art.getAuthor() != null) {
      article.setAuthor(art.getAuthor());
    } else {
      article.setAuthor("");
    }
    if (art.getTitle() != null) {
      article.setTitle(art.getTitle());
    } else {
      article.setTitle("");
    }
    if (art.getContentSourceUrl() != null) {
      article.setContentSourceUrl(art.getContentSourceUrl());
    } else {
      article.setContentSourceUrl("");
    }
    if (art.getDigest() != null) {
      article.setDigest(art.getDigest());
    } else {
      article.setDigest("");
    }
    if (art.getShowCoverPic() != null) {
      article.setShowCoverPic(art.getShowCoverPic() == 1);
    } else {
      article.setShowCoverPic(false);
    }

    if (art.getDigest() != null) {
      article.setDigest(art.getDigest());
    } else {
      article.setDigest("");
    }

    article.setNeedOpenComment(art.getNeedOpenComment() == 1);
    article.setOnlyFansCanComment(art.getOnlyFansCanComment() == 1);
    article.setContent(content);

    WxMpMaterialArticleUpdate wxMpMaterialArticleUpdateMulti = new WxMpMaterialArticleUpdate();
    wxMpMaterialArticleUpdateMulti.setMediaId(art.getMediaId());
    wxMpMaterialArticleUpdateMulti.setArticles(article);
    wxMpMaterialArticleUpdateMulti.setIndex(art.getNewsIndex());

    boolean resultMulti =
        wxService.getMaterialService().materialNewsUpdate(wxMpMaterialArticleUpdateMulti);

    if (resultMulti) {
      // article.setContent(description2);
      // 更新成功
      articleService.update(art);
      // 修改图文news表数据
      if (art.getNewsIndex() == 0) {
        MsgNews msgNews = new MsgNews();
        msgNews.setId(art.getNewsId());
        msgNews.setTitle(art.getTitle());
        msgNewsService.updateTitle(msgNews);
      }

      return RetInfo.Success("");
    } else {
      return RetInfo.Error("");
    }

  }


  /**
   * 删除永久图文素材
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "/deleteMaterial", method = RequestMethod.POST)
  @ResponseBody
  public RetInfo deleteMaterial(String id, HttpServletRequest request) throws WxErrorException {
    MsgNews news = msgNewsService.getById(id);
    WxMpService wxService = getWxMapService(request);
    boolean flag = wxService.getMaterialService().materialDelete(news.getMediaId());
    if (flag) {
      this.msgNewsService.delete(news.getId());
      return RetInfo.Success("");
    }
    return RetInfo.Error("");
  }


  /**
   * 选择图片
   * 
   * @param request
   * @param model
   * @return
   */
  @RequestMapping("/choosemateriallist")
  public String choosemateriallist(HttpServletRequest request, Model model) {
    return "wechatpublic/choosemateriallist";
  }

  /**
   * 选择图文
   * 
   * @param request
   * @param model
   * @return
   */
  @RequestMapping("/choosenewslist")
  public String choosenewslist(HttpServletRequest request, Model model) {
    return "wechatpublic/choosenewslist";
  }

  /**
   * 选择用户列表
   * 
   * @param request
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/choosecustomlist")
  public String choosecustomlist(HttpServletRequest request, Model model) {
    model.addAttribute("shopName", getShopName(request));
    List<CustomType> customTypes = customTypeService.selectCustomTypes(null);
    model.addAttribute("customTypes", JSON.toJSONString(customTypes));
    model.addAttribute("customTypeList", customTypes);
    model.addAttribute("now", new Date());
    return "wechatpublic/choosecustomlist";
  }


  @RequestMapping(value = "/queryCustoms")
  @ResponseBody
  public String queryCustoms(Custom custom, Pager pager) {
    custom.setNotNullOpenId(1);
    GrdData result = customService.queryCustoms(custom, pager);
    return JSON.toJSONString(result);
  }


}
