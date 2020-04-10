package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class AssignmentTests {

	@Test
	void testAddCourseAssignmentWithNullCourseAssignment() {
		Assignment assignment = Assignment.newInstance();
		Throwable exception = Assertions.assertThrows(NullPointerException.class, new Executable() {
	           @Override
	           public void execute() throws Throwable {
	        	   assignment.addCourseAssignment(null);
	           }
        });
		Assertions.assertEquals("The courseAssignment must not be null.", exception.getMessage());
	}
}
