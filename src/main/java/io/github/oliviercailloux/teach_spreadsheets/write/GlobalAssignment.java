package io.github.oliviercailloux.teach_spreadsheets.write;

import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.util.HSSFColor;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

public class GlobalAssignment {

	// This class generates and ods like FichierAgrege.pdf given

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
		// HSSFCellStyle cellStyle = null;

		// TITLE_POSITION.setBold(true);

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

	public static SpreadsheetDocument createGlobalAssignment(ImmutableSet<Course> allCourses,
			ImmutableSet<CoursePref> prefs, ImmutableSet<CourseAssignment> allCoursesAssigned) throws Throwable {

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		Table summary = document.appendSheet("Summary");
		headersToOds(summary);
		int line = 3;
		boolean courseHasTeacher = false; // this test helps us to see when there if they are teachers who want to teach
											// the course or not

		for (Course c : allCourses) {

			OdsHelper.setValueAt(summary, c.getStudyYear(), line, 0);
			OdsHelper.setValueAt(summary, String.valueOf(c.getSemester()), line, 1);
			OdsHelper.setValueAt(summary, c.getName(), line, 2);
			line++;
			Set<TeacherAssignment> teachersAssigned;

			for (CourseAssignment ca : allCoursesAssigned) {
				if (c.getName().equals(ca.getCourse().getName())) {
					teachersAssigned = ca.getTeacherAssignments();

					if (c.getCountGroupsCM() > 0) {

						line++;
						courseHasTeacher = false;
						OdsHelper.setValueAt(summary, "CM", line, 2);
						OdsHelper.setValueAt(summary, String.valueOf(c.getCountGroupsCM()), line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(c.getNbMinutesCM()), line, 4);

						for (CoursePref p : prefs) {

							if (c.getName().equals(p.getCourse().getName())) {
								courseHasTeacher = true;
								OdsHelper.setValueAt(summary, p.getTeacher().getFirstName(), line, 5);
								OdsHelper.setValueAt(summary, p.getTeacher().getLastName(), line, 6);

								if (!p.getPrefCM().toString().equals("UNSPECIFIED")) {
									OdsHelper.setValueAt(summary, p.getPrefCM().toString(), line, 7);
								}

								for (TeacherAssignment ta : teachersAssigned) {
									if (p.getTeacher().getFirstName().equals(ta.getTeacher().getFirstName())
											&& p.getTeacher().getLastName().equals(ta.getTeacher().getLastName())
											&& ta.getCountGroupsCM() != 0) { // if is the same Teacher
										OdsHelper.setValueAt(summary, ta.getTeacher().getFirstName(), line, 8);
										OdsHelper.setValueAt(summary, ta.getTeacher().getLastName(), line, 9);

										// ta.setFillForegroundColor(HSSFColor.GREEN.index);

									}
								}

								line++;
							}

						}

						if (!courseHasTeacher) {
							line++;
						}

					}

					if (c.getCountGroupsCMTD() > 0) {

						line++;
						courseHasTeacher = false;
						OdsHelper.setValueAt(summary, "CMTD", line, 2);
						OdsHelper.setValueAt(summary, String.valueOf(c.getCountGroupsCMTD()), line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(c.getNbMinutesCMTD()), line, 4);

						for (CoursePref p : prefs) {

							if (c.getName().equals(p.getCourse().getName())) {
								courseHasTeacher = true;
								OdsHelper.setValueAt(summary, p.getTeacher().getFirstName(), line, 5);
								OdsHelper.setValueAt(summary, p.getTeacher().getLastName(), line, 6);

								if (!p.getPrefCMTD().toString().equals("UNSPECIFIED")) {
									OdsHelper.setValueAt(summary, p.getPrefCMTD().toString(), line, 7);
								}

								for (TeacherAssignment ta : teachersAssigned) {
									if (p.getTeacher().getFirstName().equals(ta.getTeacher().getFirstName())
											&& p.getTeacher().getLastName().equals(ta.getTeacher().getLastName())
											&& ta.getCountGroupsCMTD() != 0) { // if is the same Teacher
										OdsHelper.setValueAt(summary, ta.getTeacher().getFirstName(), line, 8);
										OdsHelper.setValueAt(summary, ta.getTeacher().getLastName(), line, 9);

										// ta.setFillForegroundColor(HSSFColor.GREEN.index);
									}
								}

								line++;
							}
						}

						if (!courseHasTeacher) {
							line++;
						}

					}

					if (c.getCountGroupsCMTP() > 0) {

						line++;
						courseHasTeacher = false;
						OdsHelper.setValueAt(summary, "CMTP", line, 2);
						OdsHelper.setValueAt(summary, String.valueOf(c.getCountGroupsCMTP()), line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(c.getNbMinutesCMTP()), line, 4);

						for (CoursePref p : prefs) {

							if (c.getName().equals(p.getCourse().getName())) {
								courseHasTeacher = true;
								OdsHelper.setValueAt(summary, p.getTeacher().getFirstName(), line, 5);
								OdsHelper.setValueAt(summary, p.getTeacher().getLastName(), line, 6);

								if (!p.getPrefCMTP().toString().equals("UNSPECIFIED")) {
									OdsHelper.setValueAt(summary, p.getPrefCMTP().toString(), line, 7);
								}

								for (TeacherAssignment ta : teachersAssigned) {
									if (p.getTeacher().getFirstName().equals(ta.getTeacher().getFirstName())
											&& p.getTeacher().getLastName().equals(ta.getTeacher().getLastName())
											&& ta.getCountGroupsCMTP() != 0) { // if is the same Teacher
										OdsHelper.setValueAt(summary, ta.getTeacher().getFirstName(), line, 8);
										OdsHelper.setValueAt(summary, ta.getTeacher().getLastName(), line, 9);

										// ta.setFillForegroundColor(HSSFColor.GREEN.index);
									}
								}

								line++;
							}
						}

						if (!courseHasTeacher) {
							line++;
						}

					}

					if (c.getCountGroupsTD() > 0) {

						line++;
						courseHasTeacher = false;
						OdsHelper.setValueAt(summary, "TD", line, 2);
						OdsHelper.setValueAt(summary, String.valueOf(c.getCountGroupsTD()), line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(c.getNbMinutesTD()), line, 4);

						for (CoursePref p : prefs) {

							if (c.getName().equals(p.getCourse().getName())) {
								courseHasTeacher = true;
								OdsHelper.setValueAt(summary, p.getTeacher().getFirstName(), line, 5);
								OdsHelper.setValueAt(summary, p.getTeacher().getLastName(), line, 6);

								if (!p.getPrefTD().toString().equals("UNSPECIFIED")) {
									OdsHelper.setValueAt(summary, p.getPrefTD().toString(), line, 7);
								}

								for (TeacherAssignment ta : teachersAssigned) {
									if (p.getTeacher().getFirstName().equals(ta.getTeacher().getFirstName())
											&& p.getTeacher().getLastName().equals(ta.getTeacher().getLastName())
											&& ta.getCountGroupsTD() != 0) { // if is the same Teacher
										OdsHelper.setValueAt(summary, ta.getTeacher().getFirstName(), line, 8);
										OdsHelper.setValueAt(summary, ta.getTeacher().getLastName(), line, 9);

										// ta.setFillForegroundColor(HSSFColor.GREEN.index);
									}
								}

								line++;
							}
						}

						if (!courseHasTeacher) {
							line++;
						}

					}

					if (c.getCountGroupsTP() > 0) {

						line++;
						courseHasTeacher = false;
						OdsHelper.setValueAt(summary, "TP", line, 2);
						OdsHelper.setValueAt(summary, String.valueOf(c.getCountGroupsTP()), line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(c.getNbMinutesTP()), line, 4);

						for (CoursePref p : prefs) {

							if (c.getName().equals(p.getCourse().getName())) {
								courseHasTeacher = true;
								OdsHelper.setValueAt(summary, p.getTeacher().getFirstName(), line, 5);
								OdsHelper.setValueAt(summary, p.getTeacher().getLastName(), line, 6);

								if (!p.getPrefTP().toString().equals("UNSPECIFIED")) {
									OdsHelper.setValueAt(summary, p.getPrefTP().toString(), line, 7);
								}

								for (TeacherAssignment ta : teachersAssigned) {
									if (p.getTeacher().getFirstName().equals(ta.getTeacher().getFirstName())
											&& p.getTeacher().getLastName().equals(ta.getTeacher().getLastName())
											&& ta.getCountGroupsTP() != 0) { // if is the same Teacher
										OdsHelper.setValueAt(summary, ta.getTeacher().getFirstName(), line, 8);
										OdsHelper.setValueAt(summary, ta.getTeacher().getLastName(), line, 9);

										// ta.setFillForegroundColor(HSSFColor.GREEN.index);
									}
								}

								line++;
							}
						}

						if (!courseHasTeacher) {
							line++;
						}
					}
				}
			}
		}

		return document;

	}
}
