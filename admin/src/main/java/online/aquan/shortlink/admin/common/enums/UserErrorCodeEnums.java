package online.aquan.shortlink.admin.common.enums;

import online.aquan.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnums implements IErrorCode {

    USER_TOKEN_FAIL("A00200", "用户Token验证失败"),
    USER_NULL("B00200", "用户记录不存在"),

    USER_EXIST("B00201", "用户名已经存在"),
    USER_REGISTER_ERROR("B00202", "用户注册失败"),

    USER_HAS_LOGIN("B00203", "用户已登录"),
    USER_NOT_LOGIN("B00204", "用户还未登录");

    private final String code;
    private final String message;

    UserErrorCodeEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
