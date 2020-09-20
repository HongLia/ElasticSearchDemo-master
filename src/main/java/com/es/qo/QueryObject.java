package com.es.qo;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.Serializable;

/**
 * 查询条件相关属性对象
 */
@Setter@Getter
public class QueryObject implements Serializable{

    //当前页
    protected Integer currentPage = 1;

    //每页显示的数据大小
    protected Integer pageSize = 10;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //搜索关键字
    protected String keyword;


    //开始的记录数
    public Integer getStart(){
        return (currentPage - 1) * pageSize;
    }

    /**
     * 创建SearchSourceBuilder，并设置通用的属性
     * @return
     */
    public SearchSourceBuilder createSearchSourceBuilder(){
        //这里可以给searchSourceBuilder设置一些通用的条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(getStart());
        searchSourceBuilder.size(pageSize);
        return searchSourceBuilder;
    }

    /**
     * 创建HighlightBuilder对象，并设置一些通用的属性
     * @return
     */
    public HighlightBuilder createHighlightBuilder(){
        //这里可以给HighlightBuilder设置一些通用的高亮配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<b>");
        highlightBuilder.postTags("</b>");
        return highlightBuilder;
    }

    /**
     * 给搜索出来的资源重新设置高亮字段的值，父类无需做任何操作，由子类操作
     * @param obj
     * @param hit
     */
    public void setHighlightFields(Object obj,SearchHit hit){

    }
}
