package io.github.oliviercailloux.teach_spreadsheets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.github.oliviercailloux.teach_spreadsheets.Course;

public class CourseTests {
	@Test
	void testToString() {
		Course.Builder courseBuilder = Course.Builder.newInstance();
		
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setName("Analyse de données");
		
		String actual = courseBuilder.build().toString();
		String expected = "Course{name=Analyse de données, countGroupsTD=0, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=10, nbHoursTD=0, nbHoursCMTD=0, nbHoursTP=0, nbHoursCMTP=0, nbHoursCM=0, studyYear=, semester=0}";
		
		assertEquals(expected, actual);
	}
}
