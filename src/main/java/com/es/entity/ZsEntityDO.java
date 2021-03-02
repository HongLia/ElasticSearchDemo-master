package com.es.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 搜索实体
 * </p>
 *
 * @author zhl
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zs_entity")
public class ZsEntityDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 数据源id
     */
    private String datasourceId;

    /**
     * 数据源名称
     */
    private String datasourceName;

    /**
     * 元数据id
     */
    private String metadataId;

    /**
     * 元数据名称(英文)
     */
    private String tableName;

    /**
     * 元数据名称(中文)
     */
    private String tableText;

    /**
     * 索引名称
     */
    private String entityName;

    /**
     * 展示名称
     */
    private String entityText;

    /**
     * 创建索引(0未创建,1已创建)
     */
    private String entityCreate;

    /**
     * 抽取sql
     */
    private String executeSql;

    /**
     * 筛选条件
     */
    private String filterSql;

    /**
     * 增量字段
     */
    private String incrementField;

    /**
     * 增量类型(0自增,1时间)
     */
    private String incrementType;

    /**
     * 数据抽取总量
     */
    private Long scount;

    /**
     * 最后抽取值
     */
    private String executeValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态(0正常,1注销)
     */
    private String enabled;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 修改人
     */
    private String updator;

    /**
     * 更新人姓名
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date updateTime;


}
