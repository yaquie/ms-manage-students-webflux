-- IDENTITY implica autoincremento
CREATE TABLE students (
  id IDENTITY PRIMARY KEY,
  name VARCHAR(20),
  last_name VARCHAR(50),
  status VARCHAR(50),
  age INTEGER
);
