package io.github.oliviercailloux.teach_spreadsheets;

import java.io.Reader;
import java.nio.file.Path;

import com.google.common.collect.ImmutableSet;

/**
 * Immutable.
 * Class used to store information from an excel spreadsheet : one teacher, multiple courses and his/her preferences for these courses.
 *
 */
public class CalcData {
	private ImmutableSet<CoursePref> coursePrefs;
	private Teacher teacher;
	
	private CalcData(ImmutableSet<CoursePref> coursePrefs, Teacher teacher) {
		this.coursePrefs = coursePrefs;
		this.teacher = teacher;
	}
	
	public static CalcData newInstance(ImmutableSet<CoursePref> coursePrefs, Teacher teacher) {
		return new CalcData(coursePrefs, teacher);
	}
	
	/**
	 * Gets the data from a JSON API
	 * @param filePath the path to the json file
	 * @return the JSON string
	 */
	public static String fromJSON(String filePath) {
		return null;
	}
	
	/**
	 * Gets the data from a CSV file
	 * @param filePath the path to the CSV file
	 * @return a file reader
	 */
	public static Reader fromCSV(Path filePath) {
		return null;
	}
	
	/**
	 * @param data is the parsed data returned by getDataFromJSON
	 * @return an instance of CalcData containing the information from the parameter data
	 */
	public static CalcData populateDataFromJSON(String data) {
		return null;
	}
	
	/**
	 * @param data is the parsed data returned by getDataFromCSV
	 * @return an instance of CalcData containing the information from the parameter data
	 */
	public static CalcData populateDataFromCSV(Reader data) {
		return null;
	}
	
	public ImmutableSet<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}
}
