package io.github.oliviercailloux.teach_spreadsheets;

import com.google.common.base.MoreObjects;

/**
 * Class used to store a course information.
 * Uses Builder pattern implementation.
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class Course {
	private static final String EXCEPTION_STRING = "String must not be null.";
	private static final String EXCEPTION_INT = "int must not be positive.";
	
	private String name;
	private String studyYear;
	private int semester;
	
	private int countGroupsTD;
	private int countGroupsTP;
	private int countGroupsCMTD;
	private int countGroupsCMTP;
	private int countGroupsCM;
	
	private int nbHoursTD;
	private int nbHoursTP;
	private int nbHoursCMTD;
	private int nbHoursCMTP;
	private int nbHoursCM;
	
	private Course() {
		name = "";
		studyYear = "";
	}
	
	public String getName() {
		return name;
	}
	
	public String getStudyYear() {
		return studyYear;
	}
	
	public int getSemester() {
		return semester;
	}
	
	public int getCountGroupsTD() {
		return countGroupsTD;
	}
	
	public int getCountGroupsTP() {
		return countGroupsTP;
	}
	
	public int getCountGroupsCMTD() {
		return countGroupsCMTD;
	}
	
	public int getCountGroupsCMTP() {
		return countGroupsCMTP;
	}
	
	public int getCountGroupsCM() {
		return countGroupsCM;
	}
	
	public int getNbHoursTD() {
		return nbHoursTD;
	}
	
	public int getNbHoursTP() {
		return nbHoursTP;
	}
	
	public int getNbHoursCMTD() {
		return nbHoursCMTD;
	}
	
	public int getNbHoursCMTP() {
		return nbHoursCMTP;
	}
	
	public int getNbHoursCM() {
		return nbHoursCM;
	}
	
	public static class Builder {
        private Course courseToBuild;

        public Builder() {
            courseToBuild = new Course();
        }

        public Course build() {
            Course builtCourse = courseToBuild;
            courseToBuild = new Course();

            return builtCourse;
        }
        
        public Builder setName(String name) throws NullPointerException {
        	if (name == null) {
    			throw new NullPointerException(EXCEPTION_STRING);
    		}
    		this.courseToBuild.name = name;
    		return this;
    	}
        
    	public Builder setStudyYear(String studyYear) throws NullPointerException {
    		if (studyYear == null) {
    			throw new NullPointerException(EXCEPTION_STRING);
    		}
    		this.courseToBuild.studyYear = studyYear;
    		return this;
    	}
    	
    	public Builder setSemester(int semester) throws IllegalArgumentException {
    		if (semester < 0 || semester > 2) {
    			throw new IllegalArgumentException("int must be 1 or 2.");
    		}
    		this.courseToBuild.semester = semester;
    		return this;
    	}
    	
    	public Builder setCountGroupsTD(int countGroupsTD) throws IllegalArgumentException {
    		if (countGroupsTD < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.countGroupsTD = countGroupsTD;
    		return this;
    	}
    	
    	public Builder setCountGroupsTP(int countGroupsTP) throws IllegalArgumentException {
    		if (countGroupsTP < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.countGroupsTP = countGroupsTP;
    		return this;
    	}
    	
    	public Builder setCountGroupsCMTD(int countGroupsCMTD) throws IllegalArgumentException {
    		if (countGroupsCMTD < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.countGroupsCMTD = countGroupsCMTD;
    		return this;
    	}
    	
    	public Builder setCountGroupsCMTP(int countGroupsCMTP) throws IllegalArgumentException {
    		if (countGroupsCMTP < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.countGroupsCMTP = countGroupsCMTP;
    		return this;
    	}
    	
    	public Builder setCountGroupsCM(int countGroupsCM) throws IllegalArgumentException {
    		if (countGroupsCM < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.countGroupsCM = countGroupsCM;
    		return this;
    	}
    	
    	public Builder setNbHoursTD(int nbHoursTD) throws IllegalArgumentException {
    		if (nbHoursTD < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.nbHoursTD = nbHoursTD;
    		return this;
    	}
    	
    	public Builder setNbHoursTP(int nbHoursTP) throws IllegalArgumentException {
    		if (nbHoursTP < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.nbHoursTP = nbHoursTP;
    		return this;
    	}
    	
    	public Builder setNbHoursCMTD(int nbHoursCMTD) throws IllegalArgumentException {
    		if (nbHoursCMTD < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.nbHoursCMTD = nbHoursCMTD;
    		return this;
    	}
    	
    	public Builder setNbHoursCMTP(int nbHoursCMTP) throws IllegalArgumentException {
    		if (nbHoursCMTP < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.nbHoursCMTP = nbHoursCMTP;
    		return this;
    	}
    	
    	public Builder setNbHoursCM(int nbHoursCM) throws IllegalArgumentException {
    		if (nbHoursCM < 0) {
    			throw new IllegalArgumentException(EXCEPTION_INT);
    		}
    		this.courseToBuild.nbHoursCM = nbHoursCM;
    		return this;
    	}
    }
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
			       .add("name", name)
			       
			       .add("countGroupsTD", countGroupsTD)
			       .add("countGroupsCMTD", countGroupsCMTD)
			       .add("countGroupsTP", countGroupsTP)
			       .add("countGroupsCMTP", countGroupsCMTP)
			       .add("countGroupsCM", countGroupsCM)
			       
			       .add("nbHoursTD", nbHoursTD)
			       .add("nbHoursCMTD", nbHoursCMTD)
			       .add("nbHoursTP", nbHoursTP)
			       .add("nbHoursCMTP", nbHoursCMTP)
			       .add("nbHoursCM", nbHoursCM)
			       
			       .add("studyYear", studyYear)
			       .add("semester", semester)
			       
			       .toString();
	}
}
