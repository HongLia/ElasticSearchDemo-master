//package com.es;
//
//import com.es.qo.GoodsQueryObject;
//import com.es.qo.PageResult;
//import com.es.qo.QueryObject;
//import com.es.repository.IGoodsRepository;
//import com.es.vo.GoodsVo;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class GoodsTest extends ApplicationTest{
//
//    @Autowired
//    private IGoodsRepository goodsRepository;
//
//    /**
//     *测试添加文档
//     */
//    @Test
//    public void inster() throws Exception {
//        GoodsVo goodsVo = new GoodsVo();
//        for(int i = 1; i<=12;i++){
//            goodsVo = new GoodsVo();
//            goodsVo.setId(i + "");
//            goodsVo.setLast_version(i+ ".1.0");
//            goodsVo.setName("蘑菇" + i);
//            goodsVo.setTitle("蘑菇类");
//            goodsVo.setContent("蘑菇类产品");
//            goodsVo.setAuthor("山东大棚" + i);
//            goodsRepository.insertOrUpdate(goodsVo);
//        }
//
//
//    }
//
//    /**
//     * 测试删除文档
//     */
//    @Test
//    public void delete() throws Exception {
//            goodsRepository.delete("1");
//
//    }
//
//    /**
//     * 测试通过id获取文档
//     */
//    @Test
//    public void get() throws Exception {
//        GoodsVo goodsVo = goodsRepository.get("3");
//        System.out.println(goodsVo);
//    }
//
//
//    /**
//     *测试获取所有文档
//     */
//    @Test
//    public void getAll() throws Exception {
//        QueryObject qo = new QueryObject();
//        qo.setPageSize(1);
//        qo.setPageSize(10);
//        PageResult list = goodsRepository.getAllByIndex(qo);
//        list.getData().forEach(goodsVo -> {
//            System.out.println(goodsVo);
//        });
//    }
//
//    /**
//     * 搜索
//     */
//    @Test
//    public void search() throws Exception {
//        GoodsQueryObject qo = new GoodsQueryObject();
//        qo.setKeyword("3");
//        PageResult pageResult = goodsRepository.search(qo);
//        pageResult.getData().forEach(articleVo -> {
//            System.out.println(articleVo);
//        });
//    }
//}
