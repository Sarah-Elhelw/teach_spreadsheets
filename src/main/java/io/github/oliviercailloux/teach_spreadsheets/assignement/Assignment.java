package io.github.oliviercailloux.teach_spreadsheets.assignement;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

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
	
	public void addCourseAssignment(CourseAssignment courseAssignment) {
		listOfCourseAssignments.add(Objects.requireNonNull(courseAssignment, "The courseAssignment must not be null."));
	}
}
