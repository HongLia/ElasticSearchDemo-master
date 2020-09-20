package com.es.controller;

import com.es.qo.GoodsQueryObject;
import com.es.qo.PageResult;
import com.es.qo.QueryObject;
import com.es.repository.IGoodsRepository;
import com.es.util.Response;
import com.es.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EsGoodsDemoController {
    @Autowired
    IGoodsRepository goodsRepository;

    @PostMapping("/getAllByIndex")
    @ResponseBody
    public Response getAllByIndex(@RequestBody QueryObject qo) throws Exception {
        if(qo == null){
            return Response.failure("参数错误", null);
        }
        int currentPage = qo.getCurrentPage()== 0 ? 1 : qo.getCurrentPage();
        int pageSize = qo.getPageSize() == 0 ? 10 : qo.getPageSize();
        qo.setCurrentPage(currentPage);
        qo.setPageSize(pageSize);
        PageResult pageResult = goodsRepository.getAllByIndex(qo);
        Response r = new Response();
        r.setData(pageResult);
        return r;
    }

    @PostMapping("/getById")
    @ResponseBody
    public Response getById(@RequestBody GoodsVo vo) throws Exception {
        if(vo == null || vo.getId() == null){
            return Response.failure("参数错误",null);
        }
        GoodsVo o = goodsRepository.get(vo.getId());
        Response r = new Response();
        r.setData(o);
        return r;
    }

    @PostMapping("/search")
    @ResponseBody
    public Response search(@RequestBody QueryObject qo) throws Exception {
        if(qo == null || qo.getKeyword() == null || "".equals(qo.getKeyword())){
            return Response.failure("参数错误",null);
        }
        int currentPage = qo.getCurrentPage()== 0 ? 1 : qo.getCurrentPage();
        int pageSize = qo.getPageSize() == 0 ? 10 : qo.getPageSize();
        qo.setCurrentPage(currentPage);
        qo.setPageSize(pageSize);
        PageResult pageResult = goodsRepository.search(qo);
        Response r = new Response();
        r.setData(pageResult);
        return r;
    }

    @PostMapping("/insertOrUpdate")
    @ResponseBody
    public Response insertOrUpdate(@RequestBody GoodsVo vo) {
        try {
            goodsRepository.insertOrUpdate(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("新增失败", vo.getId());
        }
        return Response.success("新增成功");
    }

    @PostMapping("/deleteById")
    @ResponseBody
    public Response delete(@RequestBody GoodsVo vo) {
        if(vo == null || vo.getId() == null){
            return Response.failure("参数错误",null);
        }
        try {
            goodsRepository.delete(vo.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", vo.getId());
        }
        return Response.success(null);
    }

}
