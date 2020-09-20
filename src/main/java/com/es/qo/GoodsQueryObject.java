package com.es.qo;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Queue;

/**
 * 文章资源的查询对象
 */
@Setter@Getter
public class GoodsQueryObject extends QueryObject {


    /**
     * 设置搜索文章的具体条件
     * @return
     */
    @Override
    public SearchSourceBuilder createSearchSourceBuilder() {
        //这里可以给searchSourceBuilder设置一些特殊的条件
        SearchSourceBuilder searchSourceBuilder = super.createSearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title",keyword));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content",keyword));
        boolQueryBuilder.should(QueryBuilders.matchQuery("id",keyword));
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

    /**
     * 没有设置高亮的属性
     */
}
