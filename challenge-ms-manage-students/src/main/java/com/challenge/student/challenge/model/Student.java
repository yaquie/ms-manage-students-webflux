package com.challenge.student.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("students")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
  private String id;
  private String name;
  @Column("last_name")
  private String lastName;
  private String status;
  private Integer age;

  @Override
  public String toString() {
    return "Student{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", lastName='" + lastName + '\'' +
        ", status='" + status + '\'' +
        ", age=" + age +
        '}';
  }
}
