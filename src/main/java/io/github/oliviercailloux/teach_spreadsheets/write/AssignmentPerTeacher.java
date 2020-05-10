package io.github.oliviercailloux.teach_spreadsheets.write;

import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

public class AssignmentPerTeacher {


	/**
	 * These Strings are the positions in the Summarized Ods of the Teachers'
	 * personal information and the document's headers
	 */

	private final static String TITLE_POSITION = "A1";

	private final static String TEACHER_FIRST_NAME_POSITION = "A3";
	private final static String TEACHER_FIRST_NAME_POSITION_VALUE = "A4";

	private final static String TEACHER_LAST_NAME_POSITION = "C3";
	private final static String TEACHER_LAST_NAME_POSITION_VALUE = "C4";

	private final static String STATUS_POSITION = "A6";
	private final static String STATUS_POSITION_VALUE = "B6";

	private final static String OFFICE_POSITION = "C6";
	private final static String OFFICE_POSITION_VALUE = "D6";

	private final static String PERSONAL_EMAIL_POSITION = "A8";
	private final static String PERSONAL_EMAIL_POSITION_VALUE = "C8";

	private final static String DAUPHINE_EMAIL_POSITION = "A10";
	private final static String DAUPHINE_EMAIL_POSITION_VALUE = "C10";

	private final static String PERSONAL_PHONE_POSITION = "A12";
	private final static String PERSONAL_PHONE_POSITION_VALUE = "A13";

	private final static String MOBILE_PHONE_POSITION = "C12";
	private final static String MOBILE_PHONE_POSITION_VALUE = "C13";

	private final static String DAUPHINE_PHONE_NUMBER_POSITION = "E12";
	private final static String DAUPHINE_PHONE_NUMBER_POSITION_VALUE = "E13";

	private final static String YEAR_POSITION = "A16";

	private final static String SEMESTER_POSITION = "B16";

	private final static String COURSE_POSITION = "C16";

	private final static String TYPE_POSITION = "D16";

	private final static String NUMBER_MINUTES_POSITION = "E16";

	/**
	 * This method adds the headers to this new document.
	 * 
	 * @param table   This is the main table of the ods document that we want to
	 *                complete
	 * @param teacher This is the Teacher for who we want to do the summarized Fiche
	 *                de service
	 */

	private static void headersToOds(Table table, Teacher teacher) {

		table.getCellByPosition(TITLE_POSITION).setStringValue("Assignment per Teacher");

		table.getCellByPosition(TEACHER_FIRST_NAME_POSITION).setStringValue("First Name");
		table.getCellByPosition(TEACHER_FIRST_NAME_POSITION_VALUE).setStringValue(teacher.getFirstName());

		table.getCellByPosition(TEACHER_LAST_NAME_POSITION).setStringValue("Last name");
		table.getCellByPosition(TEACHER_LAST_NAME_POSITION_VALUE).setStringValue(teacher.getLastName());

		table.getCellByPosition(STATUS_POSITION).setStringValue("Status");
		table.getCellByPosition(STATUS_POSITION_VALUE).setStringValue(teacher.getStatus());

		table.getCellByPosition(OFFICE_POSITION).setStringValue("Office");
		table.getCellByPosition(OFFICE_POSITION_VALUE).setStringValue(teacher.getOffice());

		table.getCellByPosition(PERSONAL_EMAIL_POSITION).setStringValue("Personal E-mail");
		table.getCellByPosition(PERSONAL_EMAIL_POSITION_VALUE).setStringValue(teacher.getPersonalEmail());

		table.getCellByPosition(DAUPHINE_EMAIL_POSITION).setStringValue("Dauphine E-mail");
		table.getCellByPosition(DAUPHINE_EMAIL_POSITION_VALUE).setStringValue(teacher.getDauphineEmail());

		table.getCellByPosition(PERSONAL_PHONE_POSITION).setStringValue("Personal Phone");
		table.getCellByPosition(PERSONAL_PHONE_POSITION_VALUE).setStringValue(teacher.getPersonalPhone());

		table.getCellByPosition(MOBILE_PHONE_POSITION).setStringValue("Mobile Phone");
		table.getCellByPosition(MOBILE_PHONE_POSITION_VALUE).setStringValue(teacher.getMobilePhone());

		table.getCellByPosition(DAUPHINE_PHONE_NUMBER_POSITION).setStringValue("Dauphine Phone Number");
		table.getCellByPosition(DAUPHINE_PHONE_NUMBER_POSITION_VALUE).setStringValue(teacher.getDauphinePhoneNumber());

		table.getCellByPosition(YEAR_POSITION).setStringValue("Year of study");

		table.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");

		table.getCellByPosition(COURSE_POSITION).setStringValue("Course");

		table.getCellByPosition(TYPE_POSITION).setStringValue("Type");

		table.getCellByPosition(NUMBER_MINUTES_POSITION).setStringValue("Number of minutes");
	}

