package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Immutable Class used to store a course information. Uses Builder pattern
 * implementation.
 * 
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class Course {
	private static final String EXCEPTION_STRING = "String must not be null.";
	private static final String EXCEPTION_INT = "int must not be positive.";

	private String name;
	private String studyYear;
	private int semester;

	private int countGroupsTD;
	private int countGroupsTP;
	private int countGroupsCMTD;
	private int countGroupsCMTP;
	private int countGroupsCM;

	private int nbMinutesTD;
	private int nbMinutesTP;
	private int nbMinutesCMTD;
	private int nbMinutesCMTP;
	private int nbMinutesCM;

	private Course() {
		name = "";
		studyYear = "";
		semester = 0;

		// by default, semester, nbMinutes and countGroups variables are initialized at
		// zero
	}

	public String getName() {
		return name;
	}

	public String getStudyYear() {
		return studyYear;
	}

	public int getSemester() {
		return semester;
	}

	public int getCountGroupsTD() {
		return countGroupsTD;
	}

	public int getCountGroupsTP() {
		return countGroupsTP;
	}

	public int getCountGroupsCMTD() {
		return countGroupsCMTD;
	}

	public int getCountGroupsCMTP() {
		return countGroupsCMTP;
	}

	public int getCountGroupsCM() {
		return countGroupsCM;
	}

	public int getNbMinutesTD() {
		return nbMinutesTD;
	}

	public int getNbMinutesTP() {
		return nbMinutesTP;
	}

	public int getNbMinutesCMTD() {
		return nbMinutesCMTD;
	}

	public int getNbMinutesCMTP() {
		return nbMinutesCMTP;
	}

	public int getNbMinutesCM() {
		return nbMinutesCM;
	}

	public static class Builder {
		private Course courseToBuild;

		public static Builder newInstance() {
			return new Builder();
		}

		private Builder() {
			courseToBuild = new Course();
		}

		public Course build() {
			checkNotNull(courseToBuild.name);
			checkNotNull(courseToBuild.studyYear);
			checkState(courseToBuild.semester == 1 || courseToBuild.semester == 2);
			checkState(courseToBuild.countGroupsCM + courseToBuild.countGroupsCMTD + courseToBuild.countGroupsCMTP
					+ courseToBuild.countGroupsTD + courseToBuild.countGroupsTP > 0);
			checkState(courseToBuild.nbMinutesCM + courseToBuild.nbMinutesCMTD + courseToBuild.nbMinutesCMTP
					+ courseToBuild.nbMinutesTD + courseToBuild.nbMinutesTP > 0);
			checkState(!courseToBuild.name.isEmpty() && !courseToBuild.studyYear.isEmpty());

			Course courseBuilt = courseToBuild;
			courseToBuild = new Course();
			return courseBuilt;
		}

		public Builder setName(String name) {
			checkNotNull(name, EXCEPTION_STRING);
			this.courseToBuild.name = name;
			return this;
		}

		public Builder setStudyYear(String studyYear) {
			checkNotNull(studyYear, EXCEPTION_STRING);
			this.courseToBuild.studyYear = studyYear;
			return this;
		}

		public Builder setSemester(int semester) {
			checkArgument(semester == 2 || semester == 1, "int must be 1 or 2.");
			this.courseToBuild.semester = semester;
			return this;
		}

		public Builder setCountGroupsTD(int countGroupsTD) {
			checkArgument(countGroupsTD >= 0, EXCEPTION_INT);
			this.courseToBuild.countGroupsTD = countGroupsTD;
			return this;
		}

		public Builder setCountGroupsTP(int countGroupsTP) {
			checkArgument(countGroupsTP >= 0, EXCEPTION_INT);
			this.courseToBuild.countGroupsTP = countGroupsTP;
			return this;
		}

		public Builder setCountGroupsCMTD(int countGroupsCMTD) {
			checkArgument(countGroupsCMTD >= 0, EXCEPTION_INT);
			this.courseToBuild.countGroupsCMTD = countGroupsCMTD;
			return this;
		}

		public Builder setCountGroupsCMTP(int countGroupsCMTP) {
			checkArgument(countGroupsCMTP >= 0, EXCEPTION_INT);
			this.courseToBuild.countGroupsCMTP = countGroupsCMTP;
			return this;
		}

		public Builder setCountGroupsCM(int countGroupsCM) {
			checkArgument(countGroupsCM >= 0, EXCEPTION_INT);
			this.courseToBuild.countGroupsCM = countGroupsCM;
			return this;
		}

		public Builder setnbMinutesTD(int nbMinutesTD) {
			checkArgument(nbMinutesTD >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesTD = nbMinutesTD;
			return this;
		}

		public Builder setnbMinutesTP(int nbMinutesTP) {
			checkArgument(nbMinutesTP >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesTP = nbMinutesTP;
			return this;
		}

		public Builder setnbMinutesCMTD(int nbMinutesCMTD) {
			checkArgument(nbMinutesCMTD >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesCMTD = nbMinutesCMTD;
			return this;
		}

		public Builder setnbMinutesCMTP(int nbMinutesCMTP) {
			checkArgument(nbMinutesCMTP >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesCMTP = nbMinutesCMTP;
			return this;
		}

		public Builder setnbMinutesCM(int nbMinutesCM) {
			checkArgument(nbMinutesCM >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesCM = nbMinutesCM;
			return this;
		}
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name)

				.add("countGroupsTD", countGroupsTD).add("countGroupsCMTD", countGroupsCMTD)
				.add("countGroupsTP", countGroupsTP).add("countGroupsCMTP", countGroupsCMTP)
				.add("countGroupsCM", countGroupsCM)

				.add("nbMinutesTD", nbMinutesTD).add("nbMinutesCMTD", nbMinutesCMTD).add("nbMinutesTP", nbMinutesTP)
				.add("nbMinutesCMTP", nbMinutesCMTP).add("nbMinutesCM", nbMinutesCM)

				.add("studyYear", studyYear).add("semester", semester)

				.toString();
	}
}
