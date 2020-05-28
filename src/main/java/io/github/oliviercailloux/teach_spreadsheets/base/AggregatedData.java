package io.github.oliviercailloux.teach_spreadsheets.base;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable. This class aggregates the calc datas built from the reading of
 * several input vows files.
 *
 */
public class AggregatedData {
	private ImmutableSet<CalcData> calcDatas;
	/**
	 * List is preferable over Set because some courses have the same name and we
	 * want to have exactly the same courses in each CalcData.
	 */
	private ImmutableList<Course> courses;

	private AggregatedData(Set<CalcData> calcDatas, List<Course> courses) {
		this.calcDatas = ImmutableSet.copyOf(calcDatas);
		this.courses = ImmutableList.copyOf(courses);
	}

	public static class Builder {

		/**
		 * Set of {@link TeacherAssignment} : it is used to build (by adding) calcDatas.
		 */
		private Set<CalcData> tempCalcDatas;
		private List<Course> tempCourses;
		private int reading;

		private Builder() {
			tempCalcDatas = new LinkedHashSet<>();
			tempCourses = new ArrayList<>();
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public AggregatedData build() {
			return new AggregatedData(tempCalcDatas, tempCourses);
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
		 *                                  added. Beware : the courses in the calcDatas
		 *                                  must be in the same order.
		 */
		public void addCalcData(CalcData calcData) {
			checkNotNull(calcData, "The CalcData must not be null");

			List<Course> calcDataCourses = new ArrayList<>();
			for (CoursePref coursePref : calcData.getCoursePrefs()) {
				calcDataCourses.add(coursePref.getCourse());
			}

			reading++;

			if (reading == 1) {
				tempCourses = calcDataCourses;
			} else {
				checkArgument(tempCourses.equals(calcDataCourses), "There must be the same courses in the calc datas.");
			}

			for (CalcData elt : tempCalcDatas) {
				checkArgument(!elt.getTeacher().equals(calcData.getTeacher()),
						"You cannot add twice all the preferences of a teacher.");
			}

			tempCalcDatas.add(calcData);
		}
	}

	public Set<CalcData> getCalcDatas() {
		return calcDatas;
	}

	public List<Course> getCourses() {
		return courses;
	}

}