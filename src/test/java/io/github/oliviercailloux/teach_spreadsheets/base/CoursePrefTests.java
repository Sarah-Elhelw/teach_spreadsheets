package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CoursePrefTests {
	@Test
	void testCoherence() {

		Teacher teacher = Teacher.Builder.newInstance().setAddress("Pont du maréchal de lattre de tassigny")
				.setLastName("Doe").build();

		Course course = Course.Builder.newInstance().setCountGroupsCMTD(10).setNbMinutesCMTD(20)
				.setName("Analyse de données").setStudyYear(2012).setStudyLevel("DE1").setSemester(1).build();

		assertThrows(IllegalArgumentException.class, () -> {
			CoursePref.Builder.newInstance(course, teacher).setPrefTP(Preference.A).build();
		});

		assertThrows(IllegalArgumentException.class, () -> {
			CoursePref.Builder.newInstance(course, teacher).setPrefCMTD(Preference.A).setPrefNbGroupsCMTD(21).build();
		});

		assertThrows(IllegalArgumentException.class, () -> {
			CoursePref.Builder.newInstance(course, teacher).setPrefNbGroupsCMTD(1).build();
		});

	}
}
