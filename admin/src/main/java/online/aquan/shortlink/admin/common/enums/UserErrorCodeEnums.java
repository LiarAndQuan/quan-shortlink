package online.aquan.shortlink.admin.common.enums;

import online.aquan.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnums implements IErrorCode {
    
    USER_NULL("B00200","用户记录不存在");
    
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
