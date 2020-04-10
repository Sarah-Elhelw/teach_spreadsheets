package io.github.oliviercailloux.teach_spreadsheets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

public class CalcDataTests {
	@Test
	void testNewInstance() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");
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
		
		CoursePref coursePref1 = coursePrefBuilder.build();
		
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setName("Java");
		courseBuilder.setStudyYear("2012");
		courseBuilder.setSemester(1);
		
		coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(), teacher);
		
		coursePrefBuilder.setPrefCM(Preference.A);
		
		CoursePref coursePref2 = coursePrefBuilder.build();
		
		ImmutableSet<CoursePref> coursePrefs = ImmutableSet.copyOf(new CoursePref[] {coursePref1, coursePref2});
		
		CalcData calcData = CalcData.newInstance(coursePrefs, teacher);
		String actual = calcData.toString();
		
		String expected = "CalcData{coursePrefs=[CoursePref{prefCM=A, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Analyse de données, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbMinutesTD=0, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=20, studyYear=2012, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}, CoursePref{prefCM=A, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Java, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbMinutesTD=0, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=20, studyYear=2012, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}], teacher=Teacher{lastName=Doe, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetCoursePref() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");
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
		
		CoursePref coursePref1 = coursePrefBuilder.build();
		
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setName("Java");
		courseBuilder.setStudyYear("2013");
		courseBuilder.setSemester(2);
		
		coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(), teacher);
		
		coursePrefBuilder.setPrefCM(Preference.A);
		
		CoursePref coursePref2 = coursePrefBuilder.build();
		
		ImmutableSet<CoursePref> coursePrefs = ImmutableSet.copyOf(new CoursePref[] {coursePref1, coursePref2});
		
		CalcData calcData = CalcData.newInstance(coursePrefs, teacher);
		String actual = calcData.getCoursePref(null).toString();
		
		String expected = "CoursePref{prefCM=A, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Java, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbMinutesTD=0, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=20, studyYear=2013, semester=1}, Teacher=Teacher{lastName=Doe, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
		assertEquals(expected, actual);
	}
}
