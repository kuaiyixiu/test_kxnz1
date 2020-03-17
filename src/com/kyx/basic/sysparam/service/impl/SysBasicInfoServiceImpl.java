package com.kyx.basic.sysparam.service.impl;

import com.kyx.basic.sysparam.mapper.SysBasicInfoMapper;
import com.kyx.basic.sysparam.model.SysBasicInfo;
import com.kyx.basic.sysparam.service.SysBasicInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sysBasicInfoService")
public class SysBasicInfoServiceImpl implements SysBasicInfoService {
    @Resource
    private SysBasicInfoMapper sysBasicInfoMapper;

    @Override
    public boolean insertSelective(SysBasicInfo record) {
        return sysBasicInfoMapper.insertSelective(record) > 0;
    }

    @Override
    public SysBasicInfo selectByPrimaryKey(Integer id) {
        return sysBasicInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(SysBasicInfo record) {
        return sysBasicInfoMapper.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public boolean deleteByPrimaryKey(Integer id) {
        return sysBasicInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<SysBasicInfo> queryBasicInfosByFlag(int flag) {
        return sysBasicInfoMapper.queryBasicInfosByFlag(flag);
    }

    @Override
    public SysBasicInfo queryBasicInfo(int flag, String sign) {
        return sysBasicInfoMapper.queryBasicInfo(flag, sign);
    }
}
