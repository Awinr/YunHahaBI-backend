package io.web.bi.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.HighlightField;

import java.util.List;

/**
 * Employee.
 *
 * @date 2023-06-08
 */
@Document(indexName = "megacorp")
@Data
public class Employee {
    /*"
    first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
    */
    @Id
    private Long id;

    @Field(name = "first_field")
    @HighlightField
    private String firstName;

    @Field(name = "last_name")
    private String lastName;
    private Integer age;

    /**
     * 两种分词器使用的最佳实践是：索引时用ik_max_word，在搜索时用ik_smart。
     */
    @Field(name = "about", analyzer = "ik_max_word", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String about;
    private List<String> interests;

}
