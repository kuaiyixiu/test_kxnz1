package com.kyx.biz.royalty.mapper;

import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.royalty.model.Royalty;
@Repository("royaltyMapper")
public interface RoyaltyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Royalty record);

    int insertSelective(Royalty record);

    Royalty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Royalty record);

    int updateByPrimaryKey(Royalty record);

	List<Royalty> getInfo(Royalty royalty);
	
	List<Royalty> getList(Royalty royalty);

	//int deleteByShopId(Royalty royalty);
	
	int insertBatches(@Param("list") List<Royalty> royalties);

	Royalty getByRoyaltyId(Royalty ro);

	List<Royalty> getProInfo(Royalty royalty);
	/**
	 * 批量删除
	 * @param arr
	 * @return
	 */
	public boolean doRemoveeMore(Map map);
}