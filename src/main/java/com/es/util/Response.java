package com.es.util;

public class Response {
    private int code = 200;

    private String message = "";

    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static Response success(Object obj){
        Response r = new Response();
        r.setCode(200);
        r.setData(obj);
        return r;
    }

    public static Response success(String message, Object obj){
        Response r = new Response();
        r.setCode(200);
        r.setMessage(message);
        r.setData(obj);
        return r;
    }

    public static Response failure(String message, Object obj){
        Response r = new Response();
        r.setCode(-1);
        r.setMessage(message);
        r.setData(obj);
        return r;
    }

    public static Response failure(String message,int code, Object obj){
        Response r = new Response();
        r.setCode(code);
        r.setMessage(message);
        r.setData(obj);
        return r;
    }
}
