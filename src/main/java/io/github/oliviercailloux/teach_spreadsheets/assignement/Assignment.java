package io.github.oliviercailloux.teach_spreadsheets.assignement;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import com.google.common.base.MoreObjects;



/**
 * This class gathers all the course assignments that were made in a list.
 * 
 * @author sarah
 *
 */
public class Assignment {
	private List<CourseAssignment> listOfCourseAssignments;
	
	public static Assignment newInstance() {
		return new Assignment();
	}
	
	private Assignment() {
		listOfCourseAssignments = new ArrayList<>();
	}
	
	public List<CourseAssignment> getListOfCourseAssignments(){
		return listOfCourseAssignments;
	}
	
	/**
	 * Adds a course assignment to the list of course assignments.
	 * 
	 * @param courseAssignment - the object representing a new course assignment to the list of
	 * course assignments.
	 * 
	 * @throws NullPointerException if the parameter is null
	 */
	public void addCourseAssignment(CourseAssignment courseAssignment) {
		listOfCourseAssignments.add(Objects.requireNonNull(courseAssignment, "The courseAssignment must not be null."));
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
	       .add("List of CourseAssignments", listOfCourseAssignments)
	       .toString();
	}
}
