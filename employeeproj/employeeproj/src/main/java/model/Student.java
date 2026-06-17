package model;

public class Student {
	 private int id;
	    private String registrationNumber;
	    private String studentName;

	    private SubjectMarks[] subjects;

	    public Student() {
	    }

	    public Student(String registrationNumber,
	                   String studentName,
	                   SubjectMarks[] subjects) {

	        this.registrationNumber = registrationNumber;
	        this.studentName = studentName;
	        this.subjects = subjects;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getRegistrationNumber() {
	        return registrationNumber;
	    }

	    public void setRegistrationNumber(String registrationNumber) {
	        this.registrationNumber = registrationNumber;
	    }

	    public String getStudentName() {
	        return studentName;
	    }

	    public void setStudentName(String studentName) {
	        this.studentName = studentName;
	    }

	    public SubjectMarks[] getSubjects() {
	        return subjects;
	    }

	    public void setSubjects(SubjectMarks[] subjects) {
	        this.subjects = subjects;
	    }
}
