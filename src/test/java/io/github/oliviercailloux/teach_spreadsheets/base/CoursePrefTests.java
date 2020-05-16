package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CoursePrefTests {
	@Test
	void testToString() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");
		teacherBuilder.setLastName("Doe");

		Course.Builder courseBuilder = Course.Builder.newInstance();
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setCountGroupsCMTD(10);
		courseBuilder.setnbMinutesCMTD(20);
		courseBuilder.setName("Analyse de données");
		courseBuilder.setStudyYear(2012);
		courseBuilder.setStudyLevel("DE1");
		courseBuilder.setSemester(1);

		CoursePref.Builder coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(),
				teacherBuilder.build());
		coursePrefBuilder.setPrefCM(Preference.A);
		coursePrefBuilder.setPrefCMTD(Preference.B);

		String actual = coursePrefBuilder.build().toString();
		String expected = "CoursePref{prefCM=A, prefTD=UNSPECIFIED, prefCMTD=B, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Analyse de données, countGroupsTD=0, countGroupsCMTD=10, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbMinutesTD=0, nbMinutesCMTD=20, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=20, studyLevel=DE1, studyYear=2012, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
		assertEquals(expected, actual);

	}
}
