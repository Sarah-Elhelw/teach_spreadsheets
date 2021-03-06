package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.json.bind.JsonbException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

public class JsonDeserializerTests {

	@Test
	void testToCourses() throws Exception {
		URL resourceUrl = JsonDeserializer.class.getResource("Courses.json");
		if(resourceUrl != null) {
			final Path path = Path.of(resourceUrl.toURI());

			ImmutableSet<Course> courses = JsonDeserializer.toCourses(Files.readString(path));
			assertTrue(courses.size() == 2);

			Course actualCourse = courses.asList().get(1);
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
			assertEquals(2016, actualCourse.getStudyYear());
			assertEquals("DE1", actualCourse.getStudyLevel());
			assertEquals(1, actualCourse.getSemester());
		}
		

	}

	/**
	 * The aim of this test is to check that the process of deserialization uses the
	 * setters in {@link Course} class.
	 */
	@Test
	void testToCoursesWithNullName() throws Exception {
		URL resourceUrl = JsonDeserializer.class.getResource("CoursesWithNullName.json");
		if(resourceUrl != null) {
			final Path path = Path.of(resourceUrl.toURI());
			Throwable exception = assertThrows(JsonbException.class, () -> {
				JsonDeserializer.toCourses(Files.readString(path));
			});
			assertEquals("String must not be null.", ExceptionUtils.getRootCause(exception).getMessage());
		}
		
	}

}
