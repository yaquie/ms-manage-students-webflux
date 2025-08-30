package com.challenge.student.challenge.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.challenge.student.challenge.model.Student;
import com.challenge.student.challenge.repository.StudentRepository;
import com.challenge.student.challenge.service.StudentService;
import com.challenge.student.challenge.utils.DomainExceptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ManageStudentImplTest {

  @Mock
  private StudentService studentService;

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private ManageStudentImpl manageStudent;

  @Test
  @DisplayName("Obtained studens by active status")
  void obtainedStudentsByActiveStatus(){

    Student s1 = new Student("s001", "Juan", "Reyes","ACTIVE", 35);
    Student s2 = new Student("s002", "Maria", "Aguilar","ACTIVE", 28);
    Student s3 = new Student("s003", "Pepe", "Llanos","INACTIVE", 28);

    Flux<Student> studentFlux = Flux.just(s1, s2,s3);

    when(studentService.getStudentByStatus(any())).thenReturn(studentFlux);

    Mono<ResponseEntity<Flux<Student>>> responseEntityMono =
    manageStudent.studentsByActiveStatus("ACTIVE");

    StepVerifier.create(responseEntityMono)
        .assertNext(response -> {
          assertEquals(HttpStatus.OK, response.getStatusCode());
          assertNotNull(response.getBody());
        }).verifyComplete();
    verify(studentService, times(2)).getStudentByStatus("ACTIVE");
  }

  @Test
  @DisplayName("return empty when students have inactive status")
  void oreturnEmptyWhenStudentHavInactiveStatus(){

    when(studentService.getStudentByStatus(any())).thenReturn(Flux.empty());

    Mono<ResponseEntity<Flux<Student>>> responseEntityMono =
        manageStudent.studentsByActiveStatus("ACTIVE");

    StepVerifier.create(responseEntityMono)
        .assertNext(response -> {
          assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
          assertNull(response.getBody());
        }).verifyComplete();
    verify(studentService, times(1)).getStudentByStatus("ACTIVE");
  }

  @Test
  @DisplayName("save student successfully")
  void saveStudentSuccessfully(){
  Student student = new Student();
    student.setId("S0001");
    student.setName("Mari");
    student.setLastName("Salazar");
    student.setStatus("ACTIVE");
    student.setAge(15);

    when(studentService.saveStudent(student)).thenReturn(Mono.empty());

    Mono<ResponseEntity<Void>> responseSaveStudent =
        manageStudent.saveStudent(student);

    StepVerifier.create(responseSaveStudent)
        .assertNext(response -> {
          assertEquals(HttpStatus.OK, response.getStatusCode());
          assertNull(response.getBody());
        }).verifyComplete();
    verify(studentService, times(1)).saveStudent(student);
  }

  @Test
  @DisplayName("return error when student already exist")
  void returnErrorWhenStudentAlreadyExist(){
    Student student = new Student();
    student.setId("S0001");
    student.setName("Juan");
    student.setLastName("Salazar");
    student.setStatus("ACTIVE");
    student.setAge(15);

    DomainExceptions exception = new DomainExceptions("ERROR001", "El estudiante ya existe");


    when(studentService.saveStudent(student)).thenReturn(Mono.error(exception));

    Mono<ResponseEntity<Void>> responseSaveStudent =
        manageStudent.saveStudent(student);

    StepVerifier.create(responseSaveStudent)
        .expectNextMatches(response -> response.getStatusCode().is4xxClientError()).verifyComplete();
    verify(studentService, times(1)).saveStudent(student);
  }
}