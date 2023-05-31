package com.example.learntesting;


import com.example.learntesting.exceptions.DuplicatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentService0Test {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;
    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;
    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void CanAddStudent() {
        // given
        Student newStudent = new Student(
                "Phan Công Tú",
                Gender.MALE,
                "tu",
                "123"
        );

        // when
        studentService.addStudent(newStudent);

        // then
        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student captorStudent = studentArgumentCaptor.getValue();
        assertThat(captorStudent).isEqualTo(newStudent);
    }
    @Test
    void willThrownIfUsernameIsTaken(){
        // given
        Student newStudent = new Student(
                "Phan Công Tú",
                Gender.MALE,
                "tu",
                "123"
        );
        given(studentRepository.existsByUsername(newStudent.getUsername()))
                .willReturn(true);
        // when

        // then
        assertThatThrownBy(()->studentService.addStudent(newStudent))
                .isInstanceOf(DuplicatedException.class)
                .hasMessageContaining( "Username " + newStudent.getUsername() + " existed!");


    }
}