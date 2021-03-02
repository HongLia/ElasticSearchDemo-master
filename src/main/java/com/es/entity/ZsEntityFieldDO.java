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
 * 实体字段
 * </p>
 *
 * @author zhl
 * @since 2021-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zs_entity_field")
public class ZsEntityFieldDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 实体id
     */
    private String entityId;

    /**
     * 属性名称
     */
    private String propName;

    /**
     * 展示名称
     */
    private String propText;

    /**
     * 属性类型
     */
    private String propType;

    /**
     * 来源字段名称
     */
    private String fieldName;

    /**
     * 来源字段注释
     */
    private String fieldText;

    /**
     * 来源字段类型
     */
    private String fieldType;

    /**
     * 字段主键(0否,1是)
     */
    private String fieldPk;

    /**
     * 字段内容(0否,search_key)
     */
    private String searchKey;

    /**
     * 是否搜索(0否,1是)
     */
    private String searchField;

    /**
     * 概要(0否,1标题,2短信息,3长信息)
     */
    private String overviewField;

    /**
     * 详情(0否,1标题,2短信息,3长信息)
     */
    private String detailsField;

    /**
     * 列表(0否,1是)
     */
    private String listsField;

    /**
     * 档案详情(0否,1标题,2短信息,3长信息)
     */
    private String archivesField;

    /**
     * 档案标识(0否,1是)
     */
    private String archivesPk;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updator;

    /**
     * 更新时间
     */
    private Date updateTime;


}
