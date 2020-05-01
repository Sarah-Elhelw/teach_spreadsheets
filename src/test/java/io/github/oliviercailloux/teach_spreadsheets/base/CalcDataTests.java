package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CalcDataTests {
	@Test
	void testNewInstance() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setLastName("Doe");
		Teacher teacher = teacherBuilder.build();

		Course.Builder courseBuilder = Course.Builder.newInstance();
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setName("Java");
		courseBuilder.setStudyYear("2012");
		courseBuilder.setSemester(1);

		CoursePref.Builder coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(), teacher);
		coursePrefBuilder.setPrefCM(Preference.A);
		coursePrefBuilder.setPrefNbGroupsCM(9);

		CoursePref coursePref1 = coursePrefBuilder.build();

		courseBuilder.setCountGroupsCM(3);
		courseBuilder.setnbMinutesCM(2);
		courseBuilder.setName("Java");
		courseBuilder.setStudyYear("2015");
		courseBuilder.setSemester(2);

		coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(), teacher);
		coursePrefBuilder.setPrefCM(Preference.A);
		coursePrefBuilder.setPrefNbGroupsCM(2);

		CoursePref coursePref2 = coursePrefBuilder.build();

		ImmutableSet<CoursePref> coursePrefs = ImmutableSet.copyOf(new CoursePref[] { coursePref1, coursePref2 });

		assertThrows(IllegalArgumentException.class, () -> {
			CalcData.newInstance(coursePrefs, teacher);
		});
		
	}

	@Test
	void testGetCoursePref() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setLastName("Doe");
		Teacher teacher = teacherBuilder.build();

		Course.Builder courseBuilder = Course.Builder.newInstance();
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setName("Analyse de données");
		courseBuilder.setStudyYear("2012");
		courseBuilder.setSemester(1);

		CoursePref.Builder coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(), teacher);
		coursePrefBuilder.setPrefCM(Preference.A);
		coursePrefBuilder.setPrefNbGroupsCM(2);

		CoursePref coursePref1 = coursePrefBuilder.build();

		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setName("Java");
		courseBuilder.setStudyYear("2013");
		courseBuilder.setSemester(2);

		coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(), teacher);
		coursePrefBuilder.setPrefCM(Preference.A);
		coursePrefBuilder.setPrefNbGroupsCM(2);

		CoursePref coursePref2 = coursePrefBuilder.build();

		ImmutableSet<CoursePref> coursePrefs = ImmutableSet.copyOf(new CoursePref[] { coursePref1, coursePref2 });

		CalcData calcData = CalcData.newInstance(coursePrefs, teacher);
		
		assertThrows(IllegalArgumentException.class, () -> {
			calcData.getCoursePref("programmation");
		});
		
		String expected = "CoursePref{prefCM=A, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=2, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Java, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbMinutesTD=0, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=20, studyYear=2013, semester=2}, Teacher=Teacher{lastName=Doe, firstName=, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
		String actual = calcData.getCoursePref("Java").toString();
		
		assertEquals(expected, actual);
	}
}
