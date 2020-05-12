package io.github.oliviercailloux.teach_spreadsheets.write;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class GlobalAssignmentTests {

	@Test

	/**
	 * This Test verifies if the summarized document is correctly completed
	 * 
	 * @throws Throwable if it is a problem while testing if the document is
	 *                   correctly completed
	 */

	void testCreateGlobalAssignment() throws Throwable {

		Course.Builder courseBuilder1 = Course.Builder.newInstance();
		Course.Builder courseBuilder2 = Course.Builder.newInstance();
		courseBuilder1.setName("testcourse1");
		courseBuilder1.setStudyYear("2016/2017");
		courseBuilder1.setSemester(1);
		courseBuilder1.setCountGroupsCM(3);
		courseBuilder1.setCountGroupsTD(4);
		courseBuilder1.setnbMinutesCM(60);
		courseBuilder1.setnbMinutesTD(60);

		courseBuilder2.setName("testcourse2");
		courseBuilder2.setStudyYear("2016/2017");
		courseBuilder2.setSemester(1);
		courseBuilder2.setCountGroupsTP(3);
		courseBuilder2.setCountGroupsTD(4);
		courseBuilder2.setnbMinutesTP(60);
		courseBuilder2.setnbMinutesTD(60);

		Course course1 = courseBuilder1.build();
		Course course2 = courseBuilder2.build();

		Teacher.Builder teacherBuilder1 = Teacher.Builder.newInstance();
		Teacher.Builder teacherBuilder2 = Teacher.Builder.newInstance();
		teacherBuilder1.setFirstName("teacher1FirstName");
		teacherBuilder1.setLastName("teacher1LastName");
		teacherBuilder2.setFirstName("teacher2FirstName");
		teacherBuilder2.setLastName("teacher2LastName");
		Teacher teacher1 = teacherBuilder1.build();
		Teacher teacher2 = teacherBuilder2.build();

		CoursePref.Builder prefBuilder1 = CoursePref.Builder.newInstance(course1, teacher1);
		CoursePref.Builder prefBuilder2 = CoursePref.Builder.newInstance(course1, teacher2);
		CoursePref.Builder prefBuilder3 = CoursePref.Builder.newInstance(course2, teacher1);

		prefBuilder1.setPrefCM(Preference.A);
		prefBuilder1.setPrefTD(Preference.B);

		prefBuilder2.setPrefCM(Preference.C);
		prefBuilder2.setPrefTD(Preference.A);

		prefBuilder3.setPrefTP(Preference.B);
		prefBuilder3.setPrefTD(Preference.C);

		CoursePref pref1 = prefBuilder1.build();
		CoursePref pref2 = prefBuilder2.build();
		CoursePref pref3 = prefBuilder3.build();

		TeacherAssignment teacherAssignment1 = TeacherAssignment.Builder.newInstance(teacher1).setCountGroupsTD(1)
				.build();
		CourseAssignment.Builder courseAssignmentBuilder1 = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder1.addTeacherAssignment(teacherAssignment1);

		CourseAssignment courseAssignment1 = courseAssignmentBuilder1.build();

		TeacherAssignment teacherAssignment2 = TeacherAssignment.Builder.newInstance(teacher1).setCountGroupsTD(1)
				.build();
		CourseAssignment.Builder courseAssignmentBuilder2 = CourseAssignment.Builder.newInstance(course2);
		courseAssignmentBuilder2.addTeacherAssignment(teacherAssignment2);

		CourseAssignment courseAssignment2 = courseAssignmentBuilder2.build();

		ImmutableSet<Course> courses = ImmutableSet.of(course1, course2);
		ImmutableSet<CoursePref> prefs = ImmutableSet.of(pref1, pref2, pref3);
		ImmutableSet<CourseAssignment> allCoursesAssigned = ImmutableSet.of(courseAssignment1, courseAssignment2);

		try (SpreadsheetDocument document = GlobalAssignment.createGlobalAssignment(courses, prefs,
				allCoursesAssigned)) {
			Table table = document.getTableByName("Summary");

			assertEquals("testcourse1", table.getCellByPosition("C4").getDisplayText());
			assertEquals("teacher1FirstName", table.getCellByPosition("F6").getDisplayText());
			assertEquals("A", table.getCellByPosition("H6").getDisplayText());
			assertEquals("testcourse2", table.getCellByPosition("C11").getDisplayText());
			assertEquals("teacher1FirstName", table.getCellByPosition("F13").getDisplayText());
			assertEquals("C", table.getCellByPosition("H13").getDisplayText());
			assertEquals("teacher1FirstName", table.getCellByPosition("I9").getDisplayText());

		}

	}
}
