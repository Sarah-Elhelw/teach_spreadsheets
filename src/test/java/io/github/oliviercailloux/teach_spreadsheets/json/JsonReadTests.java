package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.json.bind.JsonbException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class JsonReadTests {

	@Test
	void testGetSetOfCoursesInfo() throws Exception {
		URL resourceUrl = JsonRead.class.getResource("Courses.json");
		final Path path = Path.of(resourceUrl.toURI());
		final String textFile = Files.readString(path);

		Course actualCourse = JsonRead.getSetOfCoursesInfo(textFile).asList().get(1);

		assertEquals("Analyse 1", actualCourse.getName());
		assertEquals(0, actualCourse.getCountGroupsTD());
		assertEquals(5, actualCourse.getCountGroupsCMTD());
		assertEquals(0, actualCourse.getCountGroupsTP());
		assertEquals(0, actualCourse.getCountGroupsCMTP());
		assertEquals(0, actualCourse.getCountGroupsCM());
		assertEquals(0, actualCourse.getNbMinutesTD());
		assertEquals(450, actualCourse.getNbMinutesCMTD());
		assertEquals(0, actualCourse.getNbMinutesTP());
		assertEquals(0, actualCourse.getNbMinutesCMTP());
		assertEquals(0, actualCourse.getNbMinutesCM());
		assertEquals("2016/2017", actualCourse.getStudyYear());
		assertEquals(1, actualCourse.getSemester());

	}

	/**
	 * The aim of this test is to check that the process of deserialization uses the
	 * setters in {@link Course} class.
	 */
	@Test
	void testGetSetOfCoursesInfoWithNullName() throws URISyntaxException, IOException {
		URL resourceUrl = JsonRead.class.getResource("CoursesWithNullName.json");
		final Path path = Path.of(resourceUrl.toURI());
		final String textFile = Files.readString(path);
		Throwable exception = assertThrows(JsonbException.class, () -> {
			JsonRead.getSetOfCoursesInfo(textFile);
		});
		assertEquals("String must not be null.", ExceptionUtils.getRootCause(exception).getMessage());
	}

	/**
	 * The aim of this test is to check that getSetOfTeachersInfo() returns the
	 * proper ImmutableSet of teachers read on RefRof. For confidentiality reasons,
	 * LoginRefRof.json is ignored by git and hence not published on github. For
	 * this test to function, it is needed to create a file LoginRefRof.json in the
	 * directory
	 * src\test\resources\io\github\oliviercailloux\teach_spreadsheets\json with the
	 * format specified in {@link JsonRead}.
	 */
	@Test
	void testGetSetOfTeachersInfo() throws Exception {
		URL resourceUrl = JsonRead.class.getResource("LoginRefRof.json");
		final Path path = Path.of(resourceUrl.toURI());
		String jsonLogin = Files.readString(path);

		JsonRead.authentification(jsonLogin);

		Teacher actualTeacher = JsonRead.getSetOfTeachersInfo("https://rof.testapi.dauphine.fr/ebx-dataservices/rest/data/v1/BpvRefRof/RefRof/root/Person").asList().get(1);

		assertEquals("BRACI", actualTeacher.getLastName());
		assertEquals("LINA", actualTeacher.getFirstName());
		assertEquals("", actualTeacher.getAddress());
		assertEquals("", actualTeacher.getPostCode());
		assertEquals("", actualTeacher.getCity());
		assertEquals("", actualTeacher.getPersonalPhone());
		assertEquals("", actualTeacher.getMobilePhone());
		assertEquals("", actualTeacher.getPersonalEmail());
		assertEquals("lina.braci@dauphine.fr", actualTeacher.getDauphineEmail());
		assertEquals("PROF.UNIV.", actualTeacher.getStatus());
		assertEquals("", actualTeacher.getDauphinePhoneNumber());
		assertEquals("", actualTeacher.getOffice());

	}
}
