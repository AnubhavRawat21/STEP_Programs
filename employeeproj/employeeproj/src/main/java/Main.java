import java.util.List;
import java.util.Scanner;

import controller.StudentController;
import model.Student;
import model.SubjectMarks;
import view.StudentView;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		StudentController controller = new StudentController();

		StudentView view = new StudentView();

		int choice = 0;

		while (choice != 6) {

			System.out.println();
			System.out.println("===== STUDENT MARKS SYSTEM =====");
			System.out.println("1. Add Student");
			System.out.println("2. Get Student By ID");
			System.out.println("3. Get All Students");
			System.out.println("4. Update Student");
			System.out.println("5. Delete Student");
			System.out.println("6. Exit");

			System.out.print("Enter Choice : ");
			choice = sc.nextInt();

			switch (choice) {

			case 1:

				System.out.print("Enter Reg Number : ");
				String regNo = sc.next();

				sc.nextLine();

				System.out.print("Enter Student Name : ");
				String name = sc.nextLine();

				System.out.print("Science Marks : ");
				int science = sc.nextInt();

				System.out.print("Maths Marks : ");
				int maths = sc.nextInt();

				System.out.print("Computer Science Marks : ");
				int cs = sc.nextInt();

				SubjectMarks marks = new SubjectMarks(science, maths, cs);

				Student student = new Student(regNo, name, new SubjectMarks[] { marks });

				controller.createStudent(student);

				System.out.println("Student Added Successfully");
				break;

			case 2:

				System.out.print("Enter Student ID : ");
				int id = sc.nextInt();

				Student dbStudent = controller.getStudent(id);

				view.displayStudent(dbStudent);

				break;

			case 3:

				List<Student> students = controller.getAllStudents();

				view.displayAllStudents(students);

				break;

			case 4:

				System.out.print("Student ID : ");
				int updateId = sc.nextInt();

				System.out.print("Reg Number : ");
				String updateReg = sc.next();

				sc.nextLine();

				System.out.print("Student Name : ");
				String updateName = sc.nextLine();

				System.out.print("Science : ");
				int updateScience = sc.nextInt();

				System.out.print("Maths : ");
				int updateMaths = sc.nextInt();

				System.out.print("Computer Science : ");
				int updateCs = sc.nextInt();

				SubjectMarks updateMarks = new SubjectMarks(updateScience, updateMaths, updateCs);

				Student updateStudent = new Student(updateReg, updateName, new SubjectMarks[] { updateMarks });

				updateStudent.setId(updateId);

				controller.updateStudent(updateStudent);

				System.out.println("Student Updated");

				break;

			case 5:

				System.out.print("Enter Student ID : ");
				int deleteId = sc.nextInt();

				controller.deleteStudent(deleteId);

				System.out.println("Student Deleted");

				break;

			case 6:

				System.out.println("Thank You");
				break;

			default:

				System.out.println("Invalid Choice");
			}
		}

		sc.close();
	}

}
