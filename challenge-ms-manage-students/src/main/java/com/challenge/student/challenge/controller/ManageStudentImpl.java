package com.challenge.student.challenge.controller;

import com.challenge.student.challenge.model.Student;
import com.challenge.student.challenge.service.StudentService;
import com.challenge.student.challenge.utils.DomainExceptions;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/manage")
@AllArgsConstructor
public class ManageStudentImpl implements ManageStudent{

  private final StudentService studentService;

  @PostMapping("/save")
  @Override
  public Mono<ResponseEntity<Void>> saveStudent(@RequestBody Student student) {
    return studentService.saveStudent(student)
        .thenReturn(ResponseEntity.ok().<Void>build())
    .onErrorResume(this::manageException);
  }



  @GetMapping("/students/{status}")
  @Override
  public Mono<ResponseEntity<Flux<Student>>> studentsByActiveStatus(@PathVariable("status") String status) {
    Flux<Student> studentFlux =  studentService.getStudentByStatus(status);
    return studentFlux
        .hasElements()
        .flatMap(haselement -> {
          if (haselement){
            return Mono.just(ResponseEntity.ok(studentService.getStudentByStatus(status)));
          }
          return Mono.just(ResponseEntity.noContent().build());

        });
  }

  Mono<ResponseEntity<Void>> manageException (Throwable throwable){
    if (throwable instanceof DomainExceptions){
      DomainExceptions  castException = ( DomainExceptions) throwable;
      HttpHeaders headers = new HttpHeaders();
      headers.add("code", castException.getCode());
      headers.add("message", castException.getMessage());
      return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
          .headers(headers).build());
    }
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .build());
  }
}
