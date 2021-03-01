package com.es.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
//@EqualsAndHashCode(callSuper = true)
//@Accessors(chain = true)
@TableName("zs_entity")
//@ApiModel(value="搜索实体DTO对象", description="搜索实体")
public class ZsEntityDTO {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(generator = "JDBC")
//    @ApiModelProperty("实体id")
//    @Column(name = "`id`")
    private String id;

//    @ApiModelProperty("分类id")
//    @Column(name = "`category_id`")
    private String categoryId;

//    @ApiModelProperty("数据源id")
//    @Column(name = "`datasource_id`")
    private String datasourceId;

//    @ApiModelProperty("数据源名称")
//    @Column(name = "`datasource_name`")
    private String datasourceName;

//    @ApiModelProperty("元数据id")
//    @Column(name = "`metadata_id`")
    private String metadataId;

//    @ApiModelProperty("元数据名称(英文)")
//    @Column(name = "`table_name`")
    private String tableName;

//    @ApiModelProperty("元数据名称(中文)")
//    @Column(name = "`table_text`")
    private String tableText;

//    @ApiModelProperty("索引名称")
//    @Column(name = "`entity_name`")
    private String entityName;

//    @ApiModelProperty("展示名称")
//    @Column(name = "`entity_text`")
    private String entityText;

//    @ApiModelProperty("创建索引(0未创建,1已创建)")
//    @Column(name = "`entity_create`")
    private String entityCreate;

//    @ApiModelProperty("抽取sql")
//    @Column(name = "`execute_sql`")
    private String executeSql;

//    @ApiModelProperty("筛选条件")
//    @Column(name = "`filter_sql`")
    private String filterSql;

//    @ApiModelProperty("增量字段")
//    @Column(name = "`increment_field`")
    private String incrementField;

//    @ApiModelProperty("增量类型(0自增,1时间)")
//    @Column(name = "`increment_type`")
    private String incrementType;

//    @ApiModelProperty("数据抽取总量")
//    @Column(name = "`scount`")
    private Long scount;

//    @ApiModelProperty("最后抽取值")
//    @Column(name = "`execute_value`")
    private String executeValue;

//    @ApiModelProperty("备注")
//    @Column(name = "`remark`")
    private String remark;

//    @ApiModelProperty("状态(0正常,1注销)")
//    @Column(name = "`enabled`")
    private String enabled;

//    @ApiModelProperty("创建人")
//    @Column(name = "`creator`")
    private String creator;

//    @ApiModelProperty("创建人姓名")
//    @Column(name = "`creator_name`")
    private String creatorName;

//    @ApiModelProperty("修改人")
//    @Column(name = "`updator`")
    private String updator;

//    @ApiModelProperty("更新人姓名")
//    @Column(name = "`updator_name`")
    private String updatorName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;






}
