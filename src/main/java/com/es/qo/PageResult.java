package com.es.qo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页数据
 * @param <T>
 */
@Setter@Getter
public class PageResult<T> extends QueryObject {

    //具体的数据
    private List<T> data;
    //总数据量
    private Integer totalCount;
    //总页数
    private Integer totalPage;
    //上一页
    private Integer prevPage;
    //下一页
    private Integer nextPage;


    public PageResult(){}


    public PageResult(List<T> data,Integer totalCount,Integer currentPage,Integer pageSize){
        this.data = data;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = this.totalCount % this.pageSize == 0 ?
                this.totalCount / this.pageSize :
                this.totalCount / this.pageSize + 1;
    }

    public static PageResult empty(){
        return new PageResult(new ArrayList(),0,0,0);
    }
}