	/**
	 * This method creates a summarized Ods like Fiche de service.png. For each
	 * teacher, it writes all the courses he/she will teach.
	 * 
	 * @param teacher            This is the teacher for who we want to do the
	 *                           summarized Fiche de service
	 * @param allCoursesAssigned This is a complete set of CourseAssignment (courses
	 *                           which has been assigned to teachers). It will help
	 *                           us to know to which classes the teacher has been
	 *                           assigned
	 * @return A document completed with all the courses a specific teacher will
	 *         teach
	 * @throws Throwable if the document could not be correctly completed
	 */

	public static SpreadsheetDocument createAssignmentPerTeacher(Teacher teacher,
			ImmutableSet<CourseAssignment> allCoursesAssigned) throws Throwable {

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		Table summary = document.appendSheet("Summary");
		headersToOds(summary, teacher);
		int line = 16;
		int totalNumberMinutes = 0;

		Set<TeacherAssignment> teachersAssigned;
		Course courseAssigned;

		for (CourseAssignment ca : allCoursesAssigned) {

			teachersAssigned = ca.getTeacherAssignments();

			for (TeacherAssignment ta : teachersAssigned) {

				if (teacher.getFirstName().equals(ta.getTeacher().getFirstName())
						&& teacher.getLastName().equals(ta.getTeacher().getLastName())) {
					courseAssigned = ca.getCourse();

					OdsHelper.setValueAt(summary, courseAssigned.getStudyYear(), line, 0);
					OdsHelper.setValueAt(summary, String.valueOf(courseAssigned.getSemester()), line, 1);
					OdsHelper.setValueAt(summary, courseAssigned.getName(), line, 2);

					if (ta.getCountGroupsCM() != 0) {
						OdsHelper.setValueAt(summary, "CM", line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(courseAssigned.getNbMinutesCM()), line, 4);
						totalNumberMinutes += courseAssigned.getNbMinutesCM();
						line++;
					}

					if (ta.getCountGroupsCMTD() != 0) {
						OdsHelper.setValueAt(summary, "CMTD", line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(courseAssigned.getNbMinutesCMTD()), line, 4);
						totalNumberMinutes += courseAssigned.getNbMinutesCMTD();
						line++;
					}

					if (ta.getCountGroupsCMTP() != 0) {
						OdsHelper.setValueAt(summary, "CMTP", line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(courseAssigned.getNbMinutesCMTP()), line, 4);
						totalNumberMinutes += courseAssigned.getNbMinutesCMTP();
						line++;
					}

					if (ta.getCountGroupsTD() != 0) {
						OdsHelper.setValueAt(summary, "TD", line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(courseAssigned.getNbMinutesTD()), line, 4);
						totalNumberMinutes += courseAssigned.getNbMinutesTD();
						line++;
					}

					if (ta.getCountGroupsTP() != 0) {
						OdsHelper.setValueAt(summary, "TP", line, 3);
						OdsHelper.setValueAt(summary, String.valueOf(courseAssigned.getNbMinutesTP()), line, 4);
						totalNumberMinutes += courseAssigned.getNbMinutesTP();
						line++;
					}
				}
			}
		}

		line += 3;
		OdsHelper.setValueAt(summary, "TOTAL", line, 3);
		OdsHelper.setValueAt(summary, String.valueOf(totalNumberMinutes), line, 4);

		return document;
	}

}
