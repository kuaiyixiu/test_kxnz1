package com.kyx.biz.royalty.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.royalty.model.Royalty;

public interface RoyaltyService {

	GrdData getInfo(Royalty royalty, Pager pager);

	Royalty getById(Integer id);
	/**
	 * 存档
	 * @param royalty
	 * @param ids
	 * @param session
	 * @param statue "add"新增 "modify"修改
	 * @return
	 */
    @Transactional
	RetInfo saveData(Royalty royalty, String ids, HttpSession session, String statue);

	Royalty getByRoyaltyId(Royalty ro);

	List<Royalty> getList(Royalty royalty);
}
