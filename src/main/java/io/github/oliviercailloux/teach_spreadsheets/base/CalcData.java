package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

/**
 * Immutable. Class used to store information from an excel spreadsheet : one
 * teacher, multiple courses and his/her preferences for these courses.
 */
public class CalcData {
	private ImmutableSet<CoursePref> coursePrefs;
	private Teacher teacher;

	private CalcData(Set<CoursePref> coursePrefs, Teacher teacher) {
		this.coursePrefs = ImmutableSet.copyOf(coursePrefs);
		this.teacher = teacher;
	}

	public static CalcData newInstance(Set<CoursePref> coursePrefs, Teacher teacher) {
		checkNotNull(coursePrefs);
		checkNotNull(teacher);

		for (CoursePref coursePref : coursePrefs) {
			for (CoursePref otherCoursePref : coursePrefs) {
				if (coursePref != otherCoursePref
						&& coursePref.getCourse().getName().equals(otherCoursePref.getCourse().getName())) {
					throw new IllegalArgumentException("You can't have two courses of the same name.");
				}
			}
		}

		return new CalcData(coursePrefs, teacher);
	}

	public ImmutableSet<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param courseName the name of a Course
	 * @return the CoursePref corresponding to courseName, null if not found
	 */
	public CoursePref getCoursePref(String courseName) {
		checkNotNull(coursePrefs);
		checkNotNull(courseName);

		for (CoursePref coursePref : coursePrefs) {
			if (coursePref.getCourse().getName().equals(courseName))
				return coursePref;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("coursePrefs", coursePrefs).add("teacher", teacher).toString();
	}

	/**
	 * Gets the data from a JSON API
	 * 
	 * @param filePath the path to the json file
	 * @return the JSON string
	 */
	public static String fromJSON(String filePath) {
		return null;
	}

	/**
	 * @param data is the parsed data returned by getDataFromJSON
	 * @return an instance of CalcData containing the information from the parameter
	 *         data
	 */
	public static CalcData populateDataFromJSON(String data) {
		return null;
	}
}