package io.github.oliviercailloux.teach_spreadsheets.write;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;

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
	void testCreateAssignmentPerTeacher() throws Exception {
		Teacher teacher1 = Teacher.Builder.newInstance().setFirstName("teacher1FirstName")
				.setLastName("teacher1LastName").setAddress("Place de Maréchal de Lattre de Tassigny, 75016 PARIS")
				.setDauphineEmail("teacher1@dauphine.fr").setPersonalEmail("teacher1@gmail.com")
				.setMobilePhone("06 12 34 56 78").setStatus("MCF").build();

		Course course1 = Course.Builder.newInstance().setName("testcourse1").setStudyYear(2016).setStudyLevel("DE1")
				.setSemester(1).setCountGroupsCM(3).setCountGroupsTD(4).setNbMinutesCM(60).setNbMinutesTD(60).build();

		TeacherAssignment teacherAssignment = TeacherAssignment.Builder.newInstance(course1, teacher1)
				.setCountGroupsTD(1).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder.addTeacherAssignment(teacherAssignment);

		CourseAssignment courseAssignment = courseAssignmentBuilder.build();

		ImmutableSet<CourseAssignment> allCoursesAssigned = ImmutableSet.of(courseAssignment);

		URL resourceUrl = AssignmentPerTeacher.class.getResource("AssignmentPerTeacher.ods");
		try (InputStream stream = resourceUrl.openStream();
				SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream);
				SpreadsheetDocument documentCreated = AssignmentPerTeacher.createAssignmentPerTeacher(teacher1,
						allCoursesAssigned)) {

			Table tableCreated = documentCreated.getTableByName("Summary");
			Table table = document.getTableByName("Summary");

			assertEquals(table.getCellByPosition("A4").getDisplayText(),
					tableCreated.getCellByPosition("A4").getDisplayText());

			assertEquals(table.getCellByPosition("C4").getDisplayText(),
					tableCreated.getCellByPosition("C4").getDisplayText());

			assertEquals(table.getCellByPosition("B6").getDisplayText(),
					tableCreated.getCellByPosition("B6").getDisplayText());

			assertEquals(table.getCellByPosition("C8").getDisplayText(),
					tableCreated.getCellByPosition("C8").getDisplayText());

			assertEquals(table.getCellByPosition("C10").getDisplayText(),
					tableCreated.getCellByPosition("C10").getDisplayText());

			assertEquals(table.getCellByPosition("C13").getDisplayText(),
					tableCreated.getCellByPosition("C13").getDisplayText());

			assertEquals(table.getCellByPosition("B17").getDisplayText(),
					tableCreated.getCellByPosition("B17").getDisplayText());

			assertEquals(table.getCellByPosition("C17").getDisplayText(),
					tableCreated.getCellByPosition("C17").getDisplayText());

			assertEquals(table.getCellByPosition("D17").getDisplayText(),
					tableCreated.getCellByPosition("D17").getDisplayText());

			assertEquals(table.getCellByPosition("E21").getDisplayText(),
					tableCreated.getCellByPosition("E21").getDisplayText());

		}
	}
}
