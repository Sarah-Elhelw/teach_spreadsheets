package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeacherTests {
	@Test
	void testBuild() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");
		teacherBuilder.setLastName("Doe");

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			teacherBuilder.build();
		});
		assertEquals("The dauphine email must be specified.", exception.getMessage());

	}
}
