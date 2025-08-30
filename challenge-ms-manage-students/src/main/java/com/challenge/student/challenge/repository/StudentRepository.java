package com.challenge.student.challenge.repository;

import com.challenge.student.challenge.model.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface StudentRepository extends ReactiveCrudRepository<Student, String> {

  @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM students WHERE id = :id")
  Mono<Boolean> getStudentById(@Param("id") String id);

}
