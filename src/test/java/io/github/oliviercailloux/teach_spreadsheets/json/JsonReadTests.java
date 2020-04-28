package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.json.bind.JsonbException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;


public class JsonReadTests {

	@Test
	void testGetSetOfCoursesInfo() throws Exception{
		String actualIs = JsonRead.getSetOfCoursesInfo("Courses.json").toString();
		String expectedIS = "[Course{name=PRE-RENTREE : Mathématiques, countGroupsTD=0, countGroupsCMTD=6, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=0, nbMinutesTD=0, nbMinutesCMTD=900, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=0, studyYear=2016/2017, semester=1}, Course{name=Analyse 1, countGroupsTD=0, countGroupsCMTD=5, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=0, nbMinutesTD=0, nbMinutesCMTD=450, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=0, studyYear=2016/2017, semester=1}]";
		assertEquals(expectedIS, actualIs);
	}
	
	/**
	 * The aim of this test is to check that the process of deserialization uses the setters in {@link Course} class.
	 */
	@Test
	void testGetSetOfCoursesInfoWithNullName(){
		Throwable exception = assertThrows(JsonbException.class, () -> {
			JsonRead.getSetOfCoursesInfo("CoursesWithNullName.json");
		});
		assertEquals("String must not be null.", ExceptionUtils.getRootCause(exception).getMessage());
	}
}
