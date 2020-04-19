package io.github.oliviercailloux.teach_spreadsheets.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class AssignmentTests {

	@Test
	void testAddCourseAssignmentWithNullCourseAssignment() {
		Assignment assignment = Assignment.newInstance();
		Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
			assignment.addCourseAssignment(null);
		});
		Assertions.assertEquals("The courseAssignment must not be null.", exception.getMessage());
	}
}
