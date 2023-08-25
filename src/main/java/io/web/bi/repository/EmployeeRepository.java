package io.web.bi.repository;

import io.web.bi.model.entity.Employee;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository.
 *
 * @date 2023-06-08
 */
@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long> {

    List<Employee> findEmployeeByLastNameContains(String name);

    List<Employee> findEmployeeByAbout(String about);
}
