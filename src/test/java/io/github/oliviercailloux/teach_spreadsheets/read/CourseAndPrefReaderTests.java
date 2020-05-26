package io.github.oliviercailloux.teach_spreadsheets.read;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;

import com.google.common.collect.ImmutableSet;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.read.CourseAndPrefReader;

public class CourseAndPrefReaderTests {

	/** Creating a fake teacher :*/
	static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").build();

	@Test
	void testSetInfoCourse() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				CourseAndPrefReader courseAndPrefReader = CourseAndPrefReader.newInstance();
				Course.Builder courseBuilder = Course.Builder.newInstance();
				Table sheet = document.getTableByName("DE1");
				courseAndPrefReader.setInfoCourse(sheet, courseBuilder, 1, 3, 1);
				Course course = courseBuilder.build();
				String expectedName = "PRE-RENTREE :  Mathématiques";
				int expectedNbHoursCMTD = 15 * 60;
				int expectedCountGroupsCMTD = 6;
				assertEquals(expectedName, course.getName());
				assertEquals(expectedCountGroupsCMTD, course.getCountGroupsCMTD());
				assertEquals(expectedNbHoursCMTD, course.getNbMinutesCMTD());
			}
		}
	}
	
	@Test
	void testSetInfoPref() throws Exception { 
		/** Checking the preferences for "PRE-RENTREE : Mathématiques" */
		CourseAndPrefReader courseAndPrefReader = CourseAndPrefReader.newInstance();
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				Table sheet = document.getTableByName("DE1");

				Course.Builder courseBuilder = Course.Builder.newInstance();
				courseAndPrefReader.setInfoCourse(sheet, courseBuilder, 1, 3, 1);
				Course course = courseBuilder.build();

				CoursePref.Builder coursePrefBuilder = CoursePref.Builder.newInstance(course, teacher);
				
				courseAndPrefReader.setInfoPref(sheet, coursePrefBuilder, 9, 3);
				CoursePref coursePref = coursePrefBuilder.build();
				
				/** Checking the filling : */
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefCM());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefTD());
				assertEquals(Preference.A, coursePref.getPrefCMTD());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefTP());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefCMTP());
				assertEquals(0, coursePref.getPrefNbGroupsCM());
				assertEquals(0, coursePref.getPrefNbGroupsTD());
				assertEquals(1, coursePref.getPrefNbGroupsCMTD());
				assertEquals(0, coursePref.getPrefNbGroupsTP());
				assertEquals(0, coursePref.getPrefNbGroupsCMTP());
				
			}
		}
	}

	@Test
	void testreadSemester() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				Table sheet = document.getTableByName("DE1");
				CourseAndPrefReader courseAndPrefReader = CourseAndPrefReader.newInstance();

				ImmutableSet<CoursePref> coursePrefList = courseAndPrefReader.readSemester(sheet, teacher);
				
				CoursePref coursePref = coursePrefList.asList().get(3);
				Course course = coursePref.getCourse();
				
				/** Checking the filling of the course line 7, semester 1:*/
				
				assertEquals("Algorithmique et programmation 1", course.getName());
				assertEquals(2016, course.getStudyYear());
				assertEquals("DE1", course.getStudyLevel());
				assertEquals(1800, course.getNbMinutesCMTD());
				assertEquals(1800, course.getNbMinutesCMTP());
				assertEquals(4, course.getCountGroupsCMTD());
				assertEquals(5, course.getCountGroupsCMTP());
				
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefCM());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefTD());
				assertEquals(Preference.C, coursePref.getPrefCMTD());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefTP());
				assertEquals(Preference.A, coursePref.getPrefCMTP());
				assertEquals(0, coursePref.getPrefNbGroupsCM());
				assertEquals(0, coursePref.getPrefNbGroupsTD());
				assertEquals(1, coursePref.getPrefNbGroupsCMTD());
				assertEquals(0, coursePref.getPrefNbGroupsTP());
				assertEquals(1, coursePref.getPrefNbGroupsCMTP());
			}
		}
	}
}
