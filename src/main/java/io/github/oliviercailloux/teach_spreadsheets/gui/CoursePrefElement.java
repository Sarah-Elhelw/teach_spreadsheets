package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * Represents the assignment of one group of CM, TD, CMTP, CMTD or TP for a
 * teacher and a course This structure is used to keep the Model updated for the
 * GUI
 */
public class CoursePrefElement {
	private CourseType courseType;
	private CoursePref coursePref;

	private CoursePrefElement() {
	}

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

	@Override
	public String toString() {
		Teacher teacher = coursePref.getTeacher();
		Course course = coursePref.getCourse();

		String teacherName = teacher.getLastName() + " " + teacher.getFirstName();
		String courseName = course.getName();
		String groupType = getCourseType().name();
		String choice = "";
		switch (groupType) {
			case "CM":
				choice = coursePref.getPrefCM().name();
				break;
			case "TD":
				choice = coursePref.getPrefTD().name();
				break;
			case "CMTD":
				choice = coursePref.getPrefCMTD().name();
				break;
			case "TP":
				choice = coursePref.getPrefTP().name();
				break;
			case "CMTP":
				choice = coursePref.getPrefCMTP().name();
				break;
			default:
		}
		
		return teacherName + ", " + courseName + ", " + groupType + ", " + choice;
	}
}
