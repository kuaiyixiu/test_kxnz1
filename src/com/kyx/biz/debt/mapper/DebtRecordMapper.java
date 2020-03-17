package com.kyx.biz.debt.mapper;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.debt.model.DebtRecord;
@Repository("debtRecordMapper")
public interface DebtRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DebtRecord record);

    DebtRecord selectByPrimaryKey(Integer id);
    
    DebtRecord selectBySourceIdAndKind(@Param("sourceId")Integer sourceId,@Param("kind")Integer kind);

    int updateByPrimaryKeySelective(DebtRecord record);

    int updateByPrimaryKey(DebtRecord record);

	List<DebtRecord> getInfo(DebtRecord debtRecord);

	BigDecimal getSumDebtAmount(DebtRecord debtRecord);
}