package com.challenge.student.challenge.service;

import com.challenge.student.challenge.model.Student;
import com.challenge.student.challenge.repository.StudentRepository;
import com.challenge.student.challenge.utils.DomainExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService{

  private final StudentRepository studentRepository;

  @Override
  public Mono<Void> saveStudent(Student student) {
      return studentRepository.getStudentById(student.getId())
          .flatMap(exist -> {
            log.info("already save student: {}", exist);
            if (exist){
              return Mono.error(new DomainExceptions("ERROR001", "El estudiante ya existe, no se puede registrar duplicado"));
            } else {
              System.out.println("Saving student ....");
              return studentRepository.save(student).then();
            }
          });
  }

  @Override
  public Flux<Student> getStudentByStatus(String status) {
    return studentRepository.findAll()
        .filter(s -> s.getStatus().equals(status));
  }
}
