package io.github.oliviercailloux.teach_spreadsheets.assignement;

import io.github.oliviercailloux.teach_spreadsheets.Course;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * This class assigns a list of teachers'assignments to a course.
 *  
 * @author sarah
 *
 */
public class CourseAssignment {
	private Course course;
	private List<TeacherAssignment> listOfTeacherAssignments;
	
	public static CourseAssignment newInstance() {
		return new CourseAssignment();
	}
	
	private CourseAssignment() {
		course = new Course.Builder().build();
		listOfTeacherAssignments = new ArrayList<>();
	}
	
	public Course getCourse() {
		return course;
	}
	
	/**
	 * Sets the value of the attribute course. This value must not be null.
	 * 
	 * @param course - the object used to set the value of the attribute course
	 * 
	 * @throws NullPointerException if the parameter is null
	 */
	public void setCourse(Course course) {
		this.course = Objects.requireNonNull(course, "The course must not be null.");
	}
	
	public List<TeacherAssignment> getListOfTeacherAssignments(){
		return listOfTeacherAssignments;
	}
	
	/**
	 * Adds a teacher's assignment to the listOfTeacherAssignments. The total numbers of assigned TD, TP, CMTD, 
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
		Objects.requireNonNull(teacherAssignment, "The teacherAssignment must not be null.");
		
		int sumAssignedGroupsTD=0;
		int sumAssignedGroupsTP=0;
		int sumAssignedGroupsCMTD=0;
		int sumAssignedGroupsCMTP=0;
		int sumAssignedGroupsCM=0;
		
		for (TeacherAssignment ta : listOfTeacherAssignments) {
			sumAssignedGroupsTD+=ta.getCountGroupsTD();
			sumAssignedGroupsTP+=ta.getCountGroupsTP();
			sumAssignedGroupsCMTD+=ta.getCountGroupsCMTD();
			sumAssignedGroupsCMTP+=ta.getCountGroupsCMTP();
			sumAssignedGroupsCM+=ta.getCountGroupsCM();
		}
		
		Preconditions.checkArgument(sumAssignedGroupsTD+teacherAssignment.getCountGroupsTD()<=course.getCountGroupsTD(), "The number of assigned TD groups must not exceed the number of TD groups associated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsTP+teacherAssignment.getCountGroupsTP()<=course.getCountGroupsTP(), "The number of assigned TP groups must not exceed the number of TP groups associated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsCMTD+teacherAssignment.getCountGroupsCMTD()<=course.getCountGroupsCMTD(), "The number of assigned CMTD groups must not exceed the number of CMTD groups associated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsCMTP+teacherAssignment.getCountGroupsCMTP()<=course.getCountGroupsCMTP(), "The number of assigned CMTP groups must not exceed the number of CMTP groups associated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsCM+teacherAssignment.getCountGroupsCM()<=course.getCountGroupsCM(), "The number of assigned CM groups must not exceed the number of CM groups associated to the course.");
		
		Preconditions.checkArgument(teacherAssignment.getCountGroupsTD()!=0 || teacherAssignment.getCountGroupsTP()!=0 || teacherAssignment.getCountGroupsCMTD()!=0 || teacherAssignment.getCountGroupsCMTP()!=0 || teacherAssignment.getCountGroupsCM()!=0);
		
		listOfTeacherAssignments.add(teacherAssignment);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
	       .add("Course", course.getName())
	       .add("List of Teachers'Assignnments", listOfTeacherAssignments)
	       .toString();
	}
}
