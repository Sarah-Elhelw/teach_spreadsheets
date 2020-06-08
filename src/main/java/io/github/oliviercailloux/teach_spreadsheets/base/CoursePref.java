package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * ImmutableSet. Class used to store a teacher's preference for one course. Uses
 * Builder pattern implementation.
 * 
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class CoursePref {
	private static final String EXCEPTION = "A preferred number of groups needs to be positive.";
	private static final String EXCEPTION_PREFERENCE = "You can't have a preference for a type of course that won't have sessions.";

	private Course course;
	private Teacher teacher;

	/**
	 * Degree of preference of teacher for course, represented by an enum type
	 */
	private Preference prefCM;
	private Preference prefTD;
	private Preference prefCMTD;
	private Preference prefTP;
	private Preference prefCMTP;

	/**
	 * Number of groups that the teacher wants to have
	 */
	private int prefNbGroupsCM;
	private int prefNbGroupsTD;
	private int prefNbGroupsCMTD;
	private int prefNbGroupsTP;
	private int prefNbGroupsCMTP;

	/**
	 * Given a CM, TD, CMTD, TP or CMTP :
	 * 
	 * @param nbGroups   the number of groups to assign to this type of course
	 * @param nbMinutes  the number of minutes for the sessions of this type of
	 *                   course
	 * @param preference the preference of the teacher for this type of course
	 * @return false iff there is 0 group or 0 minutes for this type of course, but
	 *         the teacher has a preference for it
	 */
	private static boolean isPreferenceCoherent(int nbGroups, int nbMinutes, Preference preference) {
		return !((nbGroups == 0 || nbMinutes == 0) && preference != Preference.UNSPECIFIED);
	}

	/**
	 * @return true iff the values for the preferences are valid according to the
	 *         course
	 */
	private void checkCoherence() {
		checkNotNull(course);
		if (!(isPreferenceCoherent(course.getCountGroupsCM(), course.getNbMinutesCM(), getPrefCM())
				&& isPreferenceCoherent(course.getCountGroupsCMTD(), course.getNbMinutesCMTD(), getPrefCMTD())
				&& isPreferenceCoherent(course.getCountGroupsCMTP(), course.getNbMinutesCMTP(), getPrefCMTP())
				&& isPreferenceCoherent(course.getCountGroupsTD(), course.getNbMinutesTD(), getPrefTD())
				&& isPreferenceCoherent(course.getCountGroupsTP(), course.getNbMinutesTP(), getPrefTP())))
			throw new IllegalArgumentException(EXCEPTION_PREFERENCE);
	}

	private CoursePref() {
		prefCM = Preference.UNSPECIFIED;
		prefTD = Preference.UNSPECIFIED;
		prefCMTD = Preference.UNSPECIFIED;
		prefTP = Preference.UNSPECIFIED;
		prefCMTP = Preference.UNSPECIFIED;
	}

	public Course getCourse() {
		return course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Preference getPrefCM() {
		return prefCM;
	}

	public Preference getPrefTD() {
		return prefTD;
	}

	public Preference getPrefCMTD() {
		return prefCMTD;
	}

	public Preference getPrefTP() {
		return prefTP;
	}

	public Preference getPrefCMTP() {
		return prefCMTP;
	}

	public int getPrefNbGroupsCM() {
		return prefNbGroupsCM;
	}

	public int getPrefNbGroupsTD() {
		return prefNbGroupsTD;
	}

	public int getPrefNbGroupsCMTD() {
		return prefNbGroupsCMTD;
	}

	public int getPrefNbGroupsTP() {
		return prefNbGroupsTP;
	}

	public int getPrefNbGroupsCMTP() {
		return prefNbGroupsCMTP;
	}

	public static class Builder {
		private CoursePref coursePrefToBuild;

		public static Builder newInstance(Course course, Teacher teacher) {
			Builder builder = new Builder();
			builder.setCourse(course);
			builder.setTeacher(teacher);
			return builder;
		}

		private Builder() {
			coursePrefToBuild = new CoursePref();
		}

		public CoursePref build() {
			coursePrefToBuild.checkCoherence();
			CoursePref coursePrefBuilt = coursePrefToBuild;
			coursePrefToBuild = new CoursePref();
			return coursePrefBuilt;
		}

		private Builder setCourse(Course course) {
			checkNotNull(course, "Course needs to be instanciated.");
			this.coursePrefToBuild.course = course;
			return this;
		}

		private Builder setTeacher(Teacher teacher) {
			checkNotNull(teacher, "Teacher needs to be instanciated.");
			this.coursePrefToBuild.teacher = teacher;
			return this;
		}

		public Builder setPrefCM(Preference prefCM) {
			checkNotNull(prefCM);
			this.coursePrefToBuild.prefCM = prefCM;
			return this;
		}

		public Builder setPrefTD(Preference prefTD) {
			checkNotNull(prefTD);
			this.coursePrefToBuild.prefTD = prefTD;
			return this;
		}

		public Builder setPrefCMTD(Preference prefCMTD) {
			checkNotNull(prefCMTD);
			this.coursePrefToBuild.prefCMTD = prefCMTD;
			return this;
		}

		public Builder setPrefTP(Preference prefTP) {
			checkNotNull(prefTP);
			this.coursePrefToBuild.prefTP = prefTP;
			return this;
		}

		public Builder setPrefCMTP(Preference prefCMTP) {
			checkNotNull(prefCMTP);
			this.coursePrefToBuild.prefCMTP = prefCMTP;
			return this;
		}

		public Builder setPrefNbGroupsCM(int prefNbGroupsCM) {
			checkArgument(prefNbGroupsCM >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsCM = prefNbGroupsCM;
			return this;
		}

		public Builder setPrefNbGroupsTD(int prefNbGroupsTD) {
			checkArgument(prefNbGroupsTD >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsTD = prefNbGroupsTD;
			return this;
		}

		public Builder setPrefNbGroupsCMTD(int prefNbGroupsCMTD) {
			checkArgument(prefNbGroupsCMTD >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsCMTD = prefNbGroupsCMTD;
			return this;
		}

		public Builder setPrefNbGroupsTP(int prefNbGroupsTP) {
			checkArgument(prefNbGroupsTP >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsTP = prefNbGroupsTP;
			return this;
		}

		public Builder setPrefNbGroupsCMTP(int prefNbGroupsCMTP) {
			checkArgument(prefNbGroupsCMTP >= 0, EXCEPTION);
			this.coursePrefToBuild.prefNbGroupsCMTP = prefNbGroupsCMTP;
			return this;
		}
	}
	
	/**
	 * We consider that two coursePrefs are equal if all their attributes are equal.
	 * 
	 * 
	 * @return true if the object in parameter is equal to the coursePref and false
	 *         if it is not equal
	 * 
	 */
	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof CoursePref)) {
			return false;
		}
		if (this == o2) {
			return true;
		}
		CoursePref cp2 = (CoursePref) o2;

		return course.equals(cp2.course) && teacher.equals(cp2.teacher) && prefCM.equals(cp2.prefCM)
				&& prefTD.equals(cp2.prefTD) && prefCMTD.equals(cp2.prefCMTD) && prefTP.equals(cp2.prefTP)
				&& prefCMTP.equals(cp2.prefCMTP) && prefNbGroupsCM == cp2.prefNbGroupsCM
				&& prefNbGroupsTD == cp2.prefNbGroupsTD && prefNbGroupsCMTD == cp2.prefNbGroupsCMTD
				&& prefNbGroupsTP == cp2.prefNbGroupsTP && prefNbGroupsCMTP == cp2.prefNbGroupsCMTP;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, teacher, prefCM, prefTD, prefCMTD, prefTP, prefCMTP, prefNbGroupsCM, prefNbGroupsTD,
				prefNbGroupsCMTD, prefNbGroupsTP, prefNbGroupsCMTP);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("prefCM", prefCM).add("prefTD", prefTD).add("prefCMTD", prefCMTD)
				.add("prefTP", prefTP).add("prefCMTP", prefCMTP)

				.add("prefNbGroupsCM", prefNbGroupsCM).add("prefNbGroupsTD", prefNbGroupsTD)
				.add("prefNbGroupsCMTD", prefNbGroupsCMTD).add("prefNbGroupsTP", prefNbGroupsTP)
				.add("prefNbGroupsCMTP", prefNbGroupsCMTP)

				.add("Course", course.toString()).add("Teacher", teacher.toString())

				.toString();
	}
}
