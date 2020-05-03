package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TestAssignmentPerTeacher {

	@Test

	/**
	 * This Test verifies if the summarized document is correctly completed
	 * 
	 * @throws Throwable if it is a problem while testing if the document is
	 *                   correctly completed
	 */

	void testAssignmentPerTeacher() throws Throwable {
		Teacher.Builder teacherBuilder1 = Teacher.Builder.newInstance();
		Teacher.Builder teacherBuilder2 = Teacher.Builder.newInstance();
		teacherBuilder1.setFirstName("teacher1FirstName");
		teacherBuilder1.setLastName("teacher1LastName");
		teacherBuilder2.setFirstName("teacher2FirstName");
		teacherBuilder2.setLastName("teacher2LastName");
		Teacher teacher1 = teacherBuilder1.build();
		Teacher teacher2 = teacherBuilder2.build();

		AssignmentPerTeacher.createAssignmentPerTeacher(teacher1);
	}
}
