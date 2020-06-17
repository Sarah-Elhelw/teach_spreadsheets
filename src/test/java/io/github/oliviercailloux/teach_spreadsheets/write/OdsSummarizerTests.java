package io.github.oliviercailloux.teach_spreadsheets.write;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class OdsSummarizerTests {
	private final static Logger LOGGER = LoggerFactory.getLogger(OdsSummarizerTests.class);

	@Test
	/**
	 * this test creates 2 assignments and writes in memory.
	 * 
	 * @throws Exception
	 */
	void testCreateSummary() throws Exception {
		/**
		 * Creation of 2 test assignments.
		 */
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
		/**
		 * Ods creation and writing.
		 */
		OdsSummarizer ods = OdsSummarizer.newInstance(courses);
		ods.addPrefs(prefs);
		long startTimeAddingAssignement = System.nanoTime();
		LOGGER.info("2 assignments : start assignment and write");
		ods.setAllCoursesAssigned(allCoursesAssigned);
		LOGGER.info("2 assignments : stop assignment");
		long startTimeWriting = System.nanoTime();
		URL resourceUrl = OdsSummarizer.class.getResource("OdsSummarizer.ods");
		try (InputStream stream = resourceUrl.openStream();
				SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream);
				SpreadsheetDocument documentCreated = ods.createSummary()) {
			LOGGER.info("2 assignments : stop writing in memory");
			long elapsedNanosWriteAndAssign = System.nanoTime() - startTimeAddingAssignement;
			long elapsedNanosWrite = System.nanoTime() - startTimeWriting;
			LOGGER.info("2 assignements : Time to write and assign : "
					+ TimeUnit.NANOSECONDS.toSeconds(elapsedNanosWriteAndAssign) + "s" + " " + "Time to write : "
					+ TimeUnit.NANOSECONDS.toSeconds(elapsedNanosWrite) + "s");
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

	@Test
	/**
	 * this test creates 100 assignments and writes on disk.
	 * 
	 * @throws Exception
	 */
	void testWriting100Summary() throws Exception {
		Set<Course> courses = new LinkedHashSet<>();
		Set<CoursePref> prefs = new LinkedHashSet<>();

		Set<CourseAssignment> allCoursesAssigned = new LinkedHashSet<>();
		/**
		 * Creation of the 100 test assignments.
		 */
		for (int i = 1; i <= 100; i++) {
			Course course = Course.Builder.newInstance().setName("testcourse" + i).setStudyYear(2016)
					.setStudyLevel("DE1").setSemester(1).setCountGroupsCM(3).setCountGroupsTD(4).setNbMinutesCM(60)
					.setNbMinutesTD(60).build();
			courses.add(course);
			Teacher teacher = Teacher.Builder.newInstance().setFirstName("teacher" + i + "FirstName")
					.setLastName("teacher" + i + "LastName").build();
			prefs.add(CoursePref.Builder.newInstance(course, teacher).setPrefCM(Preference.A).setPrefTD(Preference.B)
					.build());
			TeacherAssignment teacherAssignment = TeacherAssignment.Builder.newInstance(course, teacher)
					.setCountGroupsTD(1).build();
			CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course);
			courseAssignmentBuilder.addTeacherAssignment(teacherAssignment);
			CourseAssignment courseAssignment = courseAssignmentBuilder.build();
			allCoursesAssigned.add(courseAssignment);

		}
		/**
		 * Ods creation and writing.
		 */
		OdsSummarizer ods = OdsSummarizer.newInstance(courses);
		ods.addPrefs(prefs);
		long startTimeAddingAssignement = System.nanoTime();
		LOGGER.info("100 assignments : start assignment and write");
		ods.setAllCoursesAssigned(ImmutableSet.copyOf(allCoursesAssigned));
		LOGGER.info("100 assignments : stop assignment");
		long startTimeWriting = System.nanoTime();
		try (SpreadsheetDocument documentCreated = ods.createSummary()) {
			if (!Files.exists(Path.of("output"))) {
				Files.createDirectory(Path.of("output"));
			}
			documentCreated.save("output//testWriting100.ods");
			LOGGER.info("100 assignments : stop writing on disk");
		}
		long elapsedNanosWriteAndAssign = System.nanoTime() - startTimeAddingAssignement;
		long elapsedNanosWrite = System.nanoTime() - startTimeWriting;
		LOGGER.info("100 affectations : Time to write and assign : "
				+ TimeUnit.NANOSECONDS.toSeconds(elapsedNanosWriteAndAssign) + "s" + " " + "Time to write : "
				+ TimeUnit.NANOSECONDS.toSeconds(elapsedNanosWrite) + "s");
	}
}
