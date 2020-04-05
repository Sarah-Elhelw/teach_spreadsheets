package io.github.oliviercailloux.teach_spreadsheets;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Class used to store information from an excel spreadsheet : one teacher, multiple courses and his/her preferences for these courses.
 *
 */
public class CalcData {
	private ArrayList<CoursePref> coursePrefs;
	private Teacher teacher;
	
	/**
	 * Gets the data from an excel spreadsheet or JSON API
	 * @param filePath the path to the json file or excel spreadsheet
	 * @return the parsed data
	 */
	public static Object getData(Path filePath) {
		return null;
	}
	
	/**
	 * populates the data
	 * @param data is the parsed data returned by getData
	 */
	public static void populateData(Object data) {
		
	}
	
	public ArrayList<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}
}
