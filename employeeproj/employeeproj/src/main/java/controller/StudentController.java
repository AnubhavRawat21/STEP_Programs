package controller;

import java.util.List;

import dao.StudentDAO;
import dao.StudentDAOImpl;
import model.Student;

public class StudentController {
	private StudentDAO dao;

    public StudentController() {
        dao = new StudentDAOImpl();
    }

    public void createStudent(Student student) {
        dao.addStudent(student);
    }

    public Student getStudent(int id) {
        return dao.getStudentById(id);
    }

    public List<Student> getAllStudents() {
        return dao.getAllStudents();
    }

    public void updateStudent(Student student) {
        dao.updateStudent(student);
    }

    public void deleteStudent(int id) {
        dao.deleteStudent(id);
    }
}
