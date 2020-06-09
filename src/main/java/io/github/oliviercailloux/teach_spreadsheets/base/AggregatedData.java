package io.github.oliviercailloux.teach_spreadsheets.base;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Immutable. This class aggregates the CalcDatas built from the reading of
 * several input vows files.
 *
 */
public class AggregatedData {
	private ImmutableSet<CalcData> calcDatas;
	private ImmutableSet<Course> courses;

	private AggregatedData(Set<CalcData> calcDatas, Set<Course> courses) {
		this.calcDatas = ImmutableSet.copyOf(calcDatas);
		this.courses = ImmutableSet.copyOf(courses);
	}

	public static class Builder {

		/**
		 * Set of CalcDatas : it is used to build (by adding) calcDatas attribute.
		 */
		private Set<CalcData> tempCalcDatas;
		private Set<Course> tempCourses;
		private int reading;

		private Builder() {
			tempCalcDatas = new LinkedHashSet<>();
			tempCourses = new LinkedHashSet<>();
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public AggregatedData build() {
			checkState(tempCalcDatas.size() >= 1, "An AggregatedData must contain at least one CalcData.");
			return new AggregatedData(tempCalcDatas, tempCourses);
		}

		/**
		 * This method adds a CalcData to the set of CalcDatas of the object.
		 * 
		 * @param calcData - the CalcData to be added
		 * 
		 * @throws IllegalArgumentException if the CalcData we want to add does not
		 *                                  contain the expected courses or contains the
		 *                                  preferences of a Teacher that was already
		 *                                  added.
		 */
		public void addCalcData(CalcData calcData) {
			checkNotNull(calcData, "The CalcData must not be null");

			Set<Course> calcDataCourses = new LinkedHashSet<>();
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
						"You cannot add twice all the preferences the teacher : " + elt.getTeacher().getFirstName()
								+ elt.getTeacher().getLastName());
			}

			tempCalcDatas.add(calcData);
		}
	}

	public ImmutableSet<CalcData> getCalcDatas() {
		return calcDatas;
	}

	public ImmutableSet<Course> getCourses() {
		return courses;
	}

	/**
	 * This methods gets all the preferences of a given Teacher.
	 * 
	 * @param teacher - the Teacher whose preferences we want to get.
	 * 
	 * @return all the preferences of the given Teacher
	 * 
	 * @throws IllegalArgumentException if there is no preference referenced for the
	 *                                  given teacher.
	 */
	public ImmutableSet<CoursePref> getTeacherPrefs(Teacher teacher) {
		checkNotNull(teacher, "The teacher must not be null.");
		for (CalcData calcData : calcDatas) {
			if (teacher.equals(calcData.getTeacher())) {
				return calcData.getCoursePrefs();
			}
		}
		throw new IllegalArgumentException("There is no preference referenced for this teacher.");
	}

	/**
	 * This method gets all the preferences expressed for a given Course.
	 * 
	 * @param course - the course whose preferences for we want to get.
	 * 
	 * @return all the preferences expressed for the given Course.
	 * 
	 */
	public ImmutableSet<CoursePref> getCoursePrefs(Course course) {
		checkNotNull(course, "The course must not be null.");
		Set<CoursePref> coursePrefs = new LinkedHashSet<>();
		for (CalcData calcData : calcDatas) {
			coursePrefs.add(calcData.getCoursePref(course));
		}
		return ImmutableSet.copyOf(coursePrefs);
	}

}