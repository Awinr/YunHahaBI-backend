package io.web.bi.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.web.bi.common.BaseResponse;
import io.web.bi.common.ResultUtils;
import io.web.bi.model.entity.Employee;
import io.web.bi.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SearchController.
 *
 * @date 2023-06-08
 */
//@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final EmployeeRepository employeeRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 索引文档 - insert
     * @return
     */
    @PostMapping("/addIndex")
    public BaseResponse<Long> addIndex(@RequestBody Employee employee) {
        long id = IdWorker.getId();
        employee.setId(id);
        Employee save = employeeRepository.save(employee);
        return ResultUtils.success(save.getId());
    }

    /**
     * 检索
     * @returnapi
     */
    @GetMapping("/findByName")
    public BaseResponse<List<Employee>> findByName(@RequestParam String name) {
        List<Employee> list = employeeRepository.findEmployeeByLastNameContains(name);
        return ResultUtils.success(list);
    }

    /**
     * 高亮，ik分词
     * @return
     */
    @GetMapping("/findEmployeeByAbout")
    public BaseResponse<List<Employee>> findEmployeeByAbout(@RequestParam String name) {
        List<Employee> list = employeeRepository.findEmployeeByAbout(name);
        return ResultUtils.success(list);
    }

}



