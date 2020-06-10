package io.github.oliviercailloux.teach_spreadsheets.assignment;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;

import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableSet;
import java.util.logging.Level;


/**
 * Immutable. This class assigns a set of teachers'assignments to a course.
 *
 */
public class CourseAssignment {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseAssignment.class);
	
	private final Course course;
	
	private ImmutableSet<TeacherAssignment> teacherAssignments;

	private CourseAssignment(Course course) {
		this.course = checkNotNull(course, "The course must not be null.");
	}

	/**
	 * The second parameter is declared as a Set to remind the user that there should be no
	 * duplicates of teachers'assignments in a course assignment.
	 * 
	 */
	public static CourseAssignment newInstance(Course course, Set<TeacherAssignment> finalTeacherAssignments) {
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course);
		checkNotNull(finalTeacherAssignments, "The teachers'assignments must not be null");
		for (TeacherAssignment ta : finalTeacherAssignments) {
			courseAssignmentBuilder.addTeacherAssignment(ta);
		}
		CourseAssignment courseAssignment = courseAssignmentBuilder.build();
		
		return courseAssignment;
	}

	public static class Builder {
		/**
		 * Set of {@link TeacherAssignment} : it is used to build (by adding) the
		 * finalTeacherAssignments.
		 */
		private Set<TeacherAssignment> tempTeacherAssignments;
		private CourseAssignment courseAssignmentToBuild;

		private Builder(Course course) {
			tempTeacherAssignments = new LinkedHashSet<>();
			courseAssignmentToBuild = new CourseAssignment(course);
		}

		public static Builder newInstance(Course course) {
			return new Builder(course);
		}

		/**
		 * Builds a CourseAssignment.
		 * 
		 * @return courseAssignmentToBuild - the CourseAssignment that was built.
		 * 
		 * @throws IllegalStateException if the set of teachers'assignments is empty.
		 * 
		 */
		public CourseAssignment build() {
			checkState(tempTeacherAssignments.size() >= 1, "The course assignment must contain at least one teacher assignment.");
			courseAssignmentToBuild.teacherAssignments = ImmutableSet.copyOf(tempTeacherAssignments);
			return courseAssignmentToBuild;
		}

		/**
		 * Adds a teacher's assignment to the teacherAssignments. The total numbers of
		 * assigned TD, TP, CMTD, CMTP and CM groups to the course must not exceed the
		 * numbers of TD, TP, CMTD, CMTP and CM groups that are associated to the given
		 * course. Moreover, we cannot add a teacher's assignment were the numbers of
		 * assigned TD, TP, CMTD, CMTP and CM are all equal to zero.
		 * 
		 * @param teacherAssignment - the object representing a new teacher's assignment
		 *                          to the set of teacher's assignments.
		 * 
		 * @throws NullPointerException     if the parameter is null
		 * @throws IllegalArgumentException if the total numbers of assigned TD, TP,
		 *                                  CMTD, CMTP and CM groups to the course
		 *                                  exceed the numbers of TD, TP, CMTD, CMTP and
		 *                                  CM groups that are associated to the given
		 *                                  course or if the numbers of assigned TD, TP,
		 *                                  CMTD, CMTP and CM of the new teacher's
		 *                                  assignment are all equal to zero.
		 */
		public void addTeacherAssignment(TeacherAssignment teacherAssignment) {
			checkNotNull(courseAssignmentToBuild.course, "The Course must be set first.");
			checkNotNull(teacherAssignment, "The teacherAssignment must not be null.");

			int sumAssignedGroupsTD = 0;
			int sumAssignedGroupsTP = 0;
			int sumAssignedGroupsCMTD = 0;
			int sumAssignedGroupsCMTP = 0;
			int sumAssignedGroupsCM = 0;

			for (TeacherAssignment ta : tempTeacherAssignments) {
				sumAssignedGroupsTD += ta.getCountGroupsTD();
				sumAssignedGroupsTP += ta.getCountGroupsTP();
				sumAssignedGroupsCMTD += ta.getCountGroupsCMTD();
				sumAssignedGroupsCMTP += ta.getCountGroupsCMTP();
				sumAssignedGroupsCM += ta.getCountGroupsCM();
			}
			
			checkArgument(sumAssignedGroupsTD+teacherAssignment.getCountGroupsTD() <= courseAssignmentToBuild.course.getCountGroupsTD(), "The number of assigned TD groups must not exceed the number of TD groups associated to the course.");
			checkArgument(sumAssignedGroupsTP+teacherAssignment.getCountGroupsTP() <= courseAssignmentToBuild.course.getCountGroupsTP(), "The number of assigned TP groups must not exceed the number of TP groups associated to the course.");
			checkArgument(sumAssignedGroupsCMTD+teacherAssignment.getCountGroupsCMTD() <= courseAssignmentToBuild.course.getCountGroupsCMTD(), "The number of assigned CMTD groups must not exceed the number of CMTD groups associated to the course.");
			checkArgument(sumAssignedGroupsCMTP+teacherAssignment.getCountGroupsCMTP() <= courseAssignmentToBuild.course.getCountGroupsCMTP(), "The number of assigned CMTP groups must not exceed the number of CMTP groups associated to the course.");
			checkArgument(sumAssignedGroupsCM+teacherAssignment.getCountGroupsCM() <= courseAssignmentToBuild.course.getCountGroupsCM(), "The number of assigned CM groups must not exceed the number of CM groups associated to the course.");
			
			checkArgument(teacherAssignment.getCountGroupsTD() != 0 || teacherAssignment.getCountGroupsTP() != 0 || teacherAssignment.getCountGroupsCMTD() !=0 || teacherAssignment.getCountGroupsCMTP() !=0 || teacherAssignment.getCountGroupsCM() !=0, "An assignment must have at least one assigned group.");
			
			tempTeacherAssignments.add(teacherAssignment);
		}
	}
	
	public Course getCourse() {
		return course;
	}
	
	public Set<TeacherAssignment> getTeacherAssignments(){
		return teacherAssignments;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
	       .add("Course", course.getName())
	       .add("Set of Teachers'Assignments", teacherAssignments)
	       .toString();
	}

	
	
	public static void main(String[] args) {

		Logger logger = Logger.getLogger("logger");

		logger.log(Level.INFO, "The CourseAssignment class represents the assignment of only one course to a number of teachers.");
		 

		}
	
}
