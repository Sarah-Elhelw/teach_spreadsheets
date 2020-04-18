package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;
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

	@Test
	void testBuilderWithNegativeCountGroupsTD() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance();
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
				teacherAssignmentBuilder.setCountGroupsTD(-1);
		});
		Assertions.assertEquals("The number of TD groups must be positive.", exception.getMessage());
	}

	@Test
	void testBuilderWithNoTeacher() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance();
		teacherAssignmentBuilder.setCountGroupsTD(2);
		Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
				teacherAssignmentBuilder.build();
		});
		Assertions.assertEquals("You cannot build a TeacherAssignment without a Teacher.", exception.getMessage());
	}

	@Test
	void testSetTeacherWithNullTeacher() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance();
		Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
				teacherAssignmentBuilder.setTeacher(null);
		});
		Assertions.assertEquals("The teacher assigned must not be null.", exception.getMessage());
	}
}
