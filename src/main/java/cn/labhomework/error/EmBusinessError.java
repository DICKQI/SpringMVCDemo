package cn.labhomework.error;

public enum EmBusinessError implements CommonError {
    // 2000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_HAS_EXIST(20005, "用户名已存在"),
    USER_PASSWORD_ERROR(20002, "密码错误"),
    IDNUMBER_ERROR(20003, "身份证号错误"),
    AUTH_ERROR(20004, "权限错误"),

    // 通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),
    // 日志相关错误
    UNFOUND_LOG(30001, "没有记录喔")
    ;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;
    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
