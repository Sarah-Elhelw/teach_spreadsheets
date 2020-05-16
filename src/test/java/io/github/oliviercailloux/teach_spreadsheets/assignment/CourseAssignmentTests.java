package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.oliviercailloux.teach_spreadsheets.base.*;

public class CourseAssignmentTests {

	private static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").setFirstName("John").build();
	private static Course course = Course.Builder.newInstance().setName("Java").setCountGroupsTD(2).setnbMinutesTD(900)
			.setSemester(1).setStudyLevel("DE1").setStudyYear("2016").build();

	@Test
	void testSetCourseWithNullCourse() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
			CourseAssignment.Builder.newInstance(null);
		});
		assertEquals("The course must not be null.", exception.getMessage());
	}

	@Test
	void testAddTeacherAssignmentWithExceedingCountGroupsTD() {
		TeacherAssignment teacherAssignment = TeacherAssignment.Builder.newInstance(teacher).setCountGroupsTD(3)
				.build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			courseAssignmentBuilder.addTeacherAssignment(teacherAssignment);
		});
		assertEquals(
				"The number of assigned TD groups must not exceed the number of TD groups associated to the course.",
				exception.getMessage());
	}

	@Test
	void testAddTeacherAssignmentWithZeroGroupsAssigned() {
		TeacherAssignment teacherAssignment0 = TeacherAssignment.Builder.newInstance(teacher).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			courseAssignmentBuilder.addTeacherAssignment(teacherAssignment0);
		});
		assertEquals("An assignment must have at least one assigned group.", exception.getMessage());
	}
	
	@Test
	void testNewInstanceWithTwoParametersWithNoTeacherAssignment() {
		Throwable exception = assertThrows(IllegalStateException.class, () -> {
			CourseAssignment.newInstance(course, new LinkedHashSet<TeacherAssignment>());
		});
		assertEquals("The course assignment must contain at least one teacher assignment.", exception.getMessage());
	}
}
