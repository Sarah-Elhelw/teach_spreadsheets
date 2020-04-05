package io.github.oliviercailloux.teach_spreadsheets;

import java.io.Reader;
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
	 * Gets the data from a JSON API
	 * @param filePath the path to the json file
	 * @return the JSON string
	 */
	public static String getDataFromJSON(Path filePath) {
		return null;
	}
	
	/**
	 * Gets the data from a CSV file
	 * @param filePath the path to the CSV file
	 * @return a file reader
	 */
	public static Reader getDataFromCSV(Path filePath) {
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
	
	public ArrayList<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}
}
