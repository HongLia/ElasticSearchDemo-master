package com.es.repository.impl;

import com.es.repository.IGoodsRepository;
import com.es.util.RepositoryName;
import com.es.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoodsRepositoryImpl extends BaseRepositoryImpl<GoodsVo> implements IGoodsRepository {

    //索引
    @RepositoryName("big")
    protected String index;

    //类型
    @RepositoryName("goods")
    protected String type;


    /**
     * 写一些特殊的方法
     */
}







