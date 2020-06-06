package io.github.oliviercailloux.teach_spreadsheets.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class Model {
	private static Set<CoursePrefElement> allPreferences;
	private static Set<CoursePrefElement> chosenPreferences;

	/**
	 * 
	 * @param calcData
	 */
	public static void setData(CalcData calcData) {
		allPreferences = new HashSet<>();
		chosenPreferences = new HashSet<>();
		ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
		for (CoursePref coursePref : coursePrefs) {
			CourseType courseType = CourseType.CM;
			for (int i = 0; i < coursePref.getPrefNbGroupsCM(); i++) {
				allPreferences.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.TD;
			for (int i = 0; i < coursePref.getPrefNbGroupsTD(); i++) {
				allPreferences.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.TP;
			for (int i = 0; i < coursePref.getPrefNbGroupsTP(); i++) {
				allPreferences.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.CMTD;
			for (int i = 0; i < coursePref.getPrefNbGroupsCMTD(); i++) {
				allPreferences.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.CMTP;
			for (int i = 0; i < coursePref.getPrefNbGroupsCMTP(); i++) {
				allPreferences.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
		}
	}

	public static Set<CoursePrefElement> getAllPreferences() {
		return allPreferences;
	}

	public static Set<CoursePrefElement> getChosenPreferences() {
		return chosenPreferences;
	}
	
	/**
	 * Updates sets of CoursePrefElement thanks to the data retrieved from a table item
	 * @param texts the data retrieved from the table item. texts size is 4 : first element is teacher name, second is course name, third is group type and fourth is teacher choice
	 * @param source the set where we want to find the CoursePrefElement object corresponding to texts
	 * @param target the set where we want to add the CoursePrefElement object corresponding to texts
	 */
	private static void updateSet(ArrayList<String> texts, Set<CoursePrefElement> source, Set<CoursePrefElement> target) {
		
		for (CoursePrefElement coursePrefElement : source) {
			
			CoursePref coursePref = coursePrefElement.getCoursePref();
			Teacher teacher = coursePref.getTeacher();
			Course course = coursePref.getCourse();

			String teacherName = teacher.getLastName() + " " + teacher.getFirstName();
			String courseName = course.getName();
			String groupType = coursePrefElement.getCourseType().name();
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

			if (teacherName.equals(texts.get(0)) && courseName.equals(texts.get(1))
					&& groupType.equals(texts.get(2)) && choice.equals(texts.get(3))) {
				source.remove(coursePrefElement);
				target.add(coursePrefElement);
				break;
			}
		}
	}

	/**
	 * This method is called from Controller class when a table item has been clicked.
	 * Updates the data from Model thanks to the table item data.
	 * @param texts               the strings retrieved from the table item. texts'
	 *                            size is 4 : first element is teacher name, second is
	 *                            course name, third is group type and fourth is teacher choice
	 * @param toChosenPreferences true iff the element that has been clicked is on
	 *                            the Table named all preferences
	 */
	public static void updatePreferences(ArrayList<String> texts, boolean toChosenPreferences) {
		if (toChosenPreferences) {
			updateSet(texts, allPreferences, chosenPreferences);
		}
		else {
			updateSet(texts, chosenPreferences, allPreferences);
		}
	}
}
