package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
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

	private static Course course = Course.Builder.newInstance().setName("Java").setCountGroupsTD(2).setNbMinutesTD(900)
			.setSemester(1).setStudyYear(2016).setStudyLevel("DE2").build();
	private static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").setFirstName("John").build();

	@Test
	void testBuilderWithIncorrectCountGroupsTD() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance(course, teacher);
		
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			teacherAssignmentBuilder.setCountGroupsTD(-1);
		});
		Assertions.assertEquals("The number of TD groups must be positive.", exception.getMessage());

		exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			teacherAssignmentBuilder.setCountGroupsTD(3);
		});
		Assertions.assertEquals(
				"The number of TD groups assigned must not exceed the number of TD groups of the course.",
				exception.getMessage());
	}

	@Test
	void testSettingTeacherWithNullTeacher() {
		Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
			TeacherAssignment.Builder.newInstance(course, null);
		});
		Assertions.assertEquals("The teacher assigned must not be null.", exception.getMessage());
	}
}