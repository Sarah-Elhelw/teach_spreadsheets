package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class CourseAndPrefReader {
	public static CourseAndPrefReader newInstance(){
		return new CourseAndPrefReader();
	}
	private CourseAndPrefReader() {
		
	}
	public boolean isThereANextCourse(Table sheet) {
		TODO();
		return false;
	}
	public CoursePref nextPref(Table sheet) {
		TODO();
		return null;
	}
	public Course nextCourse(Table sheet) {
		TODO();
		return null;
	}


}
