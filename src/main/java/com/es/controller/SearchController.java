package com.es.controller;

import com.es.entity.ZsEntityDTO;
import com.es.service.EntityService;
import com.es.service.EsApi.SearchEngineService;
import com.es.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SearchController {
    @Autowired
    SearchEngineService searchEngineService;
    @Autowired
    private EntityService entityService;

    @PostMapping("/queryHealth")
    @ResponseBody
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


    @GetMapping("/getList")
    public Response getList() {
        List<ZsEntityDTO> all = entityService.getAll();
        return Response.success(all);
    }




}
