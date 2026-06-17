package model;

public class SubjectMarks {
	 private int science;
	    private int maths;
	    private int computerScience;

	    public SubjectMarks() {
	    }

	    public SubjectMarks(int science, int maths, int computerScience) {
	        this.science = science;
	        this.maths = maths;
	        this.computerScience = computerScience;
	    }

	    public int getScience() {
	        return science;
	    }

	    public void setScience(int science) {
	        this.science = science;
	    }

	    public int getMaths() {
	        return maths;
	    }

	    public void setMaths(int maths) {
	        this.maths = maths;
	    }

	    public int getComputerScience() {
	        return computerScience;
	    }

	    public void setComputerScience(int computerScience) {
	        this.computerScience = computerScience;
	    }
}
