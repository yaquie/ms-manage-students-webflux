package com.challenge.student.challenge.controller;

import com.challenge.student.challenge.model.Student;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManageStudent {
  public Mono<ResponseEntity<Void>> saveStudent(Student student);
  public Mono<ResponseEntity<Flux<Student>>> studentsByActiveStatus(String status);
}
