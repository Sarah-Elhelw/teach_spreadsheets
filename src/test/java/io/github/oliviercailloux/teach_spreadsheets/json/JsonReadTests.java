package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.json.bind.JsonbException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

public class JsonReadTests {

	@Test
	void testGetSetOfCoursesInfo() throws Exception {
		String actualIs = JsonRead.getSetOfCoursesInfo("Courses.json").toString();
		String expectedIS = "[Course{name=PRE-RENTREE : Mathématiques, countGroupsTD=0, countGroupsCMTD=6, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=0, nbMinutesTD=0, nbMinutesCMTD=900, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=0, studyYear=2016/2017, semester=1}, Course{name=Analyse 1, countGroupsTD=0, countGroupsCMTD=5, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=0, nbMinutesTD=0, nbMinutesCMTD=450, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=0, studyYear=2016/2017, semester=1}]";
		assertEquals(expectedIS, actualIs);
	}

	/**
	 * The aim of this test is to check that the process of deserialization uses the
	 * setters in {@link Course} class.
	 */
	@Test
	void testGetSetOfCoursesInfoWithNullName() {
		Throwable exception = assertThrows(JsonbException.class, () -> {
			JsonRead.getSetOfCoursesInfo("CoursesWithNullName.json");
		});
		assertEquals("String must not be null.", ExceptionUtils.getRootCause(exception).getMessage());
	}

	/**
	 * The aim of this test is to check that getSetOfTeachersInfo() returns the
	 * proper ImmutableSet of teachers read on RefRof. For confidentiality reasons,
	 * LoginRefRof.json is ignored by git and hence not published on github. For this
	 * test to function, it is needed to create a file LoginRefRof.json in the
	 * directory
	 * src\test\resources\io\github\oliviercailloux\teach_spreadsheets\json with the
	 * format specified in {@link JsonRead}.
	 */
	@Test
	void testGetSetOfTeachersInfo() throws Exception {
		String actualIs = JsonRead.getSetOfTeachersInfo("LoginRefRof.json", "https://rof.testapi.dauphine.fr/ebx-dataservices/rest/data/v1/BpvRefRof/RefRof/root/Person").asList().get(1).toString();
		String expectedIS = "Teacher{lastName=BRACI, firstName=LINA, address=, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=lina.braci@dauphine.fr, status=PROF.UNIV., dauphinePhoneNumber=, office=}";
		assertEquals(expectedIS, actualIs);
	}
}
