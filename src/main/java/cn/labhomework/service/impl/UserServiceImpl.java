package cn.labhomework.service.impl;

import cn.labhomework.dao.StudentDOMapper;
import cn.labhomework.dataobject.StudentDO;
import cn.labhomework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentDOMapper studentDOMapper;


    @Override
    public StudentDO getUserByName(String name) {
        StudentDO studentDO =  studentDOMapper.selectByName(name);
        if (studentDO == null) {
            return null;
        }
        return studentDO;
    }

    @Override
    public void updatePassword(StudentDO studentDO) {
        studentDOMapper.updateByPrimaryKey(studentDO);
    }

    @Override
    public void registerStudent(StudentDO studentDO) {
        studentDOMapper.insert(studentDO);
    }
}
