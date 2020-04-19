package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

import org.junit.jupiter.api.Assertions;

/**
 * This test class checks that the Builder and the setter setTeacher() prevent
 * from building incoherent teachers'assignments by throwing exceptions.
 * 
 * To handle the throwing of exceptions in unit testing, we took inspiration
 * from <a href=
 * "https://www.codejava.net/testing/junit-test-exception-examples-how-to-assert-an-exception-is-thrown">this
 * code.</a>
 *
 */
public class TeacherAssignmentTests {

	private static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").setFirstName("John").build();

	@Test
	void testBuilderWithNegativeCountGroupsTD() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance(teacher);
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			teacherAssignmentBuilder.setCountGroupsTD(-1);
		});
		Assertions.assertEquals("The number of TD groups must be positive.", exception.getMessage());
	}

	@Test
	void testSettingTeacherWithNullTeacher() {
		Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
			TeacherAssignment.Builder.newInstance(null);
		});
		Assertions.assertEquals("The teacher assigned must not be null.", exception.getMessage());
	}
}
