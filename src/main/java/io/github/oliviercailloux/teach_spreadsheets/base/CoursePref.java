package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * ImmutableSet.
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
	private Preference prefCM;
	private Preference prefTD;
	private Preference prefCMTD;
	private Preference prefTP;
	private Preference prefCMTP;
	
	/**
	 * Number of groups that the teacher wants to have
	 */
	private int prefNbGroupsCM;
	private int prefNbGroupsTD;
	private int prefNbGroupsCMTD;
	private int prefNbGroupsTP;
	private int prefNbGroupsCMTP;
	
	private CoursePref() {
		course = Course.Builder.newInstance().build();
		teacher = Teacher.Builder.newInstance().build();
		
		prefCM = Preference.UNSPECIFIED;
		prefTD = Preference.UNSPECIFIED;
		prefCMTD = Preference.UNSPECIFIED;
		prefTP = Preference.UNSPECIFIED;
		prefCMTP = Preference.UNSPECIFIED;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public Preference getPrefCM() {
		return prefCM;
	}
	
	public Preference getPrefTD() {
		return prefTD;
	}
	
	public Preference getPrefCMTD() {
		return prefCMTD;
	}
	
	public Preference getPrefTP() {
		return prefTP;
	}
	
	public Preference getPrefCMTP() {
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

        public static Builder newInstance() {
        	return new Builder();
        }
        
        private Builder() {
            coursePrefToBuild = new CoursePref();
        }

        public CoursePref build() {
            checkNotNull(coursePrefToBuild.course.getName());
            checkNotNull(coursePrefToBuild.teacher.getLastName());
            return coursePrefToBuild;
        }
        
        public Builder setCourse(Course course) throws NullPointerException {
        	checkNotNull(course, "Course needs to be instanciated.");
    		this.coursePrefToBuild.course = course;
    		return this;
        }
        
    	public Builder setTeacher(Teacher teacher) throws NullPointerException {
    		checkNotNull(teacher, "Teacher needs to be instanciated.");
    		this.coursePrefToBuild.teacher = teacher;
    		return this;
    	}
    	
    	public Builder setPrefCM(Preference prefCM) {
    		this.coursePrefToBuild.prefCM = prefCM;
    		return this;
    	}
    	
    	public Builder setPrefTD(Preference prefTD) {
    		this.coursePrefToBuild.prefTD = prefTD;
    		return this;
    	}
    	
    	public Builder setPrefCMTD(Preference prefCMTD) {
    		this.coursePrefToBuild.prefCMTD = prefCMTD;
    		return this;
    	}
    	
    	public Builder setPrefTP(Preference prefTP) {
    		this.coursePrefToBuild.prefTP = prefTP;
    		return this;
    	}
    	
    	public Builder setPrefCMTP(Preference prefCMTP) {
    		this.coursePrefToBuild.prefCMTP = prefCMTP;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsCM(int prefNbGroupsCM) throws IllegalArgumentException {
    		checkArgument(prefNbGroupsCM >= 0, EXCEPTION);
    		this.coursePrefToBuild.prefNbGroupsCM = prefNbGroupsCM;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsTD(int prefNbGroupsTD) throws IllegalArgumentException {
    		checkArgument(prefNbGroupsTD >= 0, EXCEPTION);
    		this.coursePrefToBuild.prefNbGroupsTD = prefNbGroupsTD;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsCMTD(int prefNbGroupsCMTD) throws IllegalArgumentException {
    		checkArgument(prefNbGroupsCMTD >= 0, EXCEPTION);
    		this.coursePrefToBuild.prefNbGroupsCMTD = prefNbGroupsCMTD;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsTP(int prefNbGroupsTP) throws IllegalArgumentException {
    		checkArgument(prefNbGroupsTP >= 0, EXCEPTION);
    		this.coursePrefToBuild.prefNbGroupsTP = prefNbGroupsTP;
    		return this;
    	}
    	
    	public Builder setPrefNbGroupsCMTP(int prefNbGroupsCMTP) throws IllegalArgumentException {
    		checkArgument(prefNbGroupsCMTP >= 0, EXCEPTION);
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
