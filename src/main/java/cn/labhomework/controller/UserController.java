package cn.labhomework.controller;

import cn.labhomework.dataobject.LoginLogDO;
import cn.labhomework.dataobject.StudentDO;
import cn.labhomework.error.BusinessException;
import cn.labhomework.error.EmBusinessError;
import cn.labhomework.response.CommonReturnType;
import cn.labhomework.service.LogService;
import cn.labhomework.service.UserService;
import cn.labhomework.util.ConstantUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller("student")
@RequestMapping("/student")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private HttpServletRequest request;

    // 用户登录API
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType login(@RequestBody HashMap loginMap) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {


        if (loginMap.get("username").equals("") || loginMap.get("password").equals("") || loginMap.isEmpty()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

//        调用service服务获取对应id的用户对象返回给前端
        StudentDO studentDO = userService.getUserByName((String) loginMap.get("username"));

        if (studentDO == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        // 检查密码是否正确
        if (studentDO.getPassword().equals(EncodeBySha1((String) loginMap.get("password")))) {
            logService.insertLoginLog(new Date(), studentDO.getId());
            // 在服务器端记录session信息
            request.getSession().setAttribute(ConstantUtils.USER_SESSION_KEY, studentDO);
            return CommonReturnType.create("登录成功");
        } else {
            throw new BusinessException(EmBusinessError.USER_PASSWORD_ERROR);
        }
    }


    // 用户主页API(查看登录记录)
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType dashboard(@RequestParam(name = "name") String username) throws BusinessException {
        if (username.equals("")) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<LoginLogDO> loginLogDOList = logService.selectLoginLogByStudentName(username);
        if (loginLogDOList == null) {
            throw new BusinessException(EmBusinessError.UNFOUND_LOG);
        }
        return CommonReturnType.create(loginLogDOList);
    }

    // 修改密码API
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType changePassword(@RequestBody HashMap passwdMap) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (passwdMap.isEmpty() || passwdMap.get("newPassword").equals("") || passwdMap.get("IdNumber").equals("") || passwdMap.get("username").equals("")) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StudentDO studentDO = userService.getUserByName((String) passwdMap.get("username"));
        if (passwdMap.get("IdNumber").equals(studentDO.getIdnumber())) {
            // 身份证正确
            String password = EncodeBySha1((String) passwdMap.get("newPassword")); // 新密码加密
            studentDO.setPassword(password);
            userService.updatePassword(studentDO);
            return CommonReturnType.create("修改密码成功");
        } else {
            return CommonReturnType.create("身份证错误");
        }
    }


    // sha1编码验证函数
    private String EncodeBySha1(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest sha1 = MessageDigest.getInstance("Sha1");
        return Base64.encodeBase64String(sha1.digest(password.getBytes("utf-8")));
    }

}
