package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.json.bind.JsonbException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

public class JsonReadTests {

	@Test
	void testGetSetOfCoursesInfo() throws Exception {
		URL resourceUrl = JsonRead.class.getResource("Courses.json");
		final Path path = Path.of(resourceUrl.toURI());
		final String textFile = JsonRead.formatToArray(Files.readString(path));

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
	void testGetSetOfCoursesInfoWithNullName() throws Exception {
		URL resourceUrl = JsonRead.class.getResource("CoursesWithNullName.json");
		final Path path = Path.of(resourceUrl.toURI());
		final String textFile = JsonRead.formatToArray(Files.readString(path));
		Throwable exception = assertThrows(JsonbException.class, () -> {
			JsonRead.getSetOfCoursesInfo(textFile);
		});
		assertEquals("String must not be null.", ExceptionUtils.getRootCause(exception).getMessage());
	}
	
	@Test
	void testDemonstratingNeedForFormatToArray() throws Exception {
		URL resourceUrl = JsonRead.class.getResource("Courses.json");
		final Path path = Path.of(resourceUrl.toURI());
		final String textFile = Files.readString(path);
		
		ImmutableSet.Builder<Course> isb = new ImmutableSet.Builder<>();
		assertEquals(isb.build(), JsonRead.getSetOfCoursesInfo(textFile));
	}

}
