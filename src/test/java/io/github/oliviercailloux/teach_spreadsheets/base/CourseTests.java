package io.github.oliviercailloux.teach_spreadsheets.base;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTests {
	@Test
	void testToString() {
		Course.Builder courseBuilder = Course.Builder.newInstance();

		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setName("Analyse de données");
		courseBuilder.setStudyYear("2014");

		String actual = courseBuilder.build().toString();
		String expected = "Course{name=Analyse de données, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbMinutesTD=0, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=20, studyYear=2014, semester=1}";

		assertEquals(expected, actual);
	}
}
