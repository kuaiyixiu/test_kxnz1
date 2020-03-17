package com.kyx.biz.wechatpublic.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;

public interface WechatCardRecordService {
    @Transactional
	int save(WechatCardRecord record);
    /**
     * 查询
     * @param wechatCardRecord
     * @param pager
     * @return
     */
	GrdData getInfo(WechatCardRecord wechatCardRecord, Pager pager);
	
	@Transactional
	RetInfo updateData(String ids, String voidRemark);
    
	/**
	 * 根据custid查询
	 * @param custId
	 * @return
	 */
	List<WechatCardRecord> selectByCustId(Integer custId);

	int selectCountByCustId(Integer custId);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	WechatCardRecord selectById(Integer id);
	
	 WechatCardRecord selectByPrimaryKey(Integer id);
	 
	 @Transactional
	 RetInfo updateWechatCardRecord(WechatCardRecord record);
	 
		
		/**
		 * 订单里提交选择的优惠卷
		 * @param couponId
		 * @param amount
		 * @param customId
		 * @param orderId
		 * @return
		 */
	 
	@Transactional
	RetInfo submitCardset(String cardsetIds, Integer custId,Integer orderId); 
		
}
