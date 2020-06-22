package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.oliviercailloux.teach_spreadsheets.base.*;

public class CourseAssignmentTests {

	private static Teacher teacher1 = Teacher.Builder.newInstance().setLastName("Doe").setFirstName("John").build();
	private static Teacher teacher2 = Teacher.Builder.newInstance().setLastName("Doe").setFirstName("Jane").build();
	private static Course course1 = Course.Builder.newInstance().setName("Java").setCountGroupsTD(2).setNbMinutesTD(900)
			.setSemester(1).setStudyLevel("DE1").setStudyYear(2016).build();
	private static Course course2 = Course.Builder.newInstance().setName("C").setCountGroupsTD(3).setNbMinutesTD(1000)
			.setSemester(2).setStudyLevel("DE1").setStudyYear(2016).build();

	
	private static TeacherAssignment teacherAssignment1 = TeacherAssignment.Builder.newInstance(course1, teacher1)
			.setCountGroupsTD(1).build();
	private static TeacherAssignment teacherAssignment2 = TeacherAssignment.Builder.newInstance(course2, teacher2)
			.setCountGroupsTD(1).build();
	private static TeacherAssignment teacherAssignment3 = TeacherAssignment.Builder.newInstance(course2, teacher1)
			.setCountGroupsTD(2).build();
	
	@Test
	void testSetCourseWithNullCourse() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
			CourseAssignment.Builder.newInstance(null);
		});
		assertEquals("The course must not be null.", exception.getMessage());
	}

	@Test
	void testAddTeacherAssignmentWithExceedingCountGroupsTD() {
		TeacherAssignment teacherAssignment1 = TeacherAssignment.Builder.newInstance(course1, teacher1)
				.setCountGroupsTD(2).build();
		TeacherAssignment teacherAssignment2 = TeacherAssignment.Builder.newInstance(course1, teacher2)
				.setCountGroupsTD(2).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder.addTeacherAssignment(teacherAssignment1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			courseAssignmentBuilder.addTeacherAssignment(teacherAssignment2);
		});
		assertEquals(
				"The number of assigned TD groups must not exceed the number of TD groups associated to the course.",
				exception.getMessage());
	}

	@Test
	void testAddTeacherAssignmentWithZeroGroupsAssigned() {
		TeacherAssignment teacherAssignment0 = TeacherAssignment.Builder.newInstance(course1, teacher1).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance(course1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			courseAssignmentBuilder.addTeacherAssignment(teacherAssignment0);
		});
		assertEquals("An assignment must have at least one assigned group.", exception.getMessage());
	}

	@Test
	void testNewInstanceWithTwoParametersWithNoTeacherAssignment() {
		Throwable exception = assertThrows(IllegalStateException.class, () -> {
			CourseAssignment.newInstance(course1, new LinkedHashSet<TeacherAssignment>());
		});
		assertEquals("The course assignment must contain at least one teacher assignment.", exception.getMessage());
	}
	
	private static ImmutableSet<CourseAssignment> buildCourseAssignments() {
		CourseAssignment.Builder courseAssignmentBuilder1 = CourseAssignment.Builder.newInstance(course1);
		courseAssignmentBuilder1.addTeacherAssignment(teacherAssignment1);
		CourseAssignment courseAssignment1 = courseAssignmentBuilder1.build();

		CourseAssignment.Builder courseAssignmentBuilder2 = CourseAssignment.Builder.newInstance(course2);
		courseAssignmentBuilder2.addTeacherAssignment(teacherAssignment2);
		courseAssignmentBuilder2.addTeacherAssignment(teacherAssignment3);
		CourseAssignment courseAssignment2 = courseAssignmentBuilder2.build();

		Set<CourseAssignment> courseAssignments = new LinkedHashSet<>();
		courseAssignments.add(courseAssignment1);
		courseAssignments.add(courseAssignment2);

		return ImmutableSet.copyOf(courseAssignments);
	}
	
	@Test
	void testGetTeachersAssignments() {
		
		Set<CourseAssignment> courseAssignments = buildCourseAssignments();
		
		assertEquals(ImmutableSet.of(teacherAssignment1, teacherAssignment3).toString(),
				CourseAssignment.getTeacherAssignments(teacher1, courseAssignments).toString());
	}
	
	@Test
	void testTeacherAssignmentsToCourseAssignments() {
		ImmutableSet<CourseAssignment> expected = buildCourseAssignments();

		Set<TeacherAssignment> teacherAssignments = new LinkedHashSet<>();
		teacherAssignments.add(teacherAssignment1);
		teacherAssignments.add(teacherAssignment2);
		teacherAssignments.add(teacherAssignment3);
		ImmutableSet<CourseAssignment> actual = CourseAssignment
				.teacherAssignmentsToCourseAssignments(teacherAssignments);

		assertEquals(expected.toString(), actual.toString());
	}
}