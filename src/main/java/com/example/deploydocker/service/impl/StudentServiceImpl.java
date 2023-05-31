package com.example.deploydocker.service.impl;

import com.example.deploydocker.domain.Student;
import com.example.deploydocker.repository.StudentRepository;
import com.example.deploydocker.service.StudentService;
import com.example.deploydocker.service.dto.StudentDTO;
import com.example.deploydocker.web.utils.PagingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Student addStudent(StudentDTO studentDTO) {
        return studentRepository.save(modelMapper.map(studentDTO, Student.class));
    }

    @Override
    public Optional<Student> updateStudent(StudentDTO studentDTO, Long id) {
        return studentRepository.findById(id).map(studentX -> {
            studentDTO.setId(studentX.getId());
            modelMapper.map(studentDTO, studentX);
            return studentX;
        }).map(studentRepository::save);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public PagingResponse<Student> getAllStudent(Pageable pageable) {
        Page<Student> page = studentRepository.findAll(pageable);
        return PagingResponse.<Student>builder()
                .page(page.getPageable().getPageNumber() + 1)
                .totalPage(page.getTotalPages())
                .totalItem(page.getTotalElements())
                .size(page.getSize())
                .contends(page.getContent())
                .build();
    }

    @Override
    public Optional<Student> getOneStudent(Long id) {
        return studentRepository.findById(id);
    }


}
