package io.github.oliviercailloux.teach_spreadsheets.write;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class AssignmentPerTeacherTests {

	@Test

	/**
	 * This Test verifies if the summarized document is correctly completed
	 * 
	 * @throws Throwable if it is a problem while testing if the document is
	 *                   correctly completed
	 */

	void testCreateAssignmentPerTeacher() throws Throwable {
		Teacher.Builder teacherBuilder1 = Teacher.Builder.newInstance();
		teacherBuilder1.setFirstName("teacher1FirstName");
		teacherBuilder1.setLastName("teacher1LastName");
		teacherBuilder1.setAddress("Place de Maréchal de Lattre de Tassigny, 75016 PARIS");
		teacherBuilder1.setDauphineEmail("teacher1@dauphine.fr");
		teacherBuilder1.setPersonalEmail("teacher1@gmail.com");
		teacherBuilder1.setMobilePhone("06 12 34 56 78");
		teacherBuilder1.setStatus("MCF");

		Teacher teacher1 = teacherBuilder1.build();

		Course.Builder courseBuilder1 = Course.Builder.newInstance();

		courseBuilder1.setName("testcourse1");
		courseBuilder1.setStudyYear("2016/2017");
		courseBuilder1.setSemester(1);
		courseBuilder1.setCountGroupsCM(3);
		courseBuilder1.setCountGroupsTD(4);
		courseBuilder1.setnbMinutesCM(60);
		courseBuilder1.setnbMinutesTD(60);

		Course course1 = courseBuilder1.build();

		TeacherAssignment teacherAssignment = TeacherAssignment.Builder.newInstance(teacher1).setCountGroupsTD(1)
				.build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder.addTeacherAssignment(teacherAssignment);

		CourseAssignment courseAssignment = courseAssignmentBuilder.build();

		ImmutableSet<CourseAssignment> allCoursesAssigned = ImmutableSet.of(courseAssignment);

		try (SpreadsheetDocument document = AssignmentPerTeacher.createAssignmentPerTeacher(teacher1,
				allCoursesAssigned)) {

			Table table = document.getTableByName("Summary");

			assertEquals("teacher1FirstName", table.getCellByPosition("A4").getDisplayText());
			assertEquals("teacher1@dauphine.fr", table.getCellByPosition("C10").getDisplayText());
			assertEquals("testcourse1", table.getCellByPosition("C17").getDisplayText());

		}
	}
}
