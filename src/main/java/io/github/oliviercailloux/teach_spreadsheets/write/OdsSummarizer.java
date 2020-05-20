package io.github.oliviercailloux.teach_spreadsheets.write;

import java.util.LinkedHashSet;
import java.util.Optional;
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
import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;

public class OdsSummarizer {

	private static final ImmutableList<String> GROUPS = ImmutableList.of("CM", "CMTD", "CMTP", "TD", "TP");

	private ImmutableSet<Course> allCourses;
	private Set<CoursePref> prefs;
	private Optional<Set<CourseAssignment>> allCoursesAssigned;
	
	private int rows;
	
	private OdsSummarizer(Set<Course> allCourses) {
		this.allCourses = ImmutableSet.copyOf(allCourses);
		prefs = new LinkedHashSet<CoursePref>();
		allCoursesAssigned = Optional.empty();
	}
	
	public static OdsSummarizer newInstance(Set<Course> allCourses) {
		return new OdsSummarizer(allCourses);
	}
	

	/**
	 * These Strings are the positions in the Summarized Ods of the Year, Semester,
	 * Course, Teacher's name and the various preferences and assignment.
	 */

	private final static String TITLE_POSITION = "A1";
	private final static String YEAR_POSITION = "A3";
	private final static String SEMESTER_POSITION = "B3";
	private final static String COURSE_TYPE_POSITION = "C3";
	private final static String GROUPS_NUMBER_POSITION = "D3";
	private final static String NUMBER_HOURS_POSITION = "E3";
	private final static String CANDIDATES_FIRST_NAME_POSITION = "F3";
	private final static String CANDIDATES_LAST_NAME_POSITION = "G3";
	private final static String CHOICES_POSITION = "H3";
	private final static String ASSIGNMENT_POSITION = "I3";
	
	/**
	 * This method sets the Teachers' preferences for the courses : it adds new
	 * teacher's preferences for a course.
	 * 
	 * @param prefsToBeSet These are all the courses preferences to be written in
	 *                     the FichierAgrege
	 * 
	 * @throws NullPointerException     if the parameter is null
	 * @throws IllegalArgumentException if we want to add a teacher's preference for
	 *                                  a course that is not in allCourses or if we
	 *                                  want to add a teacher's preferences for a
	 *                                  course while their is already one in prefs.
	 */

