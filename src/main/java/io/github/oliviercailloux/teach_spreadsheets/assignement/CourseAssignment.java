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
		listOfTeacherAssignments = new ArrayList<>();
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = Objects.requireNonNull(course, "The course must not be null.");
	}
	
	public List<TeacherAssignment> getListOfTeacherAssignments(){
		return listOfTeacherAssignments;
	}
	
	/**
	 * Adds a teacher's assignment to the listOfTeacherAssignments. The total number of assigned TD, TP, CMTD, 
	 * CMTP and CM groups to the course must not exceed the number of TD, TP, CMTD, CMTP and CM groups that 
	 * are associated to the given course.
	 * 
	 * @param teacherAssignment
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
		
		Preconditions.checkArgument(sumAssignedGroupsTD+teacherAssignment.getCountGroupsTD()<=course.getCountGroupsTD(), "The number of TD groups assigned must not exceed the number of TD groups assoicated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsTP+teacherAssignment.getCountGroupsTP()<=course.getCountGroupsTP(), "The number of TP groups assigned must not exceed the number of TP groups assoicated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsCMTD+teacherAssignment.getCountGroupsCMTD()<=course.getCountGroupsCMTD(), "The number of CMTD groups assigned must not exceed the number of CMTD groups assoicated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsCMTP+teacherAssignment.getCountGroupsCMTP()<=course.getCountGroupsCMTP(), "The number of CMTP groups assigned must not exceed the number of CMTP groups assoicated to the course.");
		Preconditions.checkArgument(sumAssignedGroupsCM+teacherAssignment.getCountGroupsCM()<=course.getCountGroupsCM(), "The number of CM groups assigned must not exceed the number of CM groups assoicated to the course.");
		
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
