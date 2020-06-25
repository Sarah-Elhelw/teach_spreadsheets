package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.SubCourseKind;

/**
 * Represents the assignment of one group of CM, TD, CMTP, CMTD or TP for a
 * teacher and a course This structure is used to keep the Model updated for the
 * GUI
 */
public class CoursePrefElement {
	private final SubCourseKind subCourseKind;
	private final CoursePref coursePref;

	private CoursePrefElement(SubCourseKind subCourseKind, CoursePref coursePref) {
		checkNotNull(subCourseKind);
		checkNotNull(coursePref);
		this.coursePref = coursePref;
		this.subCourseKind = subCourseKind;
	}

	public static CoursePrefElement newInstance(SubCourseKind subCourseKind, CoursePref coursePref) {
		return new CoursePrefElement(subCourseKind, coursePref);
	}

	public CoursePref getCoursePref() {
		return coursePref;
	}

	public SubCourseKind getSubCourseKind() {
		return subCourseKind;
	}
}
