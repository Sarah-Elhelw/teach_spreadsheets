package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
	@Test
	void testEquals() {
		Teacher teacher1 = Teacher.Builder.newInstance().setAddress("Pont du maréchal de lattre de tassigny")
				.setFirstName("John").setLastName("Doe").setDauphineEmail("john.doe@dauphine.fr").build();
		Teacher teacher2 = Teacher.Builder.newInstance().setAddress("Pont du maréchal de lattre de tassigny")
				.setFirstName("John").setLastName("Doe").setDauphineEmail("john.doe@dauphine.fr").build();
		Teacher teacher3 = Teacher.Builder.newInstance().setAddress("Pont du maréchal de lattre de tassigny")
				.setFirstName("Jane").setLastName("Doe").setDauphineEmail("jane.doe@dauphine.fr").build();
		
		assertTrue(teacher1.equals(teacher2));
		assertFalse(teacher1.equals(teacher3));
	}
}
