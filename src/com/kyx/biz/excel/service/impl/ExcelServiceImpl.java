package com.kyx.biz.excel.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.kyx.biz.car.mapper.CarMapper;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.excel.service.ExcelService;
import com.kyx.biz.product.mapper.ProductClassMapper;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductClass;
import com.kyx.biz.repertory.model.Repertory;
import com.kyx.biz.serve.mapper.ServeClassMapper;
import com.kyx.biz.serve.mapper.ServeMapper;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.model.ServeClass;
@Service("excelService")
public class ExcelServiceImpl implements ExcelService {
	@Resource
	private CustomMapper customMapper;
	@Resource
	private CarMapper carMapper;
	@Resource
	private ServeClassMapper serveClassMapper;
	@Resource
	private ServeMapper serveMapper;
	@Resource
	private ProductClassMapper productClassMapper;
	@Resource
	private ProductMapper productMapper;
	
	public Integer shopId=11;

	@Override
	public void saveCustom() {
		List<Map> list=readExcel();
		for(Map m:list){
			Custom custom=(Custom) m.get("custom");
			List<Car> carList=(List<Car>) m.get("car");
			customMapper.insertSelective(custom);
			for(Car car:carList){
				car.setCustomId(custom.getId());
				carMapper.insertSelective(car);
			}
			
		}
		
	}

