package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

/**
 * Model contains the preferences that are supposed to be shown in the UI.
 */
public class Model {
	private Set<CoursePrefElement> allPreferences;
	private Set<CoursePrefElement> chosenPreferences;
	
	public static Model newInstance() {
		Model model = new Model();
		model.allPreferences = new HashSet<>();
		model.chosenPreferences = new HashSet<>();
		return model;
	}
	
	private Model() {}

	/**
	 * Adds CoursePrefElement objects to allPreferences
	 * 
	 * @param courseType the CourseType of the CoursePrefElement objects to be added
	 * @param coursePref the CoursePref of the CoursePrefElement objects to be added
	 * @param nbGroups   the number of elements to be added
	 */
	private void addToAllPreferences(CourseType courseType, CoursePref coursePref, int nbGroups) {
		checkNotNull(courseType);
		checkNotNull(coursePref);
		checkNotNull(allPreferences);

		for (int i = 0; i < nbGroups; i++)
			allPreferences.add(CoursePrefElement.newInstance(courseType, coursePref));
	}

	/**
	 * Populates allPreferences from a CalcData instance
	 * 
	 * @param calcData
	 */
	private void setData(CalcData calcData) {
		checkNotNull(calcData);
		checkNotNull(allPreferences);

		ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
		for (CoursePref coursePref : coursePrefs) {
			addToAllPreferences(CourseType.CM, coursePref, coursePref.getPrefNbGroupsCM());
			addToAllPreferences(CourseType.TD, coursePref, coursePref.getPrefNbGroupsTD());
			addToAllPreferences(CourseType.TP, coursePref, coursePref.getPrefNbGroupsTP());
			addToAllPreferences(CourseType.CMTD, coursePref, coursePref.getPrefNbGroupsCMTD());
			addToAllPreferences(CourseType.CMTP, coursePref, coursePref.getPrefNbGroupsCMTP());
		}
	}

	/**
	 * Populates allPreferences from a set of CalcData instance
	 * 
	 * @param calcData
	 */
	public void setDataFromSet(Set<CalcData> calcDataSet) {
		checkNotNull(calcDataSet);
		checkNotNull(allPreferences);
		for (CalcData calcData : calcDataSet) {
			setData(calcData);
		}
	}

	public Set<CoursePrefElement> getAllPreferences() {
		return allPreferences;
	}

	public Set<CoursePrefElement> getChosenPreferences() {
		return chosenPreferences;
	}
	
	/**
	 * @return all of the courses contained in allPreferences
	 */
	public Set<Course> getCourses() {
		HashSet<Course> courses = new HashSet<>();
		
		for (CoursePrefElement coursePrefElement : allPreferences) {
			Course course = coursePrefElement.getCoursePref().getCourse();
			courses.add(course);
		}
		
		return courses;
	}
}
