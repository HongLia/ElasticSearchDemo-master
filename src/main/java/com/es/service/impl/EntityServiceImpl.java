package com.es.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.es.entity.ZsEntityDTO;
import com.es.mapper.ZsEntityMapper;
import com.es.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private ZsEntityMapper entityMapper;

    @Override
    public List<ZsEntityDTO> getAll() {
        return entityMapper.selectList(new QueryWrapper<>());
    }
}
