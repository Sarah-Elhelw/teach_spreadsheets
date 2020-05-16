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
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.read.CourseAndPrefReader;

public class CourseAndPrefReaderTests {

	/** Creating a fake teacher :*/
	static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").build();

	@Test
	void testSetInfoPref() throws Exception { 
		/** Checking the preferences for "PRE-RENTREE : Mathématiques" */
		CourseAndPrefReader courseAndPrefReader = CourseAndPrefReader.newInstance();
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				Table sheet = document.getTableByName("DE1");

				/** Creating a fake course : */
				Course.Builder courseBuilder = Course.Builder.newInstance();
				courseBuilder.setName("PRE-RENTREE : Mathématiques");
				courseBuilder.setCountGroupsCMTD(6);
				courseBuilder.setnbMinutesCMTD(15);
				courseBuilder.setSemester(1);
				courseBuilder.setStudyYear(2016);
				courseBuilder.setStudyLevel("DE1");
				Course course = courseBuilder.build();

				/** Creating and filling the coursePref, By default, the semester is initialized */
				CoursePref.Builder coursePrefBuilder = CoursePref.Builder.newInstance(course, teacher);
				/** Beware : the number of rows and columns starts at 0.*/																						// with 1.
				courseAndPrefReader.setInfoPref(sheet, coursePrefBuilder, 9, 3);
				CoursePref coursePref = coursePrefBuilder.build();
				/** Checking the filling : */
				String actual = coursePref.toString();
				String expected = "CoursePref{prefCM=UNSPECIFIED, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=1, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=PRE-RENTREE : Mathématiques, countGroupsTD=0, countGroupsCMTD=6, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=0, nbMinutesTD=0, nbMinutesCMTD=15, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=0, studyLevel=DE1, studyYear=2016, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
				assertEquals(expected, actual);
			}
		}
	}

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
	void testreadSemester() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				Table sheet = document.getTableByName("DE1");
				CourseAndPrefReader courseAndPrefReader = CourseAndPrefReader.newInstance();

				ImmutableSet<CoursePref> coursePrefList = courseAndPrefReader.readSemester(sheet, teacher);

				String expectedLine7 = "CoursePref{prefCM=UNSPECIFIED, prefTD=UNSPECIFIED, prefCMTD=C, prefTP=UNSPECIFIED, prefCMTP=A, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=1, prefNbGroupsTP=0, prefNbGroupsCMTP=1, Course=Course{name=Algorithmique et programmation 1, countGroupsTD=0, countGroupsCMTD=4, countGroupsTP=0, countGroupsCMTP=5, countGroupsCM=1, nbMinutesTD=0, nbMinutesCMTD=1800, nbMinutesTP=0, nbMinutesCMTP=1800, nbMinutesCM=0, studyLevel=DE1, studyYear=2016, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
				String expectedLine9 = "CoursePref{prefCM=B, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Problèmes économiques, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=1, nbMinutesTD=0, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=2160, studyLevel=DE1, studyYear=2016, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";

				assertEquals(expectedLine7, coursePrefList.asList().get(3).toString());
				assertEquals(expectedLine9, coursePrefList.asList().get(5).toString());
			}
		}
	}
}
