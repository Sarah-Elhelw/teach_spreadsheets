package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class TeacherAssignmentTests {
	
	
	@Test
	void testBuilderWithNegativeCountGroupsTD() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance();
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   teacherAssignmentBuilder.setCountGroupsTD(-1);
	           }
           });
		Assertions.assertEquals("The number of TD groups must be positive.", exception.getMessage());
	}
	
	@Test
	void testBuilderWithNoTeacher() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance();
		teacherAssignmentBuilder.setCountGroupsTD(2);
		Throwable exception = Assertions.assertThrows(NullPointerException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   teacherAssignmentBuilder.build();
	           }
        });
		Assertions.assertEquals("You cannot build a TeacherAssignment without a Teacher.", exception.getMessage());
	}
	
	@Test
	void testSetTeacherWithNullTeacher() {
		TeacherAssignment.Builder teacherAssignmentBuilder = TeacherAssignment.Builder.newInstance();
		Throwable exception = Assertions.assertThrows(NullPointerException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   teacherAssignmentBuilder.setTeacher(null);
	           }
        });
		Assertions.assertEquals("The teacher assigned must not be null.", exception.getMessage());
	}
}
