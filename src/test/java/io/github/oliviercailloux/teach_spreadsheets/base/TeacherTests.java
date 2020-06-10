package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeacherTests {
	@Test
	void testBuildWithoutLastName() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");

		assertThrows(IllegalArgumentException.class, () -> {
			teacherBuilder.build();
		});

		teacherBuilder.setLastName("");
		assertThrows(IllegalArgumentException.class, () -> {
			teacherBuilder.build();
		});
	}
}