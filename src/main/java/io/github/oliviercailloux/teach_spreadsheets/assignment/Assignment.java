package io.github.oliviercailloux.teach_spreadsheets.assignment;

import java.util.Set;
import java.util.LinkedHashSet;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * This class gathers all the course assignments that were made in a set.
 *
 */
public class Assignment {
	/**
	 * Set of {@link CourseAssignment} :  we should not be able to consider more than once a CourseAssignment.
	 */
	private Set<CourseAssignment> courseAssignments;

	public static Assignment newInstance() {
		return new Assignment();
	}

	private Assignment() {
		courseAssignments = new LinkedHashSet<>();
	}

	public Set<CourseAssignment> getListOfCourseAssignments() {
		return courseAssignments;
	}

	/**
	 * Adds a course assignment to the list of course assignments.
	 * 
	 * @param courseAssignment - the object representing a new course assignment to
	 *                         the list of course assignments.
	 * 
	 * @throws NullPointerException if the parameter is null
	 */
	public void addCourseAssignment(CourseAssignment courseAssignment) {
		courseAssignments.add(Preconditions.checkNotNull(courseAssignment, "The courseAssignment must not be null."));
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("List of CourseAssignments", courseAssignments).toString();
	}
}
