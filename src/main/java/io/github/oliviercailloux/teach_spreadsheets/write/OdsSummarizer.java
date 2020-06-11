package io.github.oliviercailloux.teach_spreadsheets.write;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.SupportedLinearMeasure;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import static com.google.common.base.Verify.verify;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.SubCourseKind;
import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

public class OdsSummarizer {

	/**
	 * These Strings are the positions in the Summarized Ods of the study level,
	 * semester, course information, teacher's name and the various preferences and
	 * assignments.
	 */
	private final static String TITLE_POSITION = "A1";
	private final static String STUDY_LEVEL_POSITION = "A3";
	private final static String SEMESTER_POSITION = "B3";
	private final static String COURSE_TYPE_POSITION = "C3";
	private final static String GROUPS_NUMBER_POSITION = "D3";
	private final static String NUMBER_HOURS_POSITION = "E3";
	private final static String CANDIDATES_FIRST_NAME_POSITION = "F3";
	private final static String CANDIDATES_LAST_NAME_POSITION = "G3";
	private final static String CHOICES_POSITION = "H3";
	private final static String ASSIGNMENT_POSITION = "I3";

	private OdsHelper ods;
	private int line;

	private ImmutableSet<Course> allCourses;
	private Set<CoursePref> prefs;
	private Set<CourseAssignment> allCoursesAssigned;

	private OdsSummarizer(Set<Course> allCourses) {
		this.allCourses = ImmutableSet.copyOf(allCourses);
		prefs = new LinkedHashSet<>();
		allCoursesAssigned = new LinkedHashSet<>();
	}

	public static OdsSummarizer newInstance(Set<Course> allCourses) {
		checkNotNull(allCourses, "The set of courses should not be null.");
		return new OdsSummarizer(allCourses);
	}

	/**
	 * This method adds new teacher's preferences for courses.
	 * 
	 * @param prefsToBeSet - These are the courses preferences to be written in the
	 *                     FichierAgrege
	 * 
	 * @throws IllegalArgumentException if we want to add a teacher's preference for
	 *                                  a course that is not in allCourses or if we
	 *                                  want to add a teacher's preferences for a
	 *                                  course while there is already one in prefs.
	 */
	public void addPrefs(Set<CoursePref> prefsToBeSet) {
		checkNotNull(prefsToBeSet, "The preferences must not be null.");

		Set<Course> coursesInPrefs = new LinkedHashSet<>();
		for (CoursePref coursePref : prefsToBeSet) {
			coursesInPrefs.add(coursePref.getCourse());
		}
		checkArgument(allCourses.containsAll(coursesInPrefs),
				"The preferences must be for courses specified in allCourses attribute.");

		for (CoursePref pref1 : prefs) {
			for (CoursePref pref2 : prefsToBeSet) {
				checkArgument(
						!(pref1.getTeacher().equals(pref2.getTeacher()) && pref1.getCourse().equals(pref2.getCourse())),
						"The preferences of a teacher for a course should be set once.");
			}
		}

		prefs.addAll(prefsToBeSet);
	}

	/**
	 * This method sets all the assignments for the courses.
	 * 
	 * @param assignmentsToBeSet - These are all the courses' assignments to be
	 *                           written in the FichierAgrege
	 * 
	 */
	public void setAllCoursesAssigned(Set<CourseAssignment> assignmentsToBeSet) {
		checkNotNull(assignmentsToBeSet, "The course assignments should not be null.");

		Set<Course> coursesInAssignments = assignmentsToBeSet.stream().map(CourseAssignment::getCourse)
				.collect(Collectors.toSet());

		checkArgument(allCourses.containsAll(coursesInAssignments),
				"The assignments must be for courses specified in allCourses attribute.");

		allCoursesAssigned = assignmentsToBeSet;
	}

