package com.kyx.biz.recharge.service;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.recharge.model.Recharge;

public interface RechargeService {
    /**
     * 充值保存
     * @param recharge
     * @return
     */
	RetInfo saveRechargeData(Recharge recharge);
    /**
     * 分页查询充值记录
     * @param recharge
     * @param pager
     * @return
     */
	GrdData getList(Recharge recharge, Pager pager);

}
