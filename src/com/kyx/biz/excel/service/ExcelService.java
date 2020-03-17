package com.kyx.biz.excel.service;

public interface ExcelService {
    /**
     * 客户导入
     */
	void saveCustom();
    /**
     * 服务导入
     */
	void saveServe();
	/**
	 * 产品导入
	 */
	void saveProduct();

}
