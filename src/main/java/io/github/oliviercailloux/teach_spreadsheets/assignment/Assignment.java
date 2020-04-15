package io.github.oliviercailloux.teach_spreadsheets.assignment;

import java.util.Set;
import java.util.Objects;
import java.util.LinkedHashSet;

import com.google.common.base.MoreObjects;

/**
 * This class gathers all the course assignments that were made in a set.
 *
 */
public class Assignment {
	private Set<CourseAssignment> listOfCourseAssignments; // We should not be able to consider more than once a
															// CourseAssignment.

	public static Assignment newInstance() {
		return new Assignment();
	}

	private Assignment() {
		listOfCourseAssignments = new LinkedHashSet<>();
	}

	public Set<CourseAssignment> getListOfCourseAssignments() {
		return listOfCourseAssignments;
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
		listOfCourseAssignments.add(Objects.requireNonNull(courseAssignment, "The courseAssignment must not be null."));
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("List of CourseAssignments", listOfCourseAssignments).toString();
	}
}
