package io.github.oliviercailloux.teach_spreadsheets.write;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class OdsSummarizerTests {

	@Test
	void testCreateSummary() throws Exception {

		Course course1 = Course.Builder.newInstance().setName("testcourse1").setStudyYear(2016).setStudyLevel("DE1")
				.setSemester(1).setCountGroupsCM(3).setCountGroupsTD(4).setNbMinutesCM(60).setNbMinutesTD(60).build();
		Course course2 = Course.Builder.newInstance().setName("testcourse2").setStudyYear(2016).setStudyLevel("DE1")
				.setSemester(1).setCountGroupsTP(3).setCountGroupsTD(4).setNbMinutesTP(60).setNbMinutesTD(60).build();

		Teacher teacher1 = Teacher.Builder.newInstance().setFirstName("teacher1FirstName")
				.setLastName("teacher1LastName").build();
		Teacher teacher2 = Teacher.Builder.newInstance().setFirstName("teacher2FirstName")
				.setLastName("teacher2LastName").build();

		CoursePref pref1 = CoursePref.Builder.newInstance(course1, teacher1).setPrefCM(Preference.A)
				.setPrefTD(Preference.B).build();
		CoursePref pref2 = CoursePref.Builder.newInstance(course1, teacher2).setPrefCM(Preference.C)
				.setPrefTD(Preference.A).build();
		CoursePref pref3 = CoursePref.Builder.newInstance(course2, teacher1).setPrefTP(Preference.B)
				.setPrefTD(Preference.C).build();

		TeacherAssignment teacherAssignment1 = TeacherAssignment.Builder.newInstance(course1, teacher1)
				.setCountGroupsTD(1).build();
		CourseAssignment.Builder courseAssignmentBuilder1 = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder1.addTeacherAssignment(teacherAssignment1);

		CourseAssignment courseAssignment1 = courseAssignmentBuilder1.build();

		TeacherAssignment teacherAssignment2 = TeacherAssignment.Builder.newInstance(course2, teacher1)
				.setCountGroupsTD(1).build();
		CourseAssignment.Builder courseAssignmentBuilder2 = CourseAssignment.Builder.newInstance(course2);
		courseAssignmentBuilder2.addTeacherAssignment(teacherAssignment2);

		CourseAssignment courseAssignment2 = courseAssignmentBuilder2.build();

		Set<Course> courses = new LinkedHashSet<>();
		courses.add(course1);
		courses.add(course2);
		Set<CoursePref> prefs = new LinkedHashSet<>();
		prefs.add(pref1);
		prefs.add(pref2);
		prefs.add(pref3);
		Set<CourseAssignment> allCoursesAssigned = new LinkedHashSet<>();
		allCoursesAssigned.add(courseAssignment1);
		allCoursesAssigned.add(courseAssignment2);

		OdsSummarizer ods = OdsSummarizer.newInstance(courses);
		ods.addPrefs(prefs);
		ods.setAllCoursesAssigned(allCoursesAssigned);

		URL resourceUrl = OdsSummarizer.class.getResource("OdsSummarizer.ods");
		try (InputStream stream = resourceUrl.openStream();
				SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream);
				SpreadsheetDocument documentCreated = ods.createSummary()) {

			Table tableCreated = documentCreated.getTableByName("Summary");
			Table table = document.getTableByName("Summary");

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 15; j++) {
					assertEquals(table.getCellByPosition(i, j).getDisplayText(),
							tableCreated.getCellByPosition(i, j).getDisplayText());
				}
			}

		}

	}
}
