package cn.labhomework.service;

import cn.labhomework.dataobject.StudentDO;

public interface UserService {

    StudentDO getUserByName(String name);

    void updatePassword(StudentDO studentDO);

}
