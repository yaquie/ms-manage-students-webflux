package com.challenge.student.challenge.utils;

public enum StudentStatus {
  ACTIVE("Activo"),
  INACTIVO("Inactivo");

  private final String description;

  StudentStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
