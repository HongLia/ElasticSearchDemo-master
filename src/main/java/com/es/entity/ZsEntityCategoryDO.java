package com.es.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 搜索分类
 * </p>
 *
 * @author zhl
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zs_entity_category")
public class ZsEntityCategoryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer thesort;

    /**
     * 状态(0正常,1注销)
     */
    private String enabled;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String updator;


}
