package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Immutable Class used to store a course information. Uses Builder pattern
 * implementation.
 * 
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class Course {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseAssignment.class);
	private static final String EXCEPTION_STRING = "String must not be null.";
	private static final String EXCEPTION_INT = "int must be positive.";

	private String name;
	/**
	 * This attribute corresponds to the academic level of the course. For example :
	 * DE1, DE2...
	 */
	private String studyLevel;
	/**
	 * This attribute corresponds to the beginning year of study of the course. For example,
	 * if the year is 2016/2017, studyYear = 2016.
	 */
	private int studyYear;
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
		studyLevel = "";
		semester = 0;

		// by default, semester, nbMinutes and countGroups variables are initialized at
		// zero
	}

	public String getName() {
		return name;
	}
	
	public String getStudyLevel() {
		return studyLevel;
	}

	public int getStudyYear() {
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
			checkNotNull(courseToBuild.name, "The course name cannot be null.");
			checkNotNull(courseToBuild.studyLevel, "The study level cannot be null.");
			checkArgument(courseToBuild.semester == 1 || courseToBuild.semester == 2, "The semester must be 1 or 2.");
			checkArgument(courseToBuild.countGroupsCM + courseToBuild.countGroupsCMTD + courseToBuild.countGroupsCMTP
					+ courseToBuild.countGroupsTD + courseToBuild.countGroupsTP > 0, "There must be at least one group for the course.");
			checkArgument(courseToBuild.nbMinutesCM + courseToBuild.nbMinutesCMTD + courseToBuild.nbMinutesCMTP
					+ courseToBuild.nbMinutesTD + courseToBuild.nbMinutesTP > 0, "There must be time to spend in teaching the course.");
			checkArgument(!courseToBuild.name.isEmpty(), "The course name must be specified.");
			checkArgument(!courseToBuild.studyLevel.isEmpty(), "The study level must be specified.");
			checkArgument(courseToBuild.studyYear != 0, "The study year must be specified.");
			Course courseBuilt = courseToBuild;
			courseToBuild = new Course();
			return courseBuilt;
		}

		public Builder setName(String name) {
			checkNotNull(name, EXCEPTION_STRING);
			this.courseToBuild.name = name;
			return this;
		}
		
		public Builder setStudyLevel(String studyLevel) {
			checkNotNull(studyLevel, EXCEPTION_STRING);
			this.courseToBuild.studyLevel = studyLevel;
			return this;
		}

		public Builder setStudyYear(int studyYear) {
			checkArgument(studyYear > 0, EXCEPTION_INT);
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

		public Builder setNbMinutesTD(int nbMinutesTD) {
			checkArgument(nbMinutesTD >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesTD = nbMinutesTD;
			return this;
		}

		public Builder setNbMinutesTP(int nbMinutesTP) {
			checkArgument(nbMinutesTP >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesTP = nbMinutesTP;
			return this;
		}

		public Builder setNbMinutesCMTD(int nbMinutesCMTD) {
			checkArgument(nbMinutesCMTD >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesCMTD = nbMinutesCMTD;
			return this;
		}

		public Builder setNbMinutesCMTP(int nbMinutesCMTP) {
			checkArgument(nbMinutesCMTP >= 0, EXCEPTION_INT);
			this.courseToBuild.nbMinutesCMTP = nbMinutesCMTP;
			return this;
		}

		public Builder setNbMinutesCM(int nbMinutesCM) {
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

				.add("studyLevel", studyLevel).add("studyYear", studyYear).add("semester", semester)

				.toString();
	}
	
	public static void main(String[] args) {

	Logger logger = Logger.getLogger("logger");

	logger.log(Level.INFO, "The classes Course, Teacher and CoursePref that are created by this process are also returned in order to be used for other purposes (like storing in JSON format the list of courses available in the input file).");
	}
}
