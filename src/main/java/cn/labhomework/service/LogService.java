package cn.labhomework.service;

import cn.labhomework.dataobject.LoginLogDO;

import java.util.Date;
import java.util.List;

public interface LogService {

    void insertLoginLog(Date loginTime, int relateId);
    List<LoginLogDO> selectLoginLogByStudentName(String name);
}
