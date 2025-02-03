package wisoft.student;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import wisoft.common.MyBatisAccess;

import java.util.ArrayList;
import java.util.List;

public class MyBatisStudentService implements StudentService{

    final SqlSessionFactory sqlSessionFactory = MyBatisAccess.getSqlSessionFactory();


    @Override
    public List<Student> getStudents() {
        final List<Student> students;

        try (final SqlSession session = sqlSessionFactory.openSession()){
            final var studentService = session.getMapper(StudentService.class);
            students = studentService.getStudents();
            return students;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Student getStudentByNo(final String studentNo) {

        final Student student;

        try (final SqlSession session = sqlSessionFactory.openSession()){
            final var studentService = session.getMapper(StudentService.class);
            student = studentService.getStudentByNo(studentNo);
            return student;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return new Student();
        }
    }

    @Override
    public Student getStudentByName(final String studentName) {
        return null;
    }

    @Override
    public Student getStudentByBirthday(final String studentBirthday) {
        final Student student;

        try (final SqlSession session = sqlSessionFactory.openSession()){
            final var studentService = session.getMapper(StudentService.class);
            student = studentService.getStudentByBirthday(studentBirthday);
            return student;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return new Student();
        }
    }

    @Override
    public Integer insertStudent(final Student student) {
        try (final SqlSession session = sqlSessionFactory.openSession()){
            final var studentService = session.getMapper(StudentService.class);
            int result = studentService.insertStudent(student);
            session.commit();
            return result;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public Integer insertStudentMulti(final Student[] students) {
        try (final SqlSession session = sqlSessionFactory.openSession()){
            final var studentService = session.getMapper(StudentService.class);
            int result = 0;
            for(Student student : students){
                result += studentService.insertStudent(student);
            }
            session.commit();
            return result;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public Integer insertStudentMultiBatch(final Student[] students) {
        try (final SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)){
            final var studentService = session.getMapper(StudentService.class);
            int result = 0;
            for(Student student : students){
                result += studentService.insertStudent(student);
            }
            session.commit();
            return students.length;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public Integer updateStudent(final Student student) {
        try (final SqlSession session = sqlSessionFactory.openSession()) {
            final StudentService studentService = session.getMapper(StudentService.class);
            int result = studentService.updateStudent(student);
            session.commit();
            return result;
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public Integer updateStudentMulti(final Student[] students) {
        try (final SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)){
            final var studentService = session.getMapper(StudentService.class);
            int result = 0;
            for(Student student : students){
                result += studentService.updateStudent(student);
            }
            session.commit();
            return students.length;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public Integer deleteStudentByNo(final String no) {
        try (final SqlSession session = sqlSessionFactory.openSession()){
            final var studentService = session.getMapper(StudentService.class);
            int result = studentService.deleteStudentByNo(no);
            session.commit();
            return result;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public Integer deleteStudentMulti(final Student[] students) {
        try (final SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)){
            final var studentService = session.getMapper(StudentService.class);
            int result = 0;
            for(Student student : students){
                result += studentService.deleteStudentByNo(student.getNo());
            }
            session.commit();
            return students.length;
        } catch (final Exception ex){
            System.err.println(ex.getMessage());
            return 0;
        }
    }
}
