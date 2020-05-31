package io.github.oliviercailloux.teach_spreadsheets.write;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.SupportedLinearMeasure;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

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

	private final static String NUMBER_HOURS_POSITION = "E16";

	/**
	 * This method formats the headers of the table in the sheet.
	 * 
	 * @param table - the sheet where the table is
	 * 
	 * @throws NullPointerException if the parameter is null
	 */
	private static void formatHeaders(Table table) {
		checkNotNull(table, "The sheet should not be null.");

		Set<String> headersPositions = Set.of(TEACHER_FIRST_NAME_POSITION, TEACHER_LAST_NAME_POSITION, STATUS_POSITION,
				OFFICE_POSITION, PERSONAL_EMAIL_POSITION, DAUPHINE_EMAIL_POSITION, PERSONAL_PHONE_POSITION,
				MOBILE_PHONE_POSITION, DAUPHINE_PHONE_NUMBER_POSITION);

		Set<String> valuesPositions = Set.of(TEACHER_FIRST_NAME_POSITION_VALUE, TEACHER_LAST_NAME_POSITION_VALUE,
				STATUS_POSITION_VALUE, OFFICE_POSITION_VALUE, PERSONAL_EMAIL_POSITION_VALUE,
				DAUPHINE_EMAIL_POSITION_VALUE, PERSONAL_PHONE_POSITION_VALUE, MOBILE_PHONE_POSITION_VALUE,
				DAUPHINE_PHONE_NUMBER_POSITION_VALUE);

		Set<String> titlesPositions = Set.of(TITLE_POSITION, YEAR_POSITION, SEMESTER_POSITION, COURSE_POSITION,
				TYPE_POSITION, NUMBER_HOURS_POSITION);

		for (String position : headersPositions) {
			table.getCellByPosition(position).setFont(new Font("Arial", FontStyle.BOLD, 9.0, Color.BLACK));
		}

		for (String position : valuesPositions) {
			table.getCellByPosition(position).setBorders(CellBordersType.ALL_FOUR,
					new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}

		for (String position : titlesPositions) {
			table.getCellByPosition(position).setFont(new Font("Arial", FontStyle.BOLD, 12.0, new Color(42, 96, 153)));
			if (!position.contentEquals(TITLE_POSITION)) {
				table.getCellByPosition(position).setHorizontalAlignment(HorizontalAlignmentType.CENTER);
			}
		}

	}

	/**
	 * This method adds the headers to this new document.
	 * 
	 * @param table   This is the main table of the ods document that we want to
	 *                complete
	 * @param teacher This is the Teacher for who we want to do the summarized Fiche
	 *                de service
	 * 
	 * @throws NullPointerException if a parameter is null
	 */
	private static void headersToOds(Table table, Teacher teacher) {
		checkNotNull(table, "The sheet should not be null.");
		checkNotNull(teacher, "The teacher should not be null.");

		table.getCellByPosition(TITLE_POSITION).setStringValue("Assignment per Teacher");

		table.getCellByPosition(TEACHER_FIRST_NAME_POSITION).setStringValue("FIRST NAME");
		table.getCellByPosition(TEACHER_FIRST_NAME_POSITION_VALUE).setStringValue(teacher.getFirstName());

		table.getCellByPosition(TEACHER_LAST_NAME_POSITION).setStringValue("LAST NAME");
		table.getCellByPosition(TEACHER_LAST_NAME_POSITION_VALUE).setStringValue(teacher.getLastName());

		table.getCellByPosition(STATUS_POSITION).setStringValue("STATUS");
		table.getCellByPosition(STATUS_POSITION_VALUE).setStringValue(teacher.getStatus());

		table.getCellByPosition(OFFICE_POSITION).setStringValue("OFFICE");
		table.getCellByPosition(OFFICE_POSITION_VALUE).setStringValue(teacher.getOffice());

		table.getCellByPosition(PERSONAL_EMAIL_POSITION).setStringValue("PERSONAL E-MAIL");
		table.getCellByPosition(PERSONAL_EMAIL_POSITION_VALUE).setStringValue(teacher.getPersonalEmail());

		table.getCellByPosition(DAUPHINE_EMAIL_POSITION).setStringValue("DAUPHINE E-MAIL");
		table.getCellByPosition(DAUPHINE_EMAIL_POSITION_VALUE).setStringValue(teacher.getDauphineEmail());

		table.getCellByPosition(PERSONAL_PHONE_POSITION).setStringValue("PERSONAL PHONE");
		table.getCellByPosition(PERSONAL_PHONE_POSITION_VALUE).setStringValue(teacher.getPersonalPhone());

		table.getCellByPosition(MOBILE_PHONE_POSITION).setStringValue("MOBILE PHONE");
		table.getCellByPosition(MOBILE_PHONE_POSITION_VALUE).setStringValue(teacher.getMobilePhone());

		table.getCellByPosition(DAUPHINE_PHONE_NUMBER_POSITION).setStringValue("DAUPHINE PHONE NUMBER");
		table.getCellByPosition(DAUPHINE_PHONE_NUMBER_POSITION_VALUE).setStringValue(teacher.getDauphinePhoneNumber());

		table.getCellByPosition(YEAR_POSITION).setStringValue("YEAR");

		table.getCellByPosition(SEMESTER_POSITION).setStringValue("SEMESTER");

		table.getCellByPosition(COURSE_POSITION).setStringValue("COURSE");

		table.getCellByPosition(TYPE_POSITION).setStringValue("TYPE");

		table.getCellByPosition(NUMBER_HOURS_POSITION).setStringValue("Nbr H");

		formatHeaders(table);
	}

	/**
	 * This method finds the teachers assignments where the teacher appears
	 * 
	 * @param teacher            The teacher we want to complete the Fichedeservice
	 * @param allCoursesAssigned A complete set of CourseAssignment
	 * 
	 * @return a set of assignments of the teacher
	 * 
	 * @throws NullPointerException if a parameter is null
	 */
	private static Set<TeacherAssignment> findTeacherAssignments(Teacher teacher,
			Set<CourseAssignment> allCoursesAssigned) {
		checkNotNull(teacher, "The teacher should not be null.");
		checkNotNull(allCoursesAssigned, "The set of courses assigned must not be null.");

		Set<TeacherAssignment> assignments = new LinkedHashSet<>();

		for (CourseAssignment ca : allCoursesAssigned) {
			for (TeacherAssignment ta : ca.getTeacherAssignments()) {
				if (teacher.equals(ta.getTeacher())) {
					assignments.add(ta);
				}
			}
		}

		return assignments;

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
	 * 
	 * @return the updated index of line
	 * 
	 * @throws NullPointerException if a parameter is null
	 */

	private static int completeCourses(OdsHelper ods, int line, TeacherAssignment ta, String group) {
		checkNotNull(ods, "The ods must not be null.");
		checkNotNull(ta, "The teacher assignment must not be null.");
		checkNotNull(group, "The group must not be null.");

		if (ta.getCountGroups(group) != 0) {
			ods.setValueAt(group, line, 3);
			ods.setValueAt(String.valueOf(ta.getCourse().getNbMinutes(group) / 60.0), line, 4);
			totalNumberMinutes += ta.getCourse().getNbMinutes(group) / 60.0;
			line++;
		}
		return line;
	}

	/**
	 * This method draws the main table in the file Fiche de service.
	 * 
	 * @param table - the sheet where the table is
	 * @param line  - the line where the table starts
	 * 
	 * @throws NullPointerException if the table is null
	 */
	private static void drawTable(Table table, int line) {
		checkNotNull(table, "The sheet should not be null.");

		for (int j = 0; j <= 4; j++) {
			for (int i = 15; i < line; i++) {
				table.getCellByPosition(j, i).setBorders(CellBordersType.ALL_FOUR,
						new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
			}
		}
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
	 * 
	 * @throws IOException if the document could not be correctly completed
	 */

	public static SpreadsheetDocument createAssignmentPerTeacher(Teacher teacher,
			Set<CourseAssignment> allCoursesAssigned) throws IOException {

		checkNotNull(teacher, "The teacher must not be null.");
		checkNotNull(allCoursesAssigned, "The set of courses assigned must not be null.");

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		Table summary = document.appendSheet("Summary");
		OdsHelper ods = OdsHelper.newInstance(summary);

		headersToOds(summary, teacher);
		int line = 16;
		totalNumberMinutes = 0;

		Set<TeacherAssignment> assignments = findTeacherAssignments(teacher, allCoursesAssigned);

		for (TeacherAssignment ta : assignments) {

			ods.setValueAt(ta.getCourse().getStudyYear(), line, 0);
			ods.setValueAt(String.valueOf(ta.getCourse().getSemester()), line, 1);
			ods.setValueAt(ta.getCourse().getName(), line, 2);

			for (String group : GROUPS) {
				line = completeCourses(ods, line, ta, group);
			}
		}

		drawTable(summary, line);

		line += 3;

		ods.setValueAt("TOTAL", line, 3);
		summary.getCellByPosition(3, line).setFont(new Font("Arial", FontStyle.BOLD, 12.0, new Color(42, 96, 153)));

		ods.setValueAt(String.valueOf(totalNumberMinutes), line, 4);
		summary.getCellByPosition(4, line).setBorders(CellBordersType.ALL_FOUR,
				new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));

		totalNumberMinutes = 0;

		return document;
	}

}