	private List<Map> readExcel() {
		//excel文件路径
        String excelPath = "E:\\会员20190722100241.xls";
        List<Map> li=new ArrayList<Map>();
        try {
            //String encoding = "GBK";
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return li;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: "+firstRowIndex);
                System.out.println("lastRowIndex: "+lastRowIndex);
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    Custom custom=new Custom();
                    custom.setShopId(shopId);
                    List<Car> carList=new ArrayList<Car>(); 
                    Map map=new HashMap();
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                            	if(cIndex==0){
                            		 custom.setCustType(getType(cell.toString()));
                            		 System.out.println("会员类型："+cell.toString());
                            	}else if(cIndex==1){
                            		custom.setCardNo(cell.toString());
                            		System.out.println("会员卡号："+cell.toString());
                            	}else if(cIndex==2){
                            		custom.setCustName(cell.toString());
                            		System.out.println("会员姓名："+cell.toString());
                            	}else if(cIndex==3){
                            		custom.setCellphone(cell.toString());
                            		System.out.println("联系方式："+cell.toString());
                            	}else if(cIndex==4){
                            		custom.setBalance(new BigDecimal(cell.toString()));
                            		System.out.println("余额："+cell.toString());
                            	}else if(cIndex==5){
                            		if(StringUtils.isNotEmpty(cell.toString())){
                                		String[] carArr=cell.toString().split("\\|");
                                		for(String carNo:carArr){
                                			Car car=new Car();
                                			car.setCarNumber(carNo);
                                			car.setCarType(1);
                                			car.setOwnerName(custom.getCustName());
                                			car.setOwnerCellphone(custom.getCellphone());
                                			carList.add(car);
                                		}
                            		}
                            		System.out.println("车牌："+cell.toString());
                            	}
                               
                            }
                        }
                    }
                    map.put("custom", custom);
                    map.put("car", carList);
                    li.add(map);
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return li;
	}
	
	private Integer getType(String typeName) {
		if("到店开卡".equals(typeName))
			return 2;
		else if("自助开卡会员".equals(typeName))
			return 1;
		return null;
	}

	@Override
	public void saveServe() {
		List<Map> list=readServe();
		for(Map m:list){
			ServeClass serveClass=(ServeClass) m.get("serveClass");
			int count=serveClassMapper.getCountByName(serveClass);
			Serve serve=(Serve) m.get("serve");
			if(count>0){
				serveClass=serveClassMapper.selectByName(serveClass);
			}else{
				serveClassMapper.insertSelective(serveClass);
			}	
			serve.setClassId(serveClass.getId());
			serveMapper.insertSelective(serve);
			
		}
		
	}

	private List<Map> readServe() {
		//excel文件路径
        String excelPath = "E:\\服务项目20190723102945.xls";
        List<Map> li=new ArrayList<Map>();
        try {
            //String encoding = "GBK";
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return li;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: "+firstRowIndex);
                System.out.println("lastRowIndex: "+lastRowIndex);
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        ServeClass serveClass=new ServeClass();
                        serveClass.setShopId(shopId);
                        Serve serve=new Serve();
                        serve.setShopId(shopId);
                        Map map=new HashMap();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                            	if(cIndex==0){//服务分类名称
                            		 serveClass.setClassName(cell.toString());
                            		 System.out.println(cell.toString());
                            	}else if(cIndex==1){
                            		 serve.setServeName(cell.toString());
                            		 System.out.println(cell.toString());
                            	}else if(cIndex==2){
                            		serve.setRemark(cell.toString());
                            		System.out.println(cell.toString());
                            	}else if(cIndex==3){
                            		serve.setSz(Double.valueOf(cell.toString()).intValue());
                            		System.out.println(cell.toString());
                            	}else if(cIndex==4){
                            		serve.setPrice(new BigDecimal(cell.toString()));
                            		System.out.println(cell.toString());
                            	}else if(cIndex==5){
                            		serve.setSgtc(new BigDecimal(cell.toString()));
                            		System.out.println(cell.toString());
                            	}
                               
                            }
                        }
                        map.put("serveClass", serveClass);
                        map.put("serve", serve);
                        li.add(map);
                    }

                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return li;
	}

	@Override
	public void saveProduct() {
		List<Map> list=readProduct();
		for(Map m:list){
            ProductClass productClass=(ProductClass) m.get("productClass");
            Product product=(Product) m.get("product");
            ProductClass pro=productClassMapper.getInfoByName(productClass);
            if(pro!=null)//存在产品类别
            	productClass=pro;
            else
            	productClassMapper.insertSelective(productClass);
            product.setClassId(productClass.getId());
            product.setQuantity(0);
            productMapper.insertSelective(product);
            //产品数量大于0 重新生成采购单 
//            if(product.getQuantity()>0){
//            	
//            }
		}
		
	}

	private List<Map> readProduct() {
		//excel文件路径
        String excelPath = "E:\\库存产品20190723145848.xls";
        List<Map> li=new ArrayList<Map>();
        try {
            //String encoding = "GBK";
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return li;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: "+firstRowIndex);
                System.out.println("lastRowIndex: "+lastRowIndex);
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        ProductClass productClass=new ProductClass();
                        productClass.setShopId(shopId);
                        Product product=new Product();
                        product.setShopId(shopId);
                        Repertory repertory=new Repertory();
                        repertory.setShopId(shopId);
                        repertory.setKind(1);
                        Map map=new HashMap();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                            	if(cIndex==0){//服务分类名称
                            		productClass.setClassName(cell.toString());
                            		 System.out.println(cell.toString());
                            	}else if(cIndex==1){
                            		product.setProductName(cell.toString());
                            		 System.out.println(cell.toString());
                            	}else if(cIndex==2){
                            		product.setProductType(cell.toString());
                            		System.out.println(cell.toString());
                            	}else if(cIndex==3){
                            		product.setCarType(cell.toString());
                            		System.out.println(cell.toString());
                            	}else if(cIndex==4){
                            		product.setQuantity(new BigDecimal(cell.toString()).intValue());
                            		System.out.println(cell.toString());
                            	}else if(cIndex==5){
                            		product.setAmount(new BigDecimal(cell.toString()));
                            		System.out.println(cell.toString());
                            	}else if(cIndex==6){
                            		product.setPrice(new BigDecimal(cell.toString()));
                            		System.out.println(cell.toString());
                            	}else if(cIndex==7){
                            		repertory.setSupplyName(cell.toString());
                            		System.out.println(cell.toString());
                            	}
                               
                            }
                        }
                        map.put("productClass", productClass);
                        map.put("product", product);
                        map.put("repertory", repertory);
                        li.add(map);
                    }

                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return li;
	}

}
