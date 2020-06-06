package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

/**
 * Represents the assignment of one group of CM, TD, CMTP, CMTD or TP for a teacher and a course
 * This structure is used to keep the Model updated for the GUI
 */
public class CoursePrefElement {
	private CourseType courseType;
	private CoursePref coursePref;
	
	private CoursePrefElement() {}
	
	public static CoursePrefElement newInstance(CourseType courseType1, CoursePref coursePref1) {
		checkNotNull(courseType1);
		checkNotNull(coursePref1);
		CoursePrefElement coursePrefElement = new CoursePrefElement();
		coursePrefElement.courseType = courseType1;
		coursePrefElement.coursePref = coursePref1;
		return coursePrefElement;
	}
	
	public CoursePref getCoursePref() {
		return coursePref;
	}
	
	public CourseType getCourseType() {
		return courseType;
	}
}
