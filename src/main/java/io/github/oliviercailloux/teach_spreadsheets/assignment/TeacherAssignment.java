package io.github.oliviercailloux.teach_spreadsheets.assignment;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * Immutable. This class provides, for a given course, a teacher that was
 * assigned and details the number of TD, TP, CMTD, CMTP and CM groups the
 * teacher will have to give lessons to.
 *
 */
public class TeacherAssignment {
	private final Teacher teacher;
	private int countGroupsTD;
	private int countGroupsTP;
	private int countGroupsCMTD;
	private int countGroupsCMTP;
	private int countGroupsCM;

	private TeacherAssignment(Teacher teacher) {
		this.teacher = Preconditions.checkNotNull(teacher, "The teacher assigned must not be null.");
		countGroupsTD = 0;
		countGroupsTP = 0;
		countGroupsCMTD = 0;
		countGroupsCMTP = 0;
		countGroupsCM = 0;
	}

	public static class Builder {
		private TeacherAssignment teacherAssignmentToBuild;

		private Builder(Teacher teacher) {
			teacherAssignmentToBuild = new TeacherAssignment(teacher);
		}

		public static Builder newInstance(Teacher teacher) {
			return new Builder(teacher);
		}

		/**
		 * Builds a TeacherAssignment.
		 * 
		 * @return teacherAssignmentToBuild - the TeacherAssignment that was built.
		 * 
		 */
		public TeacherAssignment build() {
			return teacherAssignmentToBuild;
		}

		/**
		 * Sets the value of the attribute countGroupsTD of teacherAssignmentToBuild.
		 * This value must be positive.
		 * 
		 * @param countGroupsTD - the integer used to set the value of the attribute
		 *                      countGroupsTD
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative
		 */
		public Builder setCountGroupsTD(int countGroupsTD) {
			Preconditions.checkArgument(countGroupsTD >= 0, "The number of TD groups must be positive.");
			this.teacherAssignmentToBuild.countGroupsTD = countGroupsTD;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsTP of teacherAssignmentToBuild.
		 * This value must be positive.
		 * 
		 * @param countGroupsTP - the integer used to set the value of the attribute
		 *                      countGroupsTP
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative
		 */
		public Builder setCountGroupsTP(int countGroupsTP) {
			Preconditions.checkArgument(countGroupsTP >= 0, "The number of TP groups must be positive.");
			this.teacherAssignmentToBuild.countGroupsTP = countGroupsTP;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsCMTD of teacherAssignmentToBuild.
		 * This value must be positive.
		 * 
		 * @param countGroupsCMTD - the integer used to set the value of the attribute
		 *                        countGroupsCMTD
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative
		 */
		public Builder setCountGroupsCMTD(int countGroupsCMTD) {
			Preconditions.checkArgument(countGroupsCMTD >= 0, "The number of CMTD groups must be positive.");
			this.teacherAssignmentToBuild.countGroupsCMTD = countGroupsCMTD;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsCMTP of teacherAssignmentToBuild.
		 * This value must be positive.
		 * 
		 * @param countGroupsCMTP - the integer used to set the value of the attribute
		 *                        countGroupsCMTP
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative
		 */
		public Builder setCountGroupsCMTP(int countGroupsCMTP) {
			Preconditions.checkArgument(countGroupsCMTP >= 0, "The number of CMTP groups must be positive.");
			this.teacherAssignmentToBuild.countGroupsCMTP = countGroupsCMTP;
			return this;
		}

		/**
		 * Sets the value of the attribute countGroupsCM of teacherAssignmentToBuild.
		 * This value must be positive.
		 * 
		 * @param countGroupsCM - the integer used to set the value of the attribute
		 *                      countGroupsCM
		 * 
		 * @return this - the object that called the method
		 * 
		 * @throws IllegalArgumentException if the parameter is negative
		 */
		public Builder setCountGroupsCM(int countGroupsCM) {
			Preconditions.checkArgument(countGroupsCM >= 0, "The number of CM groups must be positive.");
			this.teacherAssignmentToBuild.countGroupsCM = countGroupsCM;
			return this;
		}

	}

	public Teacher getTeacher() {
		return teacher;
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

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
	       .add("First name", teacher.getFirstName())
	       .add("Last name", teacher.getLastName())
	       .add("Number of TD groups", countGroupsTD)
	       .add("Number of TP groups", countGroupsTP)
	       .add("Number of CMTD groups", countGroupsCMTD)
	       .add("Number of CMTP groups", countGroupsCMTP)
	       .add("Number of CM groups", countGroupsCM)
	       .toString();
	}
}
