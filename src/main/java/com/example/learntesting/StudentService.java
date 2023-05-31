package com.example.learntesting;

import com.example.learntesting.exceptions.DuplicatedException;
import com.example.learntesting.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Boolean existsUsername =
                studentRepository.existsByUsername(student.getUsername());
        if(existsUsername)
            throw new DuplicatedException(
                    "Username " + student.getUsername() + " existed!"
            );
        studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id))
            throw new NotFoundException(
              "Not found student with id: " + id
            );
        studentRepository.deleteById(id);
    }
}
