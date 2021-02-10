package com.es.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 搜索实体
 * </p>
 *
 * @author hongliang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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





    public static final String ID = "id";

    public static final String CATEGORY_ID = "category_id";

    public static final String DATASOURCE_ID = "datasource_id";

    public static final String DATASOURCE_NAME = "datasource_name";

    public static final String METADATA_ID = "metadata_id";

    public static final String TABLE_NAME = "table_name";

    public static final String TABLE_TEXT = "table_text";

    public static final String ENTITY_NAME = "entity_name";

    public static final String ENTITY_TEXT = "entity_text";

    public static final String ENTITY_CREATE = "entity_create";

    public static final String EXECUTE_SQL = "execute_sql";

    public static final String FILTER_SQL = "filter_sql";

    public static final String INCREMENT_FIELD = "increment_field";

    public static final String INCREMENT_TYPE = "increment_type";

    public static final String SCOUNT = "scount";

    public static final String EXECUTE_VALUE = "execute_value";

    public static final String REMARK = "remark";

    public static final String ENABLED = "enabled";

    public static final String CREATOR = "creator";

    public static final String CREATOR_NAME = "creator_name";

    public static final String UPDATOR = "updator";

    public static final String UPDATOR_NAME = "updator_name";

}