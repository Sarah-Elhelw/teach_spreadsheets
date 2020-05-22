package io.github.oliviercailloux.teach_spreadsheets.base;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

public class AgregatedData {
	private Set<CalcData> calcDatas;
	/**
	 * List is preferable over Set because some courses have the same name and we
	 * want to have exactly the same courses in each CalcData.
	 */
	private List<Course> courses;
	private static int reading;

	private AgregatedData() {
		calcDatas = new LinkedHashSet<>();
		courses = new ArrayList<>();
		reading = 0;
	}

	public static AgregatedData newInstance() {
		return new AgregatedData();
	}

	public Set<CalcData> getCalcDatas() {
		return calcDatas;
	}

	public List<Course> getCourses() {
		return courses;
	}

	/**
	 * This method adds a {@link CalcData} to the set of calc datas of the object.
	 * 
	 * @param calcData - the CalcData to be added
	 * 
	 * @throws NullPointerException     if the parameter is null
	 * @throws IllegalArgumentException if the CalcData we want to add does not
	 *                                  contain the expected courses or contains the
	 *                                  preferences of a Teacher that were already
	 *                                  added.
	 */
	public void addCalcData(CalcData calcData) {
		checkNotNull(calcData, "The CalcData must not be null");

		List<Course> calcDataCourses = new ArrayList<>();
		for (CoursePref coursePref : calcData.getCoursePrefs()) {
			calcDataCourses.add(coursePref.getCourse());
		}

		reading++;

		if (reading == 1) {
			courses = calcDataCourses;
		} else {
			checkArgument(courses.equals(calcDataCourses), "There must be the same courses in the calc datas.");
		}

		for (CalcData elt : calcDatas) {
			checkArgument(!elt.getTeacher().equals(calcData.getTeacher()),
					"You cannot add twice all the preferences of a teacher.");
		}
	}
}