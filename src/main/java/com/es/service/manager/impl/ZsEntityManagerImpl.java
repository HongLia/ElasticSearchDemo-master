package com.es.service.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.es.entity.ZsEntityDO;
import com.es.mapper.ZsEntityMapper;
import com.es.service.manager.ZsEntityManager;
import com.es.service.service.ZsEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 搜索实体 服务实现类
 * </p>
 *
 * @author zhl
 * @since 2021-03-02
 */
@Service
public class ZsEntityManagerImpl implements ZsEntityManager {

    @Autowired
    private ZsEntityService entityService;

    @Override
    public List<ZsEntityDO> getAll() {
        return entityService.list();
    }
}
