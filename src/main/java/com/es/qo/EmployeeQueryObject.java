package com.es.qo;

import com.es.vo.EmployeeVo;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Arrays;

/***
 * 员工资源查询对象
 */
@Setter@Getter
public class EmployeeQueryObject extends QueryObject {

    /**
     * 设置搜索员工资源的具体添加
     * @return
     */
    @Override
    public SearchSourceBuilder createSearchSourceBuilder() {
        //这里可以给searchSourceBuilder设置一些特殊的条件
        SearchSourceBuilder searchSourceBuilder = super.createSearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("about",keyword));
        return searchSourceBuilder;
    }

    /**
     * 设置员工哪些字段需要高亮显示
     * @return
     */
    @Override
    public HighlightBuilder createHighlightBuilder() {
        //这里可以给HighlightBuilder设置一些特殊的高亮配置
        HighlightBuilder highlightBuilder = super.createHighlightBuilder();
        HighlightBuilder.Field highlightAbout = new HighlightBuilder.Field("about");
        highlightBuilder.field(highlightAbout);
        return highlightBuilder;
    }

    /**
     * 给搜索出来的员工资源对象重新设置高亮后的字段值
     * @param o
     * @param hit
     */
    @Override
    public void setHighlightFields(Object o,SearchHit hit) {
        //设置高亮字段
        super.setHighlightFields(o,hit);
        HighlightField highlightField = hit.getHighlightFields().get("about");
        EmployeeVo employeeVo = (EmployeeVo) o;
        employeeVo.setAbout(Arrays.toString(highlightField.getFragments()));
    }
}
