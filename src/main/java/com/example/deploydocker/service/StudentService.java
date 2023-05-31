package com.example.deploydocker.service;

import com.example.deploydocker.domain.Student;
import com.example.deploydocker.service.dto.StudentDTO;
import com.example.deploydocker.web.utils.PagingResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {
    Student addStudent(StudentDTO studentDTO);

    Optional<Student> updateStudent(StudentDTO studentDTO,Long id);

    void deleteStudent(Long id);
    PagingResponse<Student> getAllStudent(Pageable pageable);
    Optional<Student> getOneStudent(Long id);
}
