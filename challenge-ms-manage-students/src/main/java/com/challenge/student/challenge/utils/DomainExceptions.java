package com.challenge.student.challenge.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DomainExceptions  extends Throwable{
  private String code;
  private String message;

}
