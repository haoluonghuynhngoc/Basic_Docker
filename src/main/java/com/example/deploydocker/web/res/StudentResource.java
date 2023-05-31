package com.example.deploydocker.web.res;

import com.example.deploydocker.domain.Student;
import com.example.deploydocker.repository.StudentRepository;
import com.example.deploydocker.service.StudentService;
import com.example.deploydocker.service.dto.StudentDTO;
import com.example.deploydocker.web.res.error.BadRequestAlertException;
import com.example.deploydocker.web.utils.PagingResponse;
import com.example.deploydocker.web.utils.ResponseObject;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


//@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentResource {
    private final Logger log = LoggerFactory.getLogger(StudentResource.class);
    private static final String ENTITY_NAME = "student";

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    @PostMapping("/Add")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO student) throws URISyntaxException {
        if (student.getAge() <= 0) {
            throw new BadRequestAlertException(student.getId() + " Is Error", "Student", student.getAge() + " is Error");
        }
        Student result = studentService.addStudent(student);
        log.debug("REST request to save Student : {}", result);
        return ResponseEntity
                .created(new URI("/api/students/" + result.getId()))
                //.headers(httpHeaders -> httpHeaders.add("teach", "add"))
                //.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    @PutMapping("/updateById/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody StudentDTO student
    ) throws URISyntaxException {
        log.debug("REST request to update Student : {}, {}", id, student);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is null");
        }
//        if (!Objects.equals(id, student.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
//        }
        if (!studentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
//        return ResponseEntity
//                .ok()
//                //.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, student.getId().toString()))
//                .body(result);
        return ResponseObject.wrapOrNotFound(studentService.updateStudent(student, id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<PagingResponse<Student>> getAllStudents(
            @Parameter(description = "Pageable object", required = true)
            @ParameterObject Pageable pageable) {
        //"name,asc"  || "name,desc"
        log.debug("REST request to get a page of Students");
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudent(pageable));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getOneStudent(id);
        if (student.isPresent()) {
            log.debug("REST request to get Student : {}", id);
        }
        return ResponseObject.wrapOrNotFound(student);
    }

    @DeleteMapping("/removeById/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity
                .noContent()
                //.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
