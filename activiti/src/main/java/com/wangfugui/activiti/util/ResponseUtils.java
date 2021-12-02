package com.wangfugui.activiti.util;

import com.wangfugui.apprentice.common.constant.enums.CodeEnums;
import lombok.Data;

@Data
public class ResponseUtils {
    private String code;
    private String msg;
    private Object data;


    public ResponseUtils() {

    }

    public ResponseUtils(Object data, CodeEnums codeEnums) {
        this.data = data;
        this.code = codeEnums.getCode();
        this.msg = codeEnums.getMsg();
    }

    public ResponseUtils(Object data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }


    public static ResponseUtils success() {
        return new ResponseUtils((Object)null, CodeEnums.SUCCESS_CODE);
    }

    public static ResponseUtils success(Object data) {
        return new ResponseUtils(data, CodeEnums.SUCCESS_CODE);
    }



    public static ResponseUtils error() {
        return new ResponseUtils((Object)null, CodeEnums.ERROR_CODE);
    }

    public static ResponseUtils error(Object data) {
        return new ResponseUtils(data, CodeEnums.ERROR_CODE);
    }



    public static ResponseUtils msg(String msg) {
        return new ResponseUtils((Object)null, CodeEnums.ERROR_MSG.getCode(), msg);
    }

    public static ResponseUtils notLogin() {
        return new ResponseUtils((Object)null, CodeEnums.NOT_LOGIN);
    }

    public static ResponseUtils noResult() {
        return new ResponseUtils((Object)null, CodeEnums.NO_RESULT);
    }

    public static ResponseUtils build(Object data, String code, String msg) {
        return new ResponseUtils(data, code, msg);
    }

    public static ResponseUtils build(Object data, CodeEnums codeEnums) {
        return new ResponseUtils(data, codeEnums.getCode(), codeEnums.getMsg());
    }

    public static ResponseUtils build(CodeEnums codeEnums) {
        return new ResponseUtils(null, codeEnums.getCode(), codeEnums.getMsg());
    }

    public static ResponseUtils build(String code, String msg) {
        return new ResponseUtils(null, code, msg);
    }

}
