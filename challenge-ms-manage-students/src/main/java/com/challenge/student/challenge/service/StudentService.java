package com.challenge.student.challenge.service;

import com.challenge.student.challenge.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface StudentService {

  public Mono<Void> saveStudent(Student s);
  public Flux<Student> getStudentByStatus(String status);
}