	/**
	 * This methods sets the main borders of the unique table in the sheet.
	 * 
	 */
	private void putBorders() {
		Table table = ods.getTable();

		for (int col = 0; col <= 9; col++) {
			for (int row = 3; row <= line; row++) {
				if (!(new Font("Arial", FontStyle.BOLD, 11.0, Color.BLACK)
						.equals(table.getCellByPosition(2, row).getFont())) || col == 0 || col == 1) {
					table.getCellByPosition(col, row).setBorders(CellBordersType.LEFT_RIGHT,
							new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
				} else {
					if (col == 9) {
						table.getCellByPosition(col, row).setBorders(CellBordersType.RIGHT,
								new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
					}

				}
			}
			table.getCellByPosition(col, line).setBorders(CellBordersType.BOTTOM,
					new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
	}

	/**
	 * This method formats the headers of the table in the sheet.
	 * 
	 */
	private void formatHeaders() {
		Table table = ods.getTable();

		table.getCellByPosition(TITLE_POSITION)
				.setFont(new Font("Arial", FontStyle.BOLD, 15.0, new Color(201, 33, 30)));

		Set<String> headersPositions = Set.of(STUDY_LEVEL_POSITION, SEMESTER_POSITION, COURSE_TYPE_POSITION,
				GROUPS_NUMBER_POSITION, NUMBER_HOURS_POSITION, CANDIDATES_FIRST_NAME_POSITION,
				CANDIDATES_LAST_NAME_POSITION, CHOICES_POSITION, ASSIGNMENT_POSITION, "J3");

		for (String position : headersPositions) {
			table.getCellByPosition(position).setCellBackgroundColor(new Color(255, 182, 108));
			table.getCellByPosition(position).setFont(new Font("Arial", FontStyle.BOLD, 12.0, Color.BLACK));
			table.getCellByPosition(position).setBorders(CellBordersType.ALL_FOUR,
					new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
		table.getCellByPosition(ASSIGNMENT_POSITION).setBorders(CellBordersType.TOP_BOTTOM,
				new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
	}

	/**
	 * This method adds the headers to the sheet.
	 * 
	 */
	private void headersToOds() {
		Table table = ods.getTable();

		table.getCellByPosition(TITLE_POSITION).setStringValue("Teachers Preferences and Assignment");
		table.getCellByPosition(STUDY_LEVEL_POSITION).setStringValue("Study Level");
		table.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");
		table.getCellByPosition(COURSE_TYPE_POSITION).setStringValue("Course Type");
		table.getCellByPosition(GROUPS_NUMBER_POSITION).setStringValue("Number of groups");
		table.getCellByPosition(NUMBER_HOURS_POSITION).setStringValue("Number of hours");
		table.getCellByPosition(CANDIDATES_FIRST_NAME_POSITION).setStringValue("Candidate's First Name");
		table.getCellByPosition(CANDIDATES_LAST_NAME_POSITION).setStringValue("Candidate's Last Name");
		table.getCellByPosition(CHOICES_POSITION).setStringValue("Choice");
		table.getCellByPosition(ASSIGNMENT_POSITION).setStringValue("Assignment");

		formatHeaders();
	}

	/**
	 * This method formats the cells containing information about a course group.
	 * 
	 */
	private void formatGroups() {
		Table table = ods.getTable();

		table.getCellByPosition(2, line).setHorizontalAlignment(HorizontalAlignmentType.RIGHT);
		table.getCellByPosition(3, line).setHorizontalAlignment(HorizontalAlignmentType.CENTER);
		for (int col = 2; col <= 9; col++) {
			table.getCellByPosition(col, line).setBorders(CellBordersType.TOP,
					new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
	}

	/**
	 * This method sets teachers' preferences and assignments for a given course
	 * group in the sheet.
	 * 
	 * @param course           - the given course
	 * @param group            - the group type of the given course
	 * @param prefsForGroup    - the set of teachers' preferences for the given
	 *                         group
	 * @param teachersAssigned - the set of teachers assigned to the given course
	 * 
	 * 
	 */
	private void setSummarizedFileForGroup(Course course, SubCourseKind group, Set<CoursePref> prefsForGroup,
			Set<TeacherAssignment> teachersAssigned) {

		checkNotNull(course, "The course should not be null.");
		checkNotNull(group, "The group should not be null.");
		checkNotNull(prefsForGroup, "The set of preferences for the group should not be null.");
		checkNotNull(teachersAssigned, "The teachers' assignments should not be null.");

		boolean courseHasTeacher = false;

		line++;

		ods.setValueAt(group.toString(), line, 2);
		ods.setValueAt(String.valueOf(course.getCountGroups(group)), line, 3);
		ods.setValueAt(String.valueOf(course.getNbMinutes(group) / 60.0), line, 4);

		formatGroups();

		for (CoursePref p : prefsForGroup) {

			courseHasTeacher = true;

			ods.setValueAt(p.getTeacher().getFirstName(), line, 5);
			ods.setValueAt(p.getTeacher().getLastName(), line, 6);
			ods.setValueAt(p.getPref(group).toString(), line, 7);

			for (TeacherAssignment ta : teachersAssigned) {
				if (p.getTeacher().equals(ta.getTeacher()) && ta.getCountGroups(group) != 0) {
					ods.setValueAt(ta.getTeacher().getFirstName(), line, 8);
					ods.setValueAt(ta.getTeacher().getLastName(), line, 9);

				}
			}

			line++;
		}

		if (!courseHasTeacher) {
			line++;
		}

	}

	/**
	 * This method formats the cells containing information about a course.
	 * 
	 */
	private void formatCourseHeader() {
		Table table = ods.getTable();

		table.getCellByPosition(0, line).setBorders(CellBordersType.TOP,
				new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		table.getCellByPosition(1, line).setBorders(CellBordersType.TOP,
				new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		for (int col = 2; col <= 9; col++) {
			table.getCellByPosition(col, line).setCellBackgroundColor(Color.SILVER);
			table.getCellByPosition(col, line).setBorders(CellBordersType.TOP_BOTTOM,
					new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
		table.getCellByPosition(2, line).setFont(new Font("Arial", FontStyle.BOLD, 11.0, Color.BLACK));
	}

	/**
	 * This method creates a summarized Ods like FichierAgrege.pdf. For each course,
	 * it writes all the teachers who want to teach the course, their preferences
	 * and, possibly, their assignments.
	 * 
	 * @return A document completed with all the courses, all the preferences of the
	 *         teachers and the possible assignments for each course
	 * 
	 * @throws IOException if the Ods could not be created
	 */
	public SpreadsheetDocument createSummary() throws IOException {

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		ods = OdsHelper.newInstance(document.appendSheet("Summary"));
		verify(ods != null);

		headersToOds();

		line = 2;

		for (Course course : allCourses) {

			line++;

			ods.setValueAt(String.valueOf(course.getStudyLevel()), line, 0);
			ods.setValueAt(String.valueOf(course.getSemester()), line, 1);
			ods.setValueAt(course.getName(), line, 2);

			formatCourseHeader();

			Set<TeacherAssignment> teachersAssigned = new LinkedHashSet<>();

			for (CourseAssignment courseAssignment : allCoursesAssigned) {
				if (course.equals(courseAssignment.getCourse())) {
					teachersAssigned.addAll(courseAssignment.getTeacherAssignments());
				}
			}

			Set<CoursePref> prefsForGroup;
			for (SubCourseKind group : SubCourseKind.values()) {
				prefsForGroup = new LinkedHashSet<>();

				if (course.getCountGroups(group) > 0) {

					for (CoursePref p : prefs) {
						if (course.equals(p.getCourse())
								&& !p.getPref(group).toString().equals("UNSPECIFIED")) {
							prefsForGroup.add(p);
						}
					}

					setSummarizedFileForGroup(course, group, prefsForGroup, teachersAssigned);
				}

			}
		}

		putBorders();

		return document;

	}
}
