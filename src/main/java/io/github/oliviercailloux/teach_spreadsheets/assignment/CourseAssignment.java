package io.github.oliviercailloux.teach_spreadsheets.assignment;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import java.util.Set;
import java.util.LinkedHashSet;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Immutable. This class assigns a set of teachers'assignments to a course.
 *
 */
public class CourseAssignment {
	private final Course course;
	/**
	 * Set of {@link TeacherAssignment} : we should not be able to assign more than
	 * once a teacher's assignment to a course.
	 */
	private ImmutableSet<TeacherAssignment> finalTeacherAssignments;

	private CourseAssignment(Course course) {
		this.course = Preconditions.checkNotNull(course, "The course must not be null.");
	}

	private CourseAssignment(Course course, ImmutableSet<TeacherAssignment> finalTeacherAssignments) {
		this.course = Preconditions.checkNotNull(course, "The course must not be null.");
		this.finalTeacherAssignments = Preconditions.checkNotNull(finalTeacherAssignments,
				"The teachers'assignments must not be null");
	}

	public static CourseAssignment newInstance(Course course, ImmutableSet<TeacherAssignment> finalTeacherAssignments) {
		return new CourseAssignment(course, finalTeacherAssignments);
	}

	public static class Builder {
		/**
		 * Set of {@link TeacherAssignment} : it is used to build (by adding) the
		 * finalTeacherAssignments.
		 */
		private Set<TeacherAssignment> teacherAssignments;
		private CourseAssignment courseAssignmentToBuild;

		private Builder(Course course) {
			teacherAssignments = new LinkedHashSet<>();
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
		 */
		public CourseAssignment build() {
			courseAssignmentToBuild.finalTeacherAssignments = ImmutableSet.copyOf(teacherAssignments);
			return courseAssignmentToBuild;
		}

		/**
		 * Adds a teacher's assignment to the teacherAssignments. The total numbers of assigned TD, TP, CMTD, 
		 * CMTP and CM groups to the course must not exceed the numbers of TD, TP, CMTD, CMTP and CM groups that 
		 * are associated to the given course.
		 * Moreover, we cannot add a teacher's assignment were the numbers of assigned TD, TP, CMTD, CMTP and CM are
		 * all equal to zero.
		 * 
		 * @param teacherAssignment - the object representing a new teacher's assignment to the list of
		 * teacher's assignments.
		 * 
		 * @throws NullPointerException if the parameter is null
		 * @throws IllegalArgumentException if the total numbers of assigned TD, TP, CMTD, CMTP and CM groups 
		 * to the course exceed the numbers of TD, TP, CMTD, CMTP and CM groups that are associated 
		 * to the given course or if the numbers of assigned TD, TP, CMTD, CMTP and CM of the new teacher's
		 * assignment are all equal to zero.
		 */
		public void addTeacherAssignment(TeacherAssignment teacherAssignment) {
			Preconditions.checkNotNull(courseAssignmentToBuild.course, "The Course must be set first.");
			Preconditions.checkNotNull(teacherAssignment, "The teacherAssignment must not be null.");

			int sumAssignedGroupsTD = 0;
			int sumAssignedGroupsTP = 0;
			int sumAssignedGroupsCMTD = 0;
			int sumAssignedGroupsCMTP = 0;
			int sumAssignedGroupsCM = 0;

			for (TeacherAssignment ta : teacherAssignments) {
				sumAssignedGroupsTD += ta.getCountGroupsTD();
				sumAssignedGroupsTP += ta.getCountGroupsTP();
				sumAssignedGroupsCMTD += ta.getCountGroupsCMTD();
				sumAssignedGroupsCMTP += ta.getCountGroupsCMTP();
				sumAssignedGroupsCM += ta.getCountGroupsCM();
			}
			
			Preconditions.checkArgument(sumAssignedGroupsTD+teacherAssignment.getCountGroupsTD()<=courseAssignmentToBuild.course.getCountGroupsTD(), "The number of assigned TD groups must not exceed the number of TD groups associated to the course.");
			Preconditions.checkArgument(sumAssignedGroupsTP+teacherAssignment.getCountGroupsTP()<=courseAssignmentToBuild.course.getCountGroupsTP(), "The number of assigned TP groups must not exceed the number of TP groups associated to the course.");
			Preconditions.checkArgument(sumAssignedGroupsCMTD+teacherAssignment.getCountGroupsCMTD()<=courseAssignmentToBuild.course.getCountGroupsCMTD(), "The number of assigned CMTD groups must not exceed the number of CMTD groups associated to the course.");
			Preconditions.checkArgument(sumAssignedGroupsCMTP+teacherAssignment.getCountGroupsCMTP()<=courseAssignmentToBuild.course.getCountGroupsCMTP(), "The number of assigned CMTP groups must not exceed the number of CMTP groups associated to the course.");
			Preconditions.checkArgument(sumAssignedGroupsCM+teacherAssignment.getCountGroupsCM()<=courseAssignmentToBuild.course.getCountGroupsCM(), "The number of assigned CM groups must not exceed the number of CM groups associated to the course.");
			
			Preconditions.checkArgument(teacherAssignment.getCountGroupsTD()!=0 || teacherAssignment.getCountGroupsTP()!=0 || teacherAssignment.getCountGroupsCMTD()!=0 || teacherAssignment.getCountGroupsCMTP()!=0 || teacherAssignment.getCountGroupsCM()!=0, "An assignment must have at least one assigned group.");
			
			teacherAssignments.add(teacherAssignment);
		}
	}
	
	public Course getCourse() {
		return course;
	}
	
	public Set<TeacherAssignment> getTeacherAssignments(){
		return finalTeacherAssignments;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
	       .add("Course", course.getName())
	       .add("List of Teachers'Assignments", finalTeacherAssignments)
	       .toString();
	}
}
