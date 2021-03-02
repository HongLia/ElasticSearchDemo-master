package com.es.controller;

import com.es.entity.ZsEntityDO;
import com.es.service.EsApi.SearchEngineService;
import com.es.service.manager.ZsEntityManager;
import com.es.service.service.ZsEntityService;
import com.es.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "测试模块",value = "测试SearchController")
public class SearchController {
    @Autowired
    SearchEngineService searchEngineService;
    @Autowired
    private ZsEntityManager entityManager;

    @ApiOperation("查看es健康状态")
    @GetMapping("/queryHealth")
    public Response queryHealth() throws Exception {
        String s1 = searchEngineService.queryHealth();
        return Response.success(s1);
    }

    @GetMapping("/createIndex")
    public Response createIndex(@RequestParam String indexName) throws Exception {
        // TODO: 2021/2/19 写死的JSON
        String ss = "\"author\":{\n" +
                "        \"type\": \"keyword\"\n" +
                "      }";
        return Response.success(searchEngineService.createIndex(indexName, ss));
    }

    @ApiOperation("获取实体表所有数据")
    @GetMapping("/getList")
    public Response getList() {
        List<ZsEntityDO> all = entityManager.getAll();
        return Response.success(all);
    }




}
