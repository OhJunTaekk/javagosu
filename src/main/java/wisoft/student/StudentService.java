package wisoft.student;

import java.util.List;
public interface StudentService{

    List<Student> getStudents();

    Student getStudentByNo(final String studentNo);
    Student getStudentByName(final String studentName);
    Student getStudentByBirthday(final String studentBirthday);

    Integer insertStudent(final Student student);
    Integer insertStudentMulti (final Student[] students);
    Integer insertStudentMultiBatch ( final Student[] students);

    Integer updateStudent(final Student student);
    Integer updateStudentMulti(final Student[] students);
    Integer deleteStudentByNo(final String no);
    Integer deleteStudentMulti(final Student[] students);
}
