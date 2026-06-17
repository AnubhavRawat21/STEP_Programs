package view;

import java.util.List;

import model.Student;
import model.SubjectMarks;

public class StudentView {
	public void displayStudent(Student student) {

		if (student == null) {
			System.out.println("Student Not Found");
			return;
		}

		SubjectMarks marks = student.getSubjects()[0];

		System.out.println("----------------------------------");
		System.out.println("ID          : " + student.getId());
		System.out.println("Name        : " + student.getStudentName());
		System.out.println("Reg Number  : " + student.getRegistrationNumber());
		System.out.println("Science     : " + marks.getScience());
		System.out.println("Maths       : " + marks.getMaths());
		System.out.println("Computer    : " + marks.getComputerScience());

		int total = marks.getScience() + marks.getMaths() + marks.getComputerScience();

		double avg = total / 3.0;

		System.out.println("Total       : " + total);
		System.out.println("Average     : " + avg);
		System.out.println("----------------------------------");
	}

	public void displayAllStudents(List<Student> students) {

		if (students.isEmpty()) {
			System.out.println("No Students Found");
			return;
		}

		for (Student s : students) {
			displayStudent(s);
		}
	}
}
