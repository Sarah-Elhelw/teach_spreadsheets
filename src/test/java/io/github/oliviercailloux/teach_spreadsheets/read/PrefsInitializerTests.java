package io.github.oliviercailloux.teach_spreadsheets.read;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;

import org.junit.jupiter.api.Test;

import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class PrefsInitializerTests {

	/** Creating a fake teacher : */
	static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").build();

	@Test
	void testCreatePrefsList() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				ImmutableSet<CoursePref> coursePrefsSet = PrefsInitializer.createPrefslist(document, teacher);

				/**
				 * Checking the preferences for the course in cell P12 in the sheet DE1 : the
				 * aim here is to test that we can read course preferences in the semester 2.
				 */
				String expectedP12 = "CoursePref{prefCM=UNSPECIFIED, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Anglais 2, countGroupsTD=0, countGroupsCMTD=12, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=1, nbMinutesTD=0, nbMinutesCMTD=1170, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=0, studyYear=2016/2017, semester=2}, Teacher=Teacher{lastName=Doe, firstName=, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
				assertEquals(expectedP12, coursePrefsSet.asList().get(18).toString());

				/**
				 * Checking the preferences for the course in cell B4 in the sheet DE2 : the aim
				 * here is to test that we can read course preferences in another sheet than
				 * DE1.
				 */
				String expectedB4 = "CoursePref{prefCM=UNSPECIFIED, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=TestCoursDE2S1, countGroupsTD=0, countGroupsCMTD=1, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=1, nbMinutesTD=0, nbMinutesCMTD=60, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=60, studyYear=2016/2017, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
				assertEquals(expectedB4, coursePrefsSet.asList().get(22).toString());

			}
		}
	}
}
