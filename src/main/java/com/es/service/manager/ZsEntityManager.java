package com.es.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.es.entity.ZsEntityDO;

import java.util.List;

/**
 * <p>
 * 搜索实体 服务类
 * </p>
 *
 * @author zhl
 * @since 2021-03-02
 */
public interface ZsEntityManager {

    List<ZsEntityDO> getAll();
}
