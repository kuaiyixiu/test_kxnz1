package com.kyx.biz.custom.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.report.vo.ReportQueryVo;

@Repository("customMapper")
public interface CustomMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Custom record);

	int insertSelective(Custom record);

	Custom selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Custom record);

	int updateByPrimaryKey(Custom record);

	List<Custom> queryCustoms(Custom custom);

	Custom selectByCardNo(@Param("cardNo") String cardNo);

	/**
	 * 逻辑删除会员信息
	 * 
	 * @param id
	 * @return
	 */
	int delCustomById(Integer id);
	
	/**
	 * 查出会员卡号
	 * @param id
	 * @return
	 */
	String selectCardNoById(Integer id);
    /**
     * 根据会员卡号更新
     * @param custom
     * @return
     */
	int updateByCardNo(Custom custom);
    /**
     * 
     * @param startDate
     * @param endDate
     * @return
     */
	List<Custom> getInfo(Custom custom);
    /**
     * 获取当前门店未消费会员数量
     * @param reportQueryVo
     * @return
     */
	int getLossRep(ReportQueryVo reportQueryVo);
    /**
     * 查询会员总数量 
     * @return
     */
	int getCount();

  /**
   * 查询会员卡的信息和车牌
   * 
   * @param custom
   * @return
   */
  List<Custom> queryCustomAndCar(Custom custom);
}