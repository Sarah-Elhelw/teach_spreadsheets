package io.github.oliviercailloux.teach_spreadsheets.write;

import java.util.Optional;
import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

public class GlobalAssignment {

	private static final ImmutableList<String> GROUPS = ImmutableList.of("CM", "CMTD", "CMTP", "TD", "TP");

	/**
	 * These Strings are the positions in the Summarized Ods of the Year, Semester,
	 * Course, Teacher's name and the various preferences and assignment.
	 */

	private final static String TITLE_POSITION = "A1";
	private final static String YEAR_POSITION = "A3";
	private final static String SEMESTER_POSITION = "B3";
	private final static String COURSE_TYPE_POSITION = "C3";
	private final static String GROUPS_NUMBER_POSITION = "D3";
	private final static String NUMBER_MINUTES_POSITION = "E3";
	private final static String CANDIDATES_FIRST_NAME_POSITION = "F3";
	private final static String CANDIDATES_LAST_NAME_POSITION = "G3";
	private final static String CHOICES_POSITION = "H3";
	private final static String ASSIGMENT_POSITION = "I3";

	/**
	 * This method adds the headers to this new document.
	 * 
	 * @param table This is the main table of the ods document that we want to
	 *              complete
	 */

	private static void headersToOds(Table table) {

		table.getCellByPosition(TITLE_POSITION).setStringValue("Teachers Preferences and Assigment");
		table.getCellByPosition(YEAR_POSITION).setStringValue("Year of study");
		table.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");
		table.getCellByPosition(COURSE_TYPE_POSITION).setStringValue("Course Type");
		table.getCellByPosition(GROUPS_NUMBER_POSITION).setStringValue("Numbers of groups");
		table.getCellByPosition(NUMBER_MINUTES_POSITION).setStringValue("Number of minutes weekly");
		table.getCellByPosition(CANDIDATES_FIRST_NAME_POSITION).setStringValue("Candidates' First Name");
		table.getCellByPosition(CANDIDATES_LAST_NAME_POSITION).setStringValue("Candidates' Last Name");
		table.getCellByPosition(CHOICES_POSITION).setStringValue("Choices");
		table.getCellByPosition(ASSIGMENT_POSITION).setStringValue("Assigment");

	}

	/**
	 * This method sets teachers' preferences and assignments for a given course
	 * group in an ods document.
	 * 
	 * @param ods              - the ods document to complete
	 * @param line             - the starting line in the ods document where to
	 *                         write
	 * @param course           - the given course
	 * @param prefs            - a set of teachers' preferences for the given
	 *                         course. There should be no duplicates of teachers'
	 *                         preferences.
	 * @param group            - the group of the given course
	 * @param teachersAssigned - the set of teachers assigned to the given course
	 * 
	 * @return line - the updated index of line
	 */
	private static int setSummarizedFileForGroup(OdsHelper ods, int line, Course course, Set<CoursePref> prefs,
			String group, Optional<Set<TeacherAssignment>> teachersAssigned) {

		boolean courseHasTeacher = false;

		if (course.getCountGroups(group) > 0) {

			line++;
			ods.setValueAt(group, line, 2);
			ods.setValueAt(String.valueOf(course.getCountGroups(group)), line, 3);
			ods.setValueAt(String.valueOf(course.getNbMinutes(group)), line, 4);

			for (CoursePref p : prefs) {

				if (course.equals(p.getCourse()) && !p.getPref(group).toString().equals("UNSPECIFIED")) {

					courseHasTeacher = true;
					ods.setValueAt(p.getTeacher().getFirstName(), line, 5);
					ods.setValueAt(p.getTeacher().getLastName(), line, 6);
					ods.setValueAt(p.getPref(group).toString(), line, 7);

					if (teachersAssigned.isPresent()) {
						for (TeacherAssignment ta : teachersAssigned.get()) {
							if (p.getTeacher().getFirstName().equals(ta.getTeacher().getFirstName())
									&& p.getTeacher().getLastName().equals(ta.getTeacher().getLastName())
									&& ta.getCountGroups(group) != 0) {
								ods.setValueAt(ta.getTeacher().getFirstName(), line, 8);
								ods.setValueAt(ta.getTeacher().getLastName(), line, 9);

							}
						}
					}

					line++;
				}
			}

			if (!courseHasTeacher) {
				line++;
			}

		}
		return line;
	}

	/**
	 * This method creates a summarized Ods like FichierAgrege.pdf. For each course,
	 * it writes all the teachers who want to teach the course, their preferences
	 * and the possible assignment.
	 * 
	 * @param allCourses         This is a complete set of Courses.
	 * @param prefs              This is a complete set of Preferences.
	 * @param allCoursesAssigned This is a complete set of CourseAssignment (courses
	 *                           which has been assigned to teachers)
	 * @return A document competed with all the courses, all the preferences of the
	 *         teachers and the possible assignment for each course
	 * @throws Throwable if the document could not be correctly completed
	 */

	public static SpreadsheetDocument createGlobalAssignment(Set<Course> allCourses, Set<CoursePref> prefs,
			Optional<Set<CourseAssignment>> allCoursesAssigned) throws Throwable {

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		Table summary = document.appendSheet("Summary");
		OdsHelper ods = OdsHelper.newInstance(summary);

		headersToOds(summary);
		int line = 3;

		for (Course course : allCourses) {

			ods.setValueAt(course.getStudyYear(), line, 0);
			ods.setValueAt(String.valueOf(course.getSemester()), line, 1);
			ods.setValueAt(course.getName(), line, 2);
			line++;

			Optional<Set<TeacherAssignment>> teachersAssigned = Optional.empty();

			if (allCoursesAssigned.isPresent()) {
				for (CourseAssignment ca : allCoursesAssigned.get()) {
					if (course.equals(ca.getCourse())) {
						teachersAssigned = Optional.of(ca.getTeacherAssignments());
						break;
					}
				}
			}

			for (String group : GROUPS) {
				line = setSummarizedFileForGroup(ods, line, course, prefs, group, teachersAssigned);
			}
		}

		return document;

	}
}
