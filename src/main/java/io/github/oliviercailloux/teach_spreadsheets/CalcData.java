package io.github.oliviercailloux.teach_spreadsheets;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

/**
 * Immutable.
 * Class used to store information from an excel spreadsheet : one teacher, multiple courses and his/her preferences for these courses.
 */
public class CalcData {
	private ImmutableSet<CoursePref> coursePrefs;
	private Teacher teacher;
	
	/**
	 * Gets the data from a JSON API
	 * @param filePath the path to the json file
	 * @return the JSON string
	 */
	public static String fromJSON(String filePath) {
		return null;
	}
	
	/**
	 * @param data is the parsed data returned by getDataFromJSON
	 * @return an instance of CalcData containing the information from the parameter data
	 */
	public static CalcData populateDataFromJSON(String data) {
		return null;
	}
	
	public ImmutableSet<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}
	
	/**
	 * @param courseName the name of a Course
	 * @return the CoursePref corresponding to courseName
	 */
	public CoursePref getCoursePref(String courseName) {
		for (CoursePref coursePref : coursePrefs) {
			if (coursePref.getCourse().getName().contentEquals(courseName)) return coursePref; 
		}
		return null;
	}
	
	private CalcData(ImmutableSet<CoursePref> coursePrefs, Teacher teacher) {
		this.coursePrefs = coursePrefs;
		this.teacher = teacher;
	}
	
	public static CalcData newInstance(ImmutableSet<CoursePref> coursePrefs, Teacher teacher) {
		// checks that we don't have two CoursePref containing the same course
		for (CoursePref coursePref : coursePrefs) {
			for (CoursePref otherCoursePref : coursePrefs) {
				if (
						coursePref != otherCoursePref && 
						coursePref.getCourse().getName().equals(otherCoursePref.getCourse().getName())) {
					throw new IllegalArgumentException("You can't have two courses of the same name.");
				}
			}
		}
		
		return new CalcData(coursePrefs, teacher);
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(this)
			       .add("coursePrefs", coursePrefs)
			       .add("teacher", teacher)
			       .toString();
	}
}
