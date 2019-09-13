package cn.labhomework.service.impl;

import cn.labhomework.dao.LoginLogDOMapper;
import cn.labhomework.dao.StudentDOMapper;
import cn.labhomework.dataobject.LoginLogDO;
import cn.labhomework.dataobject.StudentDO;
import cn.labhomework.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LoginLogDOMapper loginLogDOMapper;

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Override
    public void insertLoginLog(Date loginTime, int relateId) {
        LoginLogDO loginLogDO = new LoginLogDO();
        loginLogDO.setDate(loginTime);
        loginLogDO.setRelatestuid(relateId);

        loginLogDOMapper.insert(loginLogDO);
    }

    @Override
    public List<LoginLogDO> selectLoginLogByStudentName(String name) {
        StudentDO studentDO = studentDOMapper.selectByName(name);

        List<LoginLogDO> loginLogDOList = loginLogDOMapper.selectByRelateId(studentDO.getId());

        if (loginLogDOList.size() == 0) {
            return null;
        }

        return loginLogDOList;
    }
}
