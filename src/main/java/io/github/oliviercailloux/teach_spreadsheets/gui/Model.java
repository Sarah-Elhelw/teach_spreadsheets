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
	private static Set<CoursePrefElement> allPreferencesData;
	private static Set<CoursePrefElement> chosenPreferencesData;

	public static void setData(CalcData calcData) {
		allPreferencesData = new HashSet<>();
		chosenPreferencesData = new HashSet<>();
		ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
		for (CoursePref coursePref : coursePrefs) {
			CourseType courseType = CourseType.CM;
			for (int i = 0; i < coursePref.getPrefNbGroupsCM(); i++) {
				allPreferencesData.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.TD;
			for (int i = 0; i < coursePref.getPrefNbGroupsTD(); i++) {
				allPreferencesData.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.TP;
			for (int i = 0; i < coursePref.getPrefNbGroupsTP(); i++) {
				allPreferencesData.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.CMTD;
			for (int i = 0; i < coursePref.getPrefNbGroupsCMTD(); i++) {
				allPreferencesData.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
			courseType = CourseType.CMTP;
			for (int i = 0; i < coursePref.getPrefNbGroupsCMTP(); i++) {
				allPreferencesData.add(CoursePrefElement.newInstance(courseType, coursePref));
			}
		}
	}

	public static Set<CoursePrefElement> getAllPreferences() {
		return allPreferencesData;
	}
	
	public static Set<CoursePrefElement> getChosenPreferences() {
		return chosenPreferencesData;
	}

	/**
	 * TODO: trouver le CoursePref qui correspond à texts, le retirer du Set dans
	 * lequel il est puis le mettre dans l'autre
	 * 
	 * @param texts               : first element is Teacher, second is course,
	 *                            third is groupType and fourth is choice
	 * @param toChosenPreferences true iff the element that has been clicked is on
	 *                            the Table named all preferences
	 */
	public static void updatePreferences(ArrayList<String> texts, boolean toChosenPreferences) {
		if (toChosenPreferences) {
			for (CoursePrefElement coursePrefElement : allPreferencesData) {
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
				
				if (teacherName.equals(texts.get(0)) && courseName.equals(texts.get(1)) && groupType.equals(texts.get(2)) && choice.equals(texts.get(3))) {
					allPreferencesData.remove(coursePrefElement);
					chosenPreferencesData.add(coursePrefElement);
					break;
				}
			}
		} else {
			for (CoursePrefElement coursePrefElement : chosenPreferencesData) {
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
				if (teacherName.equals(texts.get(0)) && courseName.equals(texts.get(1)) && groupType.equals(texts.get(2)) && choice.equals(texts.get(3))) {
					chosenPreferencesData.remove(coursePrefElement);
					allPreferencesData.add(coursePrefElement);
					break;
				}
			}
		}
	}
}
