package com.example.deploydocker.repository;

import com.example.deploydocker.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
