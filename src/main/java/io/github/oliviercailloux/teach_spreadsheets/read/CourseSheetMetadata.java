package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * This class represents the metadata in a {@link CourseSheet} (current year,
 * number of student, ...)
 *
 */
public class CourseSheetMetadata {

	private int yearBegin = 2017;
	private String yearOfStud = "";
	private String completeYearOfStudyName = "";
	private int studentNumber = 0;
	private int firstSemesterNumber = 0;

	public int getYearBegin() {
		return yearBegin;
	}

	public void setYearBegin(int yearBegin) {
		Preconditions.checkArgument(yearBegin >= 0, "The year is incorrect");
		this.yearBegin = yearBegin;
	}

	public String getYearOfStud() {
		return yearOfStud;
	}

	public void setYearOfStud(String yearOfStud) {
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	public String getCompleteYearOfStudyName() {
		return completeYearOfStudyName;
	}

	public void setCompleteYearOfStudyName(String completeYearOfStudyName) {
		this.completeYearOfStudyName = Objects.requireNonNull(completeYearOfStudyName);
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		Preconditions.checkArgument(studentNumber >= 0, "The number of student can't be negative");
		this.studentNumber = studentNumber;
	}

	public int getFirstSemesterNumber() {
		return firstSemesterNumber;
	}

	public void setFirstSemesterNumber(int firstSemesterNumber) {
		Preconditions.checkArgument(firstSemesterNumber >= 0, "The number of the first semester is incorrect");

		this.firstSemesterNumber = firstSemesterNumber;
	}

	@Override
	public String toString() {
		return "CourseSheetMetadata [yearBegin=" + yearBegin + ", yearOfStud=" + yearOfStud
				+ ", completeYearOfStudyName=" + completeYearOfStudyName + ", studentNumber=" + studentNumber
				+ ", firstSemesterNumber=" + firstSemesterNumber + "]";
	}

}
