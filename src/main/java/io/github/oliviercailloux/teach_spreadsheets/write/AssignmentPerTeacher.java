package io.github.oliviercailloux.teach_spreadsheets.write;

import java.util.LinkedHashSet;
import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

public class AssignmentPerTeacher {

	private static final ImmutableList<String> GROUPS = ImmutableList.of("CM", "CMTD", "CMTP", "TD", "TP");
	static int totalNumberMinutes = 0;

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
	 * This method finds the courses assigned to the teacher
	 * 
	 * @param teacher            The teacher we want to complete the Fichedeservice
	 * @param allCoursesAssigned A complete set of CourseAssignment
	 * @return a set of Courses assigned to the teacher
	 */
	private static Set<Course> findCoursesAssigned(Teacher teacher, Set<CourseAssignment> allCoursesAssigned) {

		Set<Course> coursesAssigned = new LinkedHashSet<>();
		Set<TeacherAssignment> teachersAssigned;

		for (CourseAssignment ca : allCoursesAssigned) {

			teachersAssigned = ca.getTeacherAssignments();
			for (TeacherAssignment ta : teachersAssigned) {

				if (teacher.getFirstName().equals(ta.getTeacher().getFirstName())
						&& teacher.getLastName().equals(ta.getTeacher().getLastName())) {
					coursesAssigned.add(ca.getCourse());

				}
			}
		}

		return coursesAssigned;

	}

	/**
	 * This method writes in the calc the courses Assigned to a teacher
	 * 
	 * @param ods            the ods document to complete
	 * @param line           the starting line in the ods document where to write
	 * @param ta             a TeacherAssignment in order to get the number of
	 *                       groups assigned to a specific teacher
	 * @param courseAssigned a Course assigned to the Teacher
	 * @param group          the group type of the given course
	 * @return the updated index of line
	 */

	private static int completeCourses(OdsHelper ods, int line, TeacherAssignment ta, Course courseAssigned,
			String group) {

		if (ta.getCountGroups(group) != 0) {
			ods.setValueAt(group, line, 3);
			ods.setValueAt(String.valueOf(courseAssigned.getNbMinutes(group)), line, 4);
			totalNumberMinutes += courseAssigned.getNbMinutes(group);
			line++;
		}
		return line;
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
			Set<CourseAssignment> allCoursesAssigned) throws Throwable {

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		Table summary = document.appendSheet("Summary");
		OdsHelper ods = OdsHelper.newInstance(summary);

		headersToOds(summary, teacher);
		int line = 16;
		totalNumberMinutes = 0;

		Set<TeacherAssignment> teachersAssigned;
		Course courseAssigned;

		Set<Course> coursesAssigned = findCoursesAssigned(teacher, allCoursesAssigned);

		for (CourseAssignment ca : allCoursesAssigned) {

			teachersAssigned = ca.getTeacherAssignments();

			for (TeacherAssignment ta : teachersAssigned) {

				if (teacher.getFirstName().equals(ta.getTeacher().getFirstName())
						&& teacher.getLastName().equals(ta.getTeacher().getLastName())) {
					courseAssigned = ca.getCourse();

					ods.setValueAt(courseAssigned.getStudyYear(), line, 0);
					ods.setValueAt(String.valueOf(courseAssigned.getSemester()), line, 1);
					ods.setValueAt(courseAssigned.getName(), line, 2);

					for (String group : GROUPS) {
						line = completeCourses(ods, line, ta, courseAssigned, group);
					}
				}
			}
		}

		line += 3;
		ods.setValueAt("TOTAL", line, 3);
		ods.setValueAt(String.valueOf(totalNumberMinutes), line, 4);
		
		document.save("target//AssignmentPerTeacher.ods");

		return document;
	}

}
