package io.github.oliviercailloux.teach_spreadsheets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CoursePrefTests {
	@Test
	void testToString() {
		Teacher.Builder teacherBuilder = new Teacher.Builder();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");
		
		Course.Builder courseBuilder = new Course.Builder();
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setName("Analyse de données");
		
		CoursePref.Builder coursePrefBuilder = new CoursePref.Builder();
		coursePrefBuilder.setPrefCM('A');
		coursePrefBuilder.setPrefTD('A');
		coursePrefBuilder.setCourse(courseBuilder.build());
		coursePrefBuilder.setTeacher(teacherBuilder.build());
		
		String actual = coursePrefBuilder.build().toString();
		String expected = "CoursePref{prefCM=A, prefTD=A, prefCMTD= , prefTP= , prefCMTP= , prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Analyse de données, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbHoursTD=0, nbHoursCMTD=0, nbHoursTP=0, nbHoursCMTP=0, nbHoursCM=0, studyYear=, semester=0}, Teacher=Teacher{lastName=, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}}";
		assertEquals(expected, actual);
		
	}
}
