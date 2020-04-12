package io.github.oliviercailloux.teach_spreadsheets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TeacherTests {
	@Test
	void testToString() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setAddress("Pont du maréchal de lattre de tassigny");

		String actual = teacherBuilder.build().toString();
		String expected = "Teacher{lastName=Doe, firstName=, address=Pont du maréchal de lattre de tassigny, postCode=, city=, personalPhone=, mobilePhone=, personalEmail=, dauphineEmail=, status=, dauphinePhoneNumber=, office=}";

		assertEquals(expected, actual);
	}
}
