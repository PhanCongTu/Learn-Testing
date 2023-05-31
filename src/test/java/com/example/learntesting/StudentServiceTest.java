package com.example.learntesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.learntesting.exceptions.DuplicatedException;
import com.example.learntesting.exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent() {
        Student student = new Student();
        student.setCreateAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        student.setFullName("Dr Jane Doe");
        student.setGender(Gender.MALE);
        student.setId(1L);
        student.setPassword("iloveyou");
        student.setUsername("janedoe");
        when(studentRepository.existsByUsername(Mockito.<String>any())).thenReturn(true);
        when(studentRepository.save(Mockito.<Student>any())).thenReturn(student);

        Student student2 = new Student();
        student2.setCreateAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        student2.setFullName("Dr Jane Doe");
        student2.setGender(Gender.MALE);
        student2.setId(1L);
        student2.setPassword("iloveyou");
        student2.setUsername("janedoe");
        assertThrows(DuplicatedException.class, () -> studentService.addStudent(student2));
        verify(studentRepository).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent2() {
        Student student = new Student();
        student.setCreateAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        student.setFullName("Dr Jane Doe");
        student.setGender(Gender.MALE);
        student.setId(1L);
        student.setPassword("iloveyou");
        student.setUsername("janedoe");
        when(studentRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        when(studentRepository.save(Mockito.<Student>any())).thenReturn(student);

        Student student2 = new Student();
        Date createAt = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        student2.setCreateAt(createAt);
        student2.setFullName("Dr Jane Doe");
        student2.setGender(Gender.MALE);
        student2.setId(1L);
        student2.setPassword("iloveyou");
        student2.setUsername("janedoe");
        studentService.addStudent(student2);
        verify(studentRepository).existsByUsername(Mockito.<String>any());
        verify(studentRepository).save(Mockito.<Student>any());
        assertSame(createAt, student2.getCreateAt());
        assertEquals("janedoe", student2.getUsername());
        assertEquals("iloveyou", student2.getPassword());
        assertEquals(1L, student2.getId());
        assertEquals(Gender.MALE, student2.getGender());
        assertEquals("Dr Jane Doe", student2.getFullName());
        assertTrue(studentService.getAllStudent().isEmpty());
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent3() {
        when(studentRepository.existsByUsername(Mockito.<String>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        when(studentRepository.save(Mockito.<Student>any())).thenThrow(new NotFoundException("An error occurred"));

        Student student = new Student();
        student.setCreateAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        student.setFullName("Dr Jane Doe");
        student.setGender(Gender.MALE);
        student.setId(1L);
        student.setPassword("iloveyou");
        student.setUsername("janedoe");
        assertThrows(NotFoundException.class, () -> studentService.addStudent(student));
        verify(studentRepository).existsByUsername(Mockito.<String>any());
    }
}

