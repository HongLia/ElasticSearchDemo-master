package com.es.repository.impl;

import com.es.config.ApplicationContextHolder;
import com.es.qo.PageResult;
import com.es.qo.QueryObject;
import com.es.repository.IBaseRepository;
import com.es.util.BeanUtil;
import com.es.util.RepositoryName;
import com.es.vo.GoodsVo;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 封装通用的操作ES方法
 * @param <T>
 */
public class BaseRepositoryImpl<T> implements IBaseRepository<T> {

    //索引
    private String baseIndex;

    //类型
    private String baseType;

    //es的REST客户端
    protected RestHighLevelClient client = ApplicationContextHolder.getBean("client");

    //传递过来的泛型类型
    private Class<T> clazz;

    /**
     * 父类的无参构造器会被子类调用
     */
    public BaseRepositoryImpl(){
        try{
            //获取子类类型（this代表子类,因为是子类调用父类的这个无参构造方法）
            Class<? extends BaseRepositoryImpl> childClass = this.getClass();
            //获取子类的index和type字段,并赋值给自己
            Field[] fields = childClass.getDeclaredFields();
            for(Field field : fields){
                if(Objects.equals(field.getName(),"index"))
                    baseIndex = field.getAnnotation(RepositoryName.class).value();
                else if(Objects.equals(field.getName(),"type"))
                    baseType = field.getAnnotation(RepositoryName.class).value();
            }
            //获取定义在父类上的泛型参数对象
            ParameterizedType parameterizedType = (ParameterizedType) childClass.getGenericSuperclass();
            // 返回实际参数类型(泛型参数可以写多个)
            Type[] types = parameterizedType.getActualTypeArguments();
            // 获取第一个参数(泛型的具体类)
            this.clazz = (Class) types[0];
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 获取低水平REST客户端
     * @return
     */
    @Override
    public RestClient getLowLevelClient() {
        return client.getLowLevelClient();
    }

    /**
     * 插入和更新操作
     * @param o
     * @throws Exception
     */
    @Override
    public void insertOrUpdate(Object o) throws Exception {
        Map map = BeanUtil.bean2Map(o);
        IndexRequest request = new IndexRequest(baseIndex, baseType, map.get("id")+"");
        request.source(map);
        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 通过文档id删除文档
     * @param id
     * @throws Exception
     */
    @Override
    public void delete(String id) throws Exception {
        DeleteRequest request = new DeleteRequest(baseIndex, baseType, id);
        client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 通过文档id获取文档
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public T get(String id) throws Exception {
        GetRequest request = new GetRequest(baseIndex, baseType, id+"");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> source = response.getSource();
        T t = BeanUtil.map2Bean(source, clazz);
        return t;
    }

    public static void main(String[] args) throws Exception {
        BaseRepositoryImpl<Object> objectBaseRepository = new BaseRepositoryImpl<>();
        objectBaseRepository.get("1");
        System.out.println("objectBaseRepository = " + objectBaseRepository);
    }

    /**
     * 获取指定索引文档类型下所有文档
     * @return
     * @throws Exception
     */
    @Override
    public PageResult getAllByIndex(QueryObject qo) throws Exception {
        SearchRequest request = new SearchRequest();
        request.indices(baseIndex);
        request.types(baseType);
        // 创建SearchSourceBuilder，设置通用参数,如分页信息等
        SearchSourceBuilder sourceBuilder = qo.createSearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.matchAllQuery());
//        创建HighlightBuilder对象，并设置一些通用的属性
        HighlightBuilder highlightBuilder = qo.createHighlightBuilder();
        sourceBuilder.highlighter(highlightBuilder);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
//        TotalHits total = searchHits.getTotalHits();
        long total = searchHits.getTotalHits();
        SearchHit[] searchHitArray = searchHits.getHits();
        List<T> data = new ArrayList<>();
        for(SearchHit hit : searchHitArray){
            Map<String, Object> source = hit.getSourceAsMap();
            T t = BeanUtil.map2Bean(source, clazz);
            data.add(t);
        }
        return new PageResult(data,Integer.parseInt(total+""),qo.getCurrentPage(),qo.getPageSize());
    }

    /**
     * 搜索
     * @param qo
     * @return
     * @throws Exception
     */
    @Override
    public PageResult search(QueryObject qo) throws Exception {
        SearchRequest request = new SearchRequest();
        request.indices(baseIndex);
        request.types(baseType);
        // 创建SearchSourceBuilder，设置通用参数,如分页信息等
        SearchSourceBuilder sourceBuilder = qo.createSearchSourceBuilder();
        HighlightBuilder highlightBuilder = qo.createHighlightBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(GoodsVo.TITLE_,qo.getKeyword()));
        sourceBuilder.highlighter(highlightBuilder);
        System.out.println("查询语句 = " + sourceBuilder.toString());
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits();
        SearchHit[] searchHitArray = searchHits.getHits();
//        System.out.println("JSONArray.toJSONString(searchHitArray) = " + JSONArray.toJSONString(searchHitArray));
        List<T> data = new ArrayList<>();
        for(SearchHit hit : searchHitArray){
            Map<String, Object> source = hit.getSourceAsMap();
            T t = BeanUtil.map2Bean(source, clazz);
            qo.setHighlightFields(t,hit);
            data.add(t);
        }
        return new PageResult(data,Integer.parseInt(total+""),qo.getCurrentPage(),qo.getPageSize());
    }
}





















