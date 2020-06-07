package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	public static void initData() {
		allPreferences = new HashSet<>();
		chosenPreferences = new HashSet<>();
	}
	
	/**
	 * Populates all preferences data from a CalcData instance
	 * @param calcData
	 */
	public static void setData(CalcData calcData) {
		checkNotNull(calcData);
		
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
	 * @param stringTableItem the strings shown from the table item. Its size is 4 : first element is teacher name, second is course name, third is group type and fourth is teacher choice
	 * @param source the set where we want to find the CoursePrefElement object corresponding to stringTableItem
	 * @param target the set where we want to add the CoursePrefElement object corresponding to stringTableItem
	 */
	private static void updateSet(List<String> stringTableItem, Set<CoursePrefElement> source, Set<CoursePrefElement> target) {
		checkNotNull(stringTableItem);
		checkArgument(stringTableItem.size() == 4);
		checkNotNull(source);
		checkNotNull(target);
		
		for (CoursePrefElement coursePrefElement : source) {
			
			List<String> stringCoursePrefElement = coursePrefElement.getDataForTableItem();

			if (stringCoursePrefElement.get(0).equals(stringTableItem.get(0)) && stringCoursePrefElement.get(1).equals(stringTableItem.get(1))
					&& stringCoursePrefElement.get(2).equals(stringTableItem.get(2)) && stringCoursePrefElement.get(3).equals(stringTableItem.get(3))) {
				source.remove(coursePrefElement);
				target.add(coursePrefElement);
				break;
			}
		}
	}

	/**
	 * This method is called from Controller class when a table item has been clicked.
	 * Updates the data from Model thanks to the table item data.
	 * @param texts               the strings shown from the table item. texts'
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
