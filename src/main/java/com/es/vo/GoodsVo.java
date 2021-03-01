package com.es.vo;

import lombok.Data;

@Data
public class GoodsVo{


    public final static String TITLE_ = "title";

    private String id;
    private String title;
    private String content;
    private String name;
    private String author;
    private String last_version;

}
