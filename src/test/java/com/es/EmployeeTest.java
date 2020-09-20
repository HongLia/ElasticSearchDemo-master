//package com.es;
//
//import com.es.qo.EmployeeQueryObject;
//import com.es.qo.PageResult;
//import com.es.qo.QueryObject;
//import com.es.repository.IEmployeeRepository;
//import com.es.vo.EmployeeVo;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class EmployeeTest extends ApplicationTest{
//
//    @Autowired
//    private IEmployeeRepository employeeRepository;
//
//    /**
//     * 测试通过id获取文档
//     */
//    @Test
//    public void get() throws Exception {
//        EmployeeVo employee = employeeRepository.get("3");
//        System.out.println(employee);
//    }
//
//    /**
//     * 添加和修改文档
//     */
//    @Test
//    public void inster() throws Exception {
//        for(long i=0;i<10;i++){
//            EmployeeVo employeeVo = new EmployeeVo();
//            employeeVo.setId(i);
//            employeeVo.setAge(20);
//            employeeVo.setName("张三"+i);
//            employeeVo.setAbout("张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三"+i);
//            employeeRepository.insertOrUpdate(employeeVo);
//        }
//
//    }
//
//    /**
//     *测试获取所有文档
//     */
//    @Test
//    public void getAll() throws Exception {
//        QueryObject qo = new QueryObject();
//        qo.setPageSize(1);
//        qo.setPageSize(10);
//        PageResult list = employeeRepository.getAllByIndex(qo);
//        list.getData().forEach(employee -> {
//            System.out.println(employee);
//        });
//    }
//
//    /**
//     * 搜索测试
//     */
//    @Test
//    public void search() throws Exception {
//        EmployeeQueryObject qo = new EmployeeQueryObject();
//        qo.setKeyword("张三");
//        PageResult pageResult = employeeRepository.search(qo);
//        pageResult.getData().forEach(employee -> {
//            System.out.println(employee);
//        });
//    }
//}
//
