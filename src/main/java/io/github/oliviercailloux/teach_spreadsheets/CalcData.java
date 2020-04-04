package io.github.oliviercailloux.teach_spreadsheets;

import java.nio.file.Path;
import java.util.ArrayList;

public class CalcData {
	private ArrayList<CoursePref> coursePrefs;
	private Teacher teacher;
	
	public static Object getData(Path filePath) {
		populateData(null);
		return null;
	}
	
	private static void populateData(Object data) {
		
	}
	
	public ArrayList<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}
}
