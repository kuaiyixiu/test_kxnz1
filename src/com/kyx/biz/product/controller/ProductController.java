package com.kyx.biz.product.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductChange;
import com.kyx.biz.product.model.ProductClass;
import com.kyx.biz.product.model.ProductRepertory;
import com.kyx.biz.product.service.ProductAllocationService;
import com.kyx.biz.product.service.ProductChangeService;
import com.kyx.biz.product.service.ProductClassService;
import com.kyx.biz.product.service.ProductRepertoryService;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.product.vo.ProductAllocationVo;
/**
 * 
 * @author tyg
 * @data 2019-4-18 下午3:40:47
 * @Descripton
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController{
	@Resource
	private ProductService productService;
	@Resource
	private ProductClassService productClassService;
	@Resource
	private ProductChangeService productChangeService;
	@Resource
	private ShopsService shopsService;
	@Resource
	private ProductRepertoryService productRepertoryService;
	@Resource
	private ProductAllocationService productAllocationService;
	
    
	@RequestMapping ("/infolist")
	public String infoList(){
		return "config/productlist";
	}
	
	@RequestMapping(value="/getProductList")
    @ResponseBody
    public String getProductList(HttpServletRequest request,Product product) {
		product.setShopId(getShopId(request));
        return JSON.toJSONString(productService.getProductList(product));
    }
	
	/**
	 *获取会员产品价格 
	 */
	@RequestMapping(value="/getProductCustomPriceList")
	@ResponseBody
	public String getProductCustomPriceList(HttpServletRequest request,Product product) {
		product.setShopId(getShopId(request));
		return JSON.toJSONString(productService.getProductCustomPrice(product));
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(Product product,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	product.setShopId(shops.getId());
    	GrdData result=productService.getInfo(product,pager);
        return JSON.toJSONString(result);
    }
    
    
    @RequestMapping("/addData")
    public String addRole(Model model,HttpSession session){
    	List<ProductClass> proList=productClassService.getAll(session);
    	model.addAttribute("classList", proList);
    	return "config/addproduct";
    }
    @RequestMapping("/editData")
    public String editData(Model model,Integer id,HttpSession session){
    	List<ProductClass> proList=productClassService.getAll(session);
    	model.addAttribute("classList", proList);
    	Product product=productService.getById(id);
    	model.addAttribute("product", product);
    	return "config/addproduct";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(Product product,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	product.setShopId(shops.getId());
    	RetInfo retInfo=productService.saveData(product);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 删除数据
     * @param role
     * @return
     */
    @RequestMapping("/delData")
    @ResponseBody
    public String delData(String ids) {
    	RetInfo retInfo=productService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    
	@RequestMapping(value="/getProductInfo")
    @ResponseBody
    public String getProductInfo(Integer id) {
        return JSON.toJSONString(productService.getById(id));
    }
	
	/**
	 * 库存查询
	 * @return
	 */
	@RequestMapping ("/stockInfo")
	public String productiInfo(Model model,HttpSession session){
		User user=(User) session.getAttribute(User.USER_SESSION);
		model.addAttribute("user", user);
		if(user.getBossAccount()==1){
			Shops shops = (Shops) session.getAttribute("shopsSession");
			model.addAttribute("shops", shops);
			String dbName = shops.getDbName();
			List<Shops> shopList=shopsService.getShops().get(dbName);
			model.addAttribute("shopList", shopList);
		}
		return "stock/stockinfo";
	}
	
    @RequestMapping(value="/getStockList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getStockList(Product product,Pager pager,HttpSession session) {
//    	Shops shops=(Shops) session.getAttribute("shopsSession");
//    	product.setShopId(shops.getId());
    	GrdData result=productService.getInfo(product,pager);
        return JSON.toJSONString(result);
    }
    /**
     * 查看流水
     * @param model
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/detailData")
    public String detailData(Model model,Integer id,HttpSession session){
    	Product product=productService.getById(id);
    	model.addAttribute("product", product);
    	return "stock/productdetail";
    }
    
    @RequestMapping(value="/getDetailList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getDetailList(ProductChange productChange,Pager pager,HttpSession session) {
    	GrdData result=productChangeService.getInfo(productChange,pager);
        return JSON.toJSONString(result);
    }
    /**
     * 库存调拨
     * @param model
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/allocateData")
    public String allocateData(Model model,Integer id,HttpSession session){
    	Product product=productService.getById(id);
    	model.addAttribute("product", product); 
		Shops shops = (Shops) session.getAttribute("shopsSession");
		model.addAttribute("shops", shops);
		String dbName = shops.getDbName();
		List<Shops> shopList=shopsService.getShops().get(dbName);
		model.addAttribute("shopList", shopList);
    	return "stock/productallocation";
    }
    
    /**
     * 获取调拨产品数据
     * @param productRepertory
     * @param pager
     * @return
     */
    @RequestMapping(value="/getAllocateList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getAllocateList(ProductRepertory productRepertory,Pager pager) {
    	GrdData result=productRepertoryService.getInfo(productRepertory, pager);
        return JSON.toJSONString(result);
    }
    
    /**
     * 校验产品在相关门店中是否包含此产品
     * @param product
     * @param session
     * @return
     */
    @RequestMapping(value="/checkExist",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String checkExist(Product product,HttpSession session) {
    	RetInfo retInfo=productService.checkExist(product);
        return AppUtils.getReturnInfo(retInfo);
    }
    /**
     * 跳转到相关产品
     * @param productId
     * @param relationShop
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/relationProduct")
    public String relationProduct(Integer productId,Integer relationShop,Model model,HttpSession session){
    	Product product=productService.getById(productId);
    	model.addAttribute("product", product);
    	Product pro=new Product();
    	pro.setShopId(relationShop);
    	List<Product> list=productService.getProductList(pro);
    	model.addAttribute("list", list);
    	return "stock/relationproduct";
    }
    
    /**
     * 存档信息
     * @param grdData
     * @param session
     * @return
     */
    @RequestMapping("/saveAllocationInfo")
    @ResponseBody
    public String saveAllocationInfo(ProductAllocationVo productAllocationVo,HttpSession session) {
    	RetInfo retInfo=productAllocationService.saveAllocationInfo(productAllocationVo,session);
		return  AppUtils.getReturnInfo(retInfo);
    }
	 
    /**
     * 库存预警
     * @return
     */
	@RequestMapping ("/previewinfo")
	public String previewinfo(){
		return "stock/previewinfo";
	}
	/**
	 * 库存预警
	 * @param product
	 * @return
	 */
    @RequestMapping(value="/getPreviewList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getPreviewList(Product product,Pager pager,HttpServletRequest request) {
    	product.setShopId(getShopId(request));
    	GrdData result=productService.getPreviewList(product,pager);
        return JSON.toJSONString(result);
    }

}
