package com.es.enums;

public enum WhetherEnum {

    YES("1", "是"),
    NO("0", "否"),
    ;
    private String code ;
    private String msg ;

    WhetherEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
