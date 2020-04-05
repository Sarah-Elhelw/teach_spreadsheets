package io.github.oliviercailloux.teach_spreadsheets;

import com.google.common.base.MoreObjects;

/**
 * Class used to store a teacher's preference for one course.
 * Uses Builder pattern implementation.
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class CoursePref {
	private static final String EXCEPTION = "A preferred number of groups needs to be positive.";
	
	private Course course;
	private Teacher teacher;
	
	/**
	 * Degree of preference of teacher for course, represented by chars
	 */
	private char prefCM;
	private char prefTD;
	private char prefCMTD;
	private char prefTP;
	private char prefCMTP;
	
	/**
	 * Number of groups that the teacher wants to have
	 */
	private int prefNbGroupsCM;
	private int prefNbGroupsTD;
	private int prefNbGroupsCMTD;
	private int prefNbGroupsTP;
	private int prefNbGroupsCMTP;
	
	private CoursePref() {
		course = new Course.Builder().build();
		teacher = new Teacher.Builder().build();
		
		prefCM = ' ';
		prefTD = ' ';
		prefCMTD = ' ';
		prefTP = ' ';
		prefCMTP = ' ';
	}
	
	public Course getCourse() {
		return course;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public char getPrefCM() {
		return prefCM;
	}
	
	public char getPrefTD() {
		return prefTD;
	}
	
	public char getPrefCMTD() {
		return prefCMTD;
	}
	
	public char getPrefTP() {
		return prefTP;
	}
	
	public char getPrefCMTP() {
		return prefCMTP;
	}
	
	public int getPrefNbGroupsCM() {
		return prefNbGroupsCM;
	}
	
	public int getPrefNbGroupsTD() {
		return prefNbGroupsTD;
	}
	
	public int getPrefNbGroupsCMTD() {
		return prefNbGroupsCMTD;
	}
	
	public int getPrefNbGroupsTP() {
		return prefNbGroupsTP;
	}
	
	public int getPrefNbGroupsCMTP() {
		return prefNbGroupsCMTP;
	}
	
	public static class Builder {
        private CoursePref coursePrefToBuild;

        Builder() {
            coursePrefToBuild = new CoursePref();
        }

        CoursePref build() {
            CoursePref builtCoursePref = coursePrefToBuild;
            coursePrefToBuild = new CoursePref();

            return builtCoursePref;
        }
        
        public Builder setCourse(Course course) throws NullPointerException {
        	if (course == null) {
        		throw new NullPointerException("Course needs to be instanciated.");
        	}
    		this.coursePrefToBuild.course = course;
    		return this;
        }
        
    	public Builder setTeacher(Teacher teacher) throws NullPointerException {
    		if (teacher == null) {
    			throw new NullPointerException("Teacher needs to be instanciated.");
    		}
    		this.coursePrefToBuild.teacher = teacher;
    		return this;
    	}
    	
    	public Builder setPrefCM(char prefCM) {
    		this.coursePrefToBuild.prefCM = prefCM;
    		return this;
    	}
    	
    	public Builder setPrefTD(char prefTD) {
    		this.coursePrefToBuild.prefTD = prefTD;
    		return this;
    	}
    	
    	public Builder setPrefCMTD(char prefCMTD) {
    		this.coursePrefToBuild.prefCMTD = prefCMTD;
    		return this;
    	}
    	
    	public Builder setPrefTP(char prefTP) {
    		this.coursePrefToBuild.prefTP = prefTP;
    		return this;
    	}
    	
    	public Builder setPrefCMTP(char prefCMTP) {
    		this.coursePrefToBuild.prefCMTP = prefCMTP;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsCM(int prefNbGroupsCM) throws IllegalArgumentException {
    		if (prefNbGroupsCM < 0) {
    			throw new IllegalArgumentException(EXCEPTION);
    		}
    		this.coursePrefToBuild.prefNbGroupsCM = prefNbGroupsCM;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsTD(int prefNbGroupsTD) throws IllegalArgumentException {
    		if (prefNbGroupsTD < 0) {
    			throw new IllegalArgumentException(EXCEPTION);
    		}
    		this.coursePrefToBuild.prefNbGroupsTD = prefNbGroupsTD;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsCMTD(int prefNbGroupsCMTD) throws IllegalArgumentException {
    		if (prefNbGroupsCMTD < 0) {
    			throw new IllegalArgumentException(EXCEPTION);
    		}
    		this.coursePrefToBuild.prefNbGroupsCMTD = prefNbGroupsCMTD;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsTP(int prefNbGroupsTP) throws IllegalArgumentException {
    		if (prefNbGroupsTP < 0) {
    			throw new IllegalArgumentException(EXCEPTION);
    		}
    		this.coursePrefToBuild.prefNbGroupsTP = prefNbGroupsTP;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsCMTP(int prefNbGroupsCMTP) throws IllegalArgumentException {
    		if (prefNbGroupsCMTP < 0) {
    			throw new IllegalArgumentException(EXCEPTION);
    		}
    		this.coursePrefToBuild.prefNbGroupsCMTP = prefNbGroupsCMTP;
    		return this;
    	}
    }
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
			       .add("prefCM", prefCM)
			       .add("prefTD", prefTD)
			       .add("prefCMTD", prefCMTD)
			       .add("prefTP", prefTP)
			       .add("prefCMTP", prefCMTP)
			       
			       .add("prefNbGroupsCM", prefNbGroupsCM)
			       .add("prefNbGroupsTD", prefNbGroupsTD)
			       .add("prefNbGroupsCMTD", prefNbGroupsCMTD)
			       .add("prefNbGroupsTP", prefNbGroupsTP)
			       .add("prefNbGroupsCMTP", prefNbGroupsCMTP)
			       
			       .add("Course", course.toString())
			       .add("Teacher", teacher.toString())

			       .toString();
	}
}
