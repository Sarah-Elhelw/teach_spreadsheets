package io.github.oliviercailloux.teach_spreadsheets.read;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;

import org.junit.jupiter.api.Test;

import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;

public class PrefsInitializerTests {

	/** Creating a fake teacher : */
	static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").build();

	@Test
	void testCreatePrefsList() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				ImmutableSet<CoursePref> coursePrefsSet = PrefsInitializer.createPrefslist(document, teacher);

				Course course;
				CoursePref coursePref;

				/**
				 * Checking the preferences for the course in cell P12 in the sheet DE1 : the
				 * aim here is to test that we can read course preferences in the semester 2.
				 */

				coursePref = coursePrefsSet.asList().get(18);
				course = coursePref.getCourse();

				assertEquals("Anglais 2", course.getName());
				assertEquals(2016, course.getStudyYear());
				assertEquals("DE1", course.getStudyLevel());
				assertEquals(1170, course.getNbMinutesCMTD());
				assertEquals(12, course.getCountGroupsCMTD());

				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefCM());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefTD());
				assertEquals(Preference.A, coursePref.getPrefCMTD());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefTP());
				assertEquals(Preference.UNSPECIFIED, coursePref.getPrefCMTP());
				assertEquals(0, coursePref.getPrefNbGroupsCM());
				assertEquals(0, coursePref.getPrefNbGroupsTD());
				assertEquals(2, coursePref.getPrefNbGroupsCMTD());
				assertEquals(0, coursePref.getPrefNbGroupsTP());
				assertEquals(0, coursePref.getPrefNbGroupsCMTP());

				/**
				 * Checking the preferences for the course in cell B4 in the sheet DE2 : the aim
				 * here is to test that we can read course preferences in another sheet than
				 * DE1.
				 */

				coursePref = coursePrefsSet.asList().get(22);
				course = coursePref.getCourse();

				assertEquals("TestCoursDE2S1", course.getName());
				assertEquals(2016, course.getStudyYear());
				assertEquals("DE2", course.getStudyLevel());
				assertEquals(60, course.getNbMinutesCM());
				assertEquals(60, course.getNbMinutesCMTD());
				assertEquals(1, course.getCountGroupsCMTD());

			}
		}
	}
}
