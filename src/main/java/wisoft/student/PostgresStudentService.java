package wisoft.student;

import wisoft.common.PostgresAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresStudentService implements StudentService {
    @Override
    public List<Student> getStudents() {
        final var query = "SELECT * FROM student";
        final List<Student> students = new ArrayList<>();

        try (
                final Connection conn = PostgresAccess.setConnection();
                final PreparedStatement statement = conn.prepareStatement(query);
                final ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                final Student student = new Student(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate()
                );
                students.add(student);
            }
        } catch (final SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState    : " + e.getSQLState());
        }
        return students;
    }

    @Override
    public Student getStudentByNo(final String studentNo) {
        final String query = "SELECT * FROM student WHERE no = ?";
        Student student = new Student();

        try (
                final Connection connection = PostgresAccess.setConnection();
                final PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, studentNo);
            try (
                    final ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    student = new Student(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3).toLocalDate()
                    );
                }
            }
        } catch (final SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
            System.err.println("SQLState : " + e.getSQLState());
        }

        return student;
    }

    @Override
    public Student getStudentByName(final String studentName) {
        return null;
    }

    @Override
    public Student getStudentByBirthday(final String studentBirthday) {
        final String query = "SELECT * FROM student WHERE birthday = ?";
        Student student = new Student();

        try (
                final Connection connection = PostgresAccess.setConnection();
                final PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setDate(1, Date.valueOf(studentBirthday));
            try (
                    final ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    student = new Student(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3).toLocalDate()
                    );
                }
            }
        } catch (final SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
            System.err.println("SQLState : " + e.getSQLState());
        }

        return student;
    }

    @Override
    public Integer insertStudent(final Student student) {
        int result = 0;
        final String query = "INSERT INTO STUDENT(no, name, birthday) VALUES (?, ?, ?)";

        try (final Connection connection = PostgresAccess.setConnection()) {
            connection.setAutoCommit(false);

            try (
                    final PreparedStatement statement = connection.prepareStatement(query)
            ) {
                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());

                if (student.getBirthday() == null) {
                    statement.setNull(3, Types.DATE);
                } else {
                    statement.setDate(3, Date.valueOf((student.getBirthday())));
                }

                result = statement.executeUpdate();
                connection.commit();
            } catch (final SQLException e) {
                connection.rollback();
                System.err.println("SQLException : " + e.getMessage());
                System.err.println("SQLState : " + e.getSQLState());
            }
        } catch (final SQLException e) {
            System.err.println("SQLException : " + e.getMessage());
            System.err.println("SQLState : " + e.getSQLState());
        }

        return result;
    }

    @Override
    public Integer insertStudentMulti(final Student[] students) {
        int result = 0;
        final String query = "INSERT INTO student(no, name, birthday) VALUES (?, ?, ?)";

        try (
                final Connection connection = PostgresAccess.setConnection();
        ) {
            connection.setAutoCommit(false);

            try (
                    final PreparedStatement statement = connection.prepareStatement(query);
            ) {
                for (Student student : students) {
                    if (student.getNo() == null && student.getName() == null) {
                        break;
                    }

                    statement.setString(1, student.getNo());
                    statement.setString(2, student.getName());

                    if (student.getBirthday() == null) {
                        statement.setNull(3, Types.DATE);
                    } else {
                        statement.setDate(3, Date.valueOf(student.getBirthday()));
                    }
                    result += statement.executeUpdate();
                    statement.clearParameters();
                }
                connection.commit();
            } catch (final SQLException e) {
                connection.rollback();
                System.err.println("SQLException: " + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Integer insertStudentMultiBatch(final Student[] students) {
        int[] result = new int[10];
        final String query = "INSERT INTO student(no, name, birthday) VALUES (?, ?, ?)";

        try (
                final Connection connection = PostgresAccess.setConnection();
        ) {
            connection.setAutoCommit(false);

            try (
                    final PreparedStatement statement = connection.prepareStatement(query);
            ) {
                for (Student student : students) {
                    if (student.getNo() == null && student.getName() == null) {
                        break;
                    }

                    statement.setString(1, student.getNo());
                    statement.setString(2, student.getName());

                    if (student.getBirthday() == null) {
                        statement.setNull(3, Types.DATE);
                    } else {
                        statement.setDate(3, Date.valueOf(student.getBirthday()));
                    }
                    statement.addBatch();
                    statement.clearParameters();
                }

                result = statement.executeBatch();
                connection.commit();
            } catch (final SQLException e) {
                connection.rollback();
                System.err.println("SQLException: " + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return result.length;
    }

    @Override
    public Integer updateStudent(final Student student) {
        return 0;
    }

    @Override
    public Integer updateStudentMulti(final Student[] students) {
        return 0;
    }

    @Override
    public Integer deleteStudentByNo(final String no) {
        return 0;
    }

    @Override
    public Integer deleteStudentMulti(final Student[] students) {
        return 0;
    }
}