	public void setPrefs(Set<CoursePref> prefsToBeSet) {
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
	 * This method sets all the assignments for the courses. According to the way
	 * teach_spreadsheets project will work, a set of all the courses' assignments
	 * will be provided to create the summarized assignment ods file. In this class,
	 * the assignments are set as optional in the case only the teachers'
	 * preferences are wanted : an empty optional means there are no assignment
	 * informations to display.
	 * 
	 * @param assignmentsToBeSet These are all the courses Assignment to be written
	 *                           in the FichierAgrege
	 * 
	 * @throws NullPointerException if the parameter is null
	 */

	public void setAllCoursesAssigned(Set<CourseAssignment> assignmentsToBeSet) {
		checkNotNull(assignmentsToBeSet, "The course assignments should not be null.");

		Set<Course> coursesInAssignments = new LinkedHashSet<>();
		for (CourseAssignment courseAssignment : assignmentsToBeSet) {
			coursesInAssignments.add(courseAssignment.getCourse());
		}
		checkArgument(allCourses.containsAll(coursesInAssignments),
				"The assignments must be for courses specified in allCourses attribute.");
		
		allCoursesAssigned = Optional.of(assignmentsToBeSet);
	}

	private void putBorders(Table table) {
		checkNotNull(table);
		for(int col = 0; col <= 9; col++) {
			for(int row = 3; row <= rows; row++) {
				if (!(new Font("Arial", FontStyle.BOLD, 11.0, Color.BLACK).equals(table.getCellByPosition(2, row).getFont()))) {
					table.getCellByPosition(col, row).setBorders(CellBordersType.LEFT_RIGHT, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
				}
				table.getCellByPosition(col, row).setBorders(CellBordersType.LEFT_RIGHT, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
			}
			table.getCellByPosition(col, rows).setBorders(CellBordersType.BOTTOM, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
	}
	
	private static void formatHeaders(Table table) {
		table.getCellByPosition(TITLE_POSITION).setFont(new Font("Arial", FontStyle.BOLD, 15.0, new Color(201, 33, 30)));
		Set<String> headersPositions = Set.of(YEAR_POSITION, SEMESTER_POSITION, COURSE_TYPE_POSITION, GROUPS_NUMBER_POSITION, NUMBER_HOURS_POSITION, CANDIDATES_FIRST_NAME_POSITION, CANDIDATES_LAST_NAME_POSITION, CHOICES_POSITION, ASSIGNMENT_POSITION);
		for (String position : headersPositions) {
			table.getCellByPosition(position).setCellBackgroundColor(new Color(255, 182, 108));
			table.getCellByPosition(position).setFont(new Font("Arial", FontStyle.BOLD, 12.0, Color.BLACK));
			table.getCellByPosition(position).setBorders(CellBordersType.ALL_FOUR, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
		table.getCellByPosition("J3").setCellBackgroundColor(new Color(255, 182, 108));
		table.getCellByPosition("J3").setBorders(CellBordersType.ALL_FOUR, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		table.getCellByPosition(ASSIGNMENT_POSITION).setBorders(CellBordersType.TOP_BOTTOM, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
	}
	
	/**
	 * This method adds the headers to this new document.
	 * 
	 * @param table - this is the main table of the ods document to be completed
	 */

	private static void headersToOds(Table table) {

		table.getCellByPosition(TITLE_POSITION).setStringValue("Teachers Preferences and Assignment");
		table.getCellByPosition(YEAR_POSITION).setStringValue("Year of study");
		table.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");
		table.getCellByPosition(COURSE_TYPE_POSITION).setStringValue("Course Type");
		table.getCellByPosition(GROUPS_NUMBER_POSITION).setStringValue("Numbers of groups");
		table.getCellByPosition(NUMBER_HOURS_POSITION).setStringValue("Number of hours weekly");
		table.getCellByPosition(CANDIDATES_FIRST_NAME_POSITION).setStringValue("Candidates' First Name");
		table.getCellByPosition(CANDIDATES_LAST_NAME_POSITION).setStringValue("Candidates' Last Name");
		table.getCellByPosition(CHOICES_POSITION).setStringValue("Choices");
		table.getCellByPosition(ASSIGNMENT_POSITION).setStringValue("Assignment");
		
		formatHeaders(table);
	}

	private void formatGroups(Table table, int line) {
		table.getCellByPosition(2, line).setHorizontalAlignment(HorizontalAlignmentType.RIGHT);
		table.getCellByPosition(3, line).setHorizontalAlignment(HorizontalAlignmentType.CENTER);
		for (int col = 2; col <=9; col++) {
			table.getCellByPosition(col, line).setBorders(CellBordersType.TOP, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
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
	 * @param group            - the group type of the given course
	 * @param teachersAssigned - the set of teachers assigned to the given course
	 * 
	 * @return line - the updated index of line
	 */
	private int setSummarizedFileForGroup(OdsHelper ods, Table table, int line, Course course, String group,
			Optional<Set<TeacherAssignment>> teachersAssigned) {

		boolean courseHasTeacher = false;

		if (course.getCountGroups(group) > 0) {

			line++;
			
			ods.setValueAt(group, line, 2);
			ods.setValueAt(String.valueOf(course.getCountGroups(group)), line, 3);
			ods.setValueAt(String.valueOf(course.getNbMinutes(group)/60), line, 4);

			formatGroups(table, line);

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
	
	public void formatCourseHeader(Table table, int line) {
		table.getCellByPosition(0, line).setBorders(CellBordersType.TOP, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		table.getCellByPosition(1, line).setBorders(CellBordersType.TOP, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		for (int col = 2; col<=9; col++) {
			table.getCellByPosition(col, line).setCellBackgroundColor(Color.SILVER);
			table.getCellByPosition(col, line).setBorders(CellBordersType.TOP_BOTTOM, new Border(Color.BLACK, 0.03, SupportedLinearMeasure.CM));
		}
		table.getCellByPosition(2, line).setFont(new Font("Arial", FontStyle.BOLD, 11.0, Color.BLACK));
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

	public SpreadsheetDocument createSummary() throws Throwable {

		SpreadsheetDocument document = OdsHelper.createAnEmptyOds();
		Table summary = document.appendSheet("Summary");
		OdsHelper ods = OdsHelper.newInstance(summary);

		headersToOds(summary);
		int line = 3;

		for (Course course : allCourses) {

			ods.setValueAt(course.getStudyYear(), line, 0);
			ods.setValueAt(String.valueOf(course.getSemester()), line, 1);
			ods.setValueAt(course.getName(), line, 2);
			
			formatCourseHeader(summary, line);
			
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

			line--;
			for (String group : GROUPS) {
				line = setSummarizedFileForGroup(ods, summary, line, course, group, teachersAssigned);
			}
		}
		
		rows = line;
		putBorders(summary);
		
		document.save("target//OdsSummarizer.ods");
		return document;

	}
}
