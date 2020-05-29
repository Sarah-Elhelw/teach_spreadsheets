package io.github.oliviercailloux.teach_spreadsheets.base;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTests {
	@Test
	void testBuild() {
		Course.Builder courseBuilder1 = Course.Builder.newInstance().setCountGroupsCM(10).setNbMinutesCM(20)
				.setName("Analyse de données");
		Course.Builder courseBuilder2 = Course.Builder.newInstance().setCountGroupsCM(10).setNbMinutesCM(20)
				.setName("Analyse de données");
		Course.Builder courseBuilder3 = Course.Builder.newInstance().setCountGroupsCM(10).setNbMinutesCM(20)
				.setName("Analyse de données");

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			courseBuilder1.setStudyYear(2014).setStudyLevel("DE1").build();
		});
		assertEquals("The semester must be 1 or 2.", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			courseBuilder2.setSemester(1).setStudyLevel("DE1").build();
		});
		assertEquals("The study year must be specified.", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			courseBuilder3.setSemester(1).setStudyYear(2014).build();
		});
		assertEquals("The study level must be specified.", exception.getMessage());

	}
	
	@Test
	void testEquals() {
		Course course1 = Course.Builder.newInstance().setCountGroupsCMTD(5).setNbMinutesCMTD(1080)
				.setName("LV2 (Allemand / Espagnol)").setStudyYear(2016).setStudyLevel("DE1").setSemester(1).build();
		Course course2 = Course.Builder.newInstance().setCountGroupsCMTD(5).setNbMinutesCMTD(1080)
				.setName("LV2 (Allemand / Espagnol)").setStudyYear(2016).setStudyLevel("DE1").setSemester(1).build();
		Course course3 = Course.Builder.newInstance().setCountGroupsCMTD(5).setNbMinutesCMTD(1080)
				.setName("LV2 (Allemand / Espagnol)").setStudyYear(2016).setStudyLevel("DE1").setSemester(2).build();
		
		assertTrue(course1.equals(course2));
		assertFalse(course1.equals(course3));
	}
}
