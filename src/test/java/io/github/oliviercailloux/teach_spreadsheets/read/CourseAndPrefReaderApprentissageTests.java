package io.github.oliviercailloux.teach_spreadsheets.read;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CourseAndPrefReaderApprentissageTests {
	/** Creating a fake teacher :*/
	static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").build();
	@Test
	void testreadSemester() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				Table sheet = document.getTableByName("APPRENTISSAGE");
				CourseAndPrefReaderApprentissage courseAndPrefReader = CourseAndPrefReaderApprentissage.newInstance();

				ImmutableSet<CoursePref> coursePrefList = courseAndPrefReader.readApprentissage(sheet, teacher);

				CoursePref actualCoursePref=coursePrefList.asList().get(0);
				
				Course actualCourse=actualCoursePref.getCourse();
				Teacher acutalTeacher=actualCoursePref.getTeacher();
				
				assertEquals("Doe", acutalTeacher.getLastName());
				assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCM());
				assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefTD());
				assertEquals(Preference.B, actualCoursePref.getPrefCMTD());
				assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefTP());
				assertEquals(Preference.UNSPECIFIED, actualCoursePref.getPrefCMTP());
				assertEquals(0, actualCoursePref.getPrefNbGroupsCM());
				assertEquals(0, actualCoursePref.getPrefNbGroupsTD());
				assertEquals(1, actualCoursePref.getPrefNbGroupsCMTD());
				assertEquals(0, actualCoursePref.getPrefNbGroupsTP());
				assertEquals(0, actualCoursePref.getPrefNbGroupsCMTP());
				assertEquals("Remise à Niveau en Maths", actualCourse.getName());
				assertEquals(0, actualCourse.getCountGroupsTD());
				assertEquals(1, actualCourse.getCountGroupsCMTD());
				assertEquals(0, actualCourse.getCountGroupsTP());
				assertEquals(0, actualCourse.getCountGroupsCMTP());
				assertEquals(0, actualCourse.getCountGroupsCM());
				assertEquals(0, actualCourse.getNbMinutesTD());
				assertEquals(720, actualCourse.getNbMinutesCMTD());
				assertEquals(0, actualCourse.getNbMinutesTP());
				assertEquals(0, actualCourse.getNbMinutesCMTP());
				assertEquals(0, actualCourse.getNbMinutesCM());
				assertEquals("APPRENTISSAGE", actualCourse.getStudyYear());
				assertEquals(1, actualCourse.getSemester());
			}
		}
	}

}
