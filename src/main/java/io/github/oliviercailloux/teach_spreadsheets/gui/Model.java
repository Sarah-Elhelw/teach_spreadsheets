package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.SubCourseKind;

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

	private Model() {
	}

	/**
	 * Adds CoursePrefElement objects to allPreferences
	 * 
	 * @param subCourseKind the SubCourseKind of the CoursePrefElement objects to be added
	 * @param coursePref the CoursePref of the CoursePrefElement objects to be added
	 * @param nbGroups   the number of elements to be added
	 */
	private void addToAllPreferences(SubCourseKind subCourseKind, CoursePref coursePref, int nbGroups) {
		checkNotNull(subCourseKind);
		checkNotNull(coursePref);
		checkNotNull(allPreferences);

		for (int i = 0; i < nbGroups; i++)
			allPreferences.add(CoursePrefElement.newInstance(subCourseKind, coursePref));
	}

	/**
	 * Populates allPreferences from a TeacherPrefs instance
	 * 
	 * @param teacherPrefs
	 */
	private void setData(TeacherPrefs teacherPrefs) {
		checkNotNull(teacherPrefs);
		checkNotNull(allPreferences);

		ImmutableSet<CoursePref> coursePrefs = teacherPrefs.getCoursePrefs();
		for (CoursePref coursePref : coursePrefs) {
			addToAllPreferences(SubCourseKind.CM, coursePref, coursePref.getPrefNbGroupsCM());
			addToAllPreferences(SubCourseKind.TD, coursePref, coursePref.getPrefNbGroupsTD());
			addToAllPreferences(SubCourseKind.TP, coursePref, coursePref.getPrefNbGroupsTP());
			addToAllPreferences(SubCourseKind.CMTD, coursePref, coursePref.getPrefNbGroupsCMTD());
			addToAllPreferences(SubCourseKind.CMTP, coursePref, coursePref.getPrefNbGroupsCMTP());
		}
	}

	/**
	 * Populates allPreferences from a set of TeacherPrefs instance
	 * 
	 * @param teacherPrefsSet
	 */
	public void setDataFromSet(Set<TeacherPrefs> teacherPrefsSet) {
		checkNotNull(teacherPrefsSet);
		checkNotNull(allPreferences);
		for (TeacherPrefs teacherPrefs : teacherPrefsSet) {
			setData(teacherPrefs);
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
