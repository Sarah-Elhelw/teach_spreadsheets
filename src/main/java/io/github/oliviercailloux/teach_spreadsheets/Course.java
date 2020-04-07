package io.github.oliviercailloux.teach_spreadsheets;

import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable
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
		
		semester = 1;
		
		// by default, nbHours and countGroups variables are initialized at zero
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

        public static Builder newInstance() {
        	return new Builder();
        }
        
        private Builder() {
            courseToBuild = new Course();
        }

        public Course build() {
        	checkNotNull(courseToBuild.name);
        	checkNotNull(courseToBuild.studyYear);
        	checkNotNull(courseToBuild.semester);
        	checkArgument(
        			courseToBuild.countGroupsCM + 
        			courseToBuild.countGroupsCMTD +
        			courseToBuild.countGroupsCMTP +
        			courseToBuild.countGroupsTD +
        			courseToBuild.countGroupsTP > 0);
        	checkArgument(
        			courseToBuild.nbHoursCM + 
        			courseToBuild.nbHoursCMTD +
        			courseToBuild.nbHoursCMTP +
        			courseToBuild.nbHoursTD +
        			courseToBuild.nbHoursTP > 0);

            return courseToBuild;
        }
        
        public Builder setName(String name) throws NullPointerException {
        	checkNotNull(name, EXCEPTION_STRING);
    		this.courseToBuild.name = name;
    		return this;
    	}
        
    	public Builder setStudyYear(String studyYear) throws NullPointerException {
    		checkNotNull(studyYear, EXCEPTION_STRING);
    		this.courseToBuild.studyYear = studyYear;
    		return this;
    	}
    	
    	public Builder setSemester(int semester) throws IllegalArgumentException {
    		checkArgument(semester == 0 || semester == 1, "int must be 1 or 2.");
    		this.courseToBuild.semester = semester;
    		return this;
    	}
    	
    	public Builder setCountGroupsTD(int countGroupsTD) throws IllegalArgumentException {
    		checkArgument(countGroupsTD >= 0, EXCEPTION_INT);
    		this.courseToBuild.countGroupsTD = countGroupsTD;
    		return this;
    	}
    	
    	public Builder setCountGroupsTP(int countGroupsTP) throws IllegalArgumentException {
    		checkArgument(countGroupsTP >= 0, EXCEPTION_INT);
    		this.courseToBuild.countGroupsTP = countGroupsTP;
    		return this;
    	}
    	
    	public Builder setCountGroupsCMTD(int countGroupsCMTD) throws IllegalArgumentException {
    		checkArgument(countGroupsCMTD >= 0, EXCEPTION_INT);
    		this.courseToBuild.countGroupsCMTD = countGroupsCMTD;
    		return this;
    	}
    	
    	public Builder setCountGroupsCMTP(int countGroupsCMTP) throws IllegalArgumentException {
    		checkArgument(countGroupsCMTP >= 0, EXCEPTION_INT);
    		this.courseToBuild.countGroupsCMTP = countGroupsCMTP;
    		return this;
    	}
    	
    	public Builder setCountGroupsCM(int countGroupsCM) throws IllegalArgumentException {
    		checkArgument(countGroupsCM >= 0, EXCEPTION_INT);
    		this.courseToBuild.countGroupsCM = countGroupsCM;
    		return this;
    	}
    	
    	public Builder setNbHoursTD(int nbHoursTD) throws IllegalArgumentException {
    		checkArgument(nbHoursTD >= 0, EXCEPTION_INT);
    		this.courseToBuild.nbHoursTD = nbHoursTD;
    		return this;
    	}
    	
    	public Builder setNbHoursTP(int nbHoursTP) throws IllegalArgumentException {
    		checkArgument(nbHoursTP >= 0, EXCEPTION_INT);
    		this.courseToBuild.nbHoursTP = nbHoursTP;
    		return this;
    	}
    	
    	public Builder setNbHoursCMTD(int nbHoursCMTD) throws IllegalArgumentException {
    		checkArgument(nbHoursCMTD >= 0, EXCEPTION_INT);
    		this.courseToBuild.nbHoursCMTD = nbHoursCMTD;
    		return this;
    	}
    	
    	public Builder setNbHoursCMTP(int nbHoursCMTP) throws IllegalArgumentException {
    		checkArgument(nbHoursCMTP >= 0, EXCEPTION_INT);
    		this.courseToBuild.nbHoursCMTP = nbHoursCMTP;
    		return this;
    	}
    	
    	public Builder setNbHoursCM(int nbHoursCM) throws IllegalArgumentException {
    		checkArgument(nbHoursCM >= 0, EXCEPTION_INT);
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
