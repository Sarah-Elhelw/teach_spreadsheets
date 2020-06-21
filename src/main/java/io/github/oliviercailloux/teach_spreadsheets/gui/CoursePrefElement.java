package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

/**
 * Represents the assignment of one group of CM, TD, CMTP, CMTD or TP for a
 * teacher and a course This structure is used to keep the Model updated for the
 * GUI
 */
public class CoursePrefElement {
	private final CourseType courseType;
	private final CoursePref coursePref;

	private CoursePrefElement(CourseType courseType, CoursePref coursePref) {
		checkNotNull(courseType);
		checkNotNull(coursePref);
		this.coursePref = coursePref;
		this.courseType = courseType;
	}

	public static CoursePrefElement newInstance(CourseType courseType, CoursePref coursePref) {
		return new CoursePrefElement(courseType, coursePref);
	}

	public CoursePref getCoursePref() {
		return coursePref;
	}

	public CourseType getCourseType() {
		return courseType;
	}
}
