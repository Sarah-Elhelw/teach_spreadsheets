package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import io.github.oliviercailloux.teach_spreadsheets.base.*;

public class CourseAssignmentTests {
	
	private static Teacher teacher = Teacher.Builder.newInstance().setLastName("Doe").setFirstName("John").build();
	private static Course course = Course.Builder.newInstance().setName("Java").setCountGroupsTD(2).setNbHoursTD(15).build();
	
	@Test
	void testBuilderWithNoCourse() {
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance();
		Throwable exception = Assertions.assertThrows(NullPointerException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   courseAssignmentBuilder.build();
	           }
		});
		Assertions.assertEquals("You cannot build a CourseAssignment without a Course.", exception.getMessage());
	}
	
	@Test
	void testSetCourseWithNullCourse() {
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance();
		Throwable exception = Assertions.assertThrows(NullPointerException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   courseAssignmentBuilder.setCourse(null);
	           }
		});
		Assertions.assertEquals("The course must not be null.", exception.getMessage());
	}
	
	@Test
	void testAddTeacherAssignmentWithExceedingCountGroupsTD() {
		TeacherAssignment teacherAssignment = TeacherAssignment.Builder.newInstance().setTeacher(teacher).setCountGroupsTD(3).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance();
		courseAssignmentBuilder.setCourse(course);
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   courseAssignmentBuilder.addTeacherAssignment(teacherAssignment);
	           }
		});
		Assertions.assertEquals("The number of assigned TD groups must not exceed the number of TD groups associated to the course.", exception.getMessage());
	}
	
	@Test
	void testAddTeacherAssignmentWithZeroGroupsAssigned() {
		TeacherAssignment teacherAssignment0 = TeacherAssignment.Builder.newInstance().setTeacher(teacher).build();
		CourseAssignment.Builder courseAssignmentBuilder = CourseAssignment.Builder.newInstance();
		courseAssignmentBuilder.setCourse(course);
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   courseAssignmentBuilder.addTeacherAssignment(teacherAssignment0);
	           }
		});
		Assertions.assertEquals("An assignment must have at least one assigned group.", exception.getMessage());
	}
}
