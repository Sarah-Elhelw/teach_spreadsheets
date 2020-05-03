package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeachersPreferences {

	// This class generate and ods like FichierAgrege.pdf given but without the
	// Assignment column

	/**
	 * These Strings are the positions in the Summarized Ods of the Year, Semester,
	 * Course, Teacher's name and the various preferences.
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

	/**
	 * This method adds the headers to this new document.
	 * 
	 * @param table This is the main table of the ods document that we want to
	 *              complete
	 */

	private static void headersToOds(Table table) {

		table.getCellByPosition(TITLE_POSITION).setStringValue("Teachers' Preferences and Assigment");
		table.getCellByPosition(YEAR_POSITION).setStringValue("Year of study");
		table.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");
		table.getCellByPosition(COURSE_TYPE_POSITION).setStringValue("Course Type");
		table.getCellByPosition(GROUPS_NUMBER_POSITION).setStringValue("Numbers of groups");
		table.getCellByPosition(NUMBER_MINUTES_POSITION).setStringValue("Number of minutes weekly");
		table.getCellByPosition(CANDIDATES_FIRST_NAME_POSITION).setStringValue("Candidates' First Name");
		table.getCellByPosition(CANDIDATES_LAST_NAME_POSITION).setStringValue("Candidates' Last Name");
		table.getCellByPosition(CHOICES_POSITION).setStringValue("Choices");

		// TO DO : mettre les entetes en forme : en gras + le titre en plus grand et en
		// gras
		// Essayer de formater les cellules avec la largeur du texte
	}

	/*
	 * Je voulais utiliser cette methode pour éviter la duplication du code mais je
	 * pense que ça devient très compliqué à l'utiliser
	 * 
	 * private static int completePreferences(Table summary, int countGroups, int
	 * nbMinutes , int line, String courseName, ImmutableSet<CoursePref> prefs) {
	 * 
	 * if (countGroups >0) {
	 * 
	 * boolean courseHasTeacher = false; // this test helps us to see when there if
	 * they are teachers who want to // teach the course or not
	 * OdsHelper.setValueAt(summary, "CM", line, 2); OdsHelper.setValueAt(summary,
	 * String.valueOf(countGroups), line, 3); OdsHelper.setValueAt(summary,
	 * String.valueOf(nbMinutes), line, 4);
	 * 
	 * for (CoursePref p : prefs) {
	 * 
	 * if (courseName.equals(p.getCourse().getName())) { courseHasTeacher = true;
	 * OdsHelper.setValueAt(summary, p.getTeacher().getFirstName(), line, 5);
	 * OdsHelper.setValueAt(summary, p.getTeacher().getLastName(), line, 6);
	 * 
	 * if (!p.getPrefCM().toString().equals("UNSPECIFIED")) {
	 * OdsHelper.setValueAt(summary, p.getPrefCM().toString(), line, 7); } }
	 * 
	 * line++; }
	 * 
	 * if (!courseHasTeacher) { line++; }
	 * 
	 * }
	 * 
	 * return line;
	 * 
	 * }
	 */

	/**
	 * This method creates a summarized Ods. For each course, it writes all the
	 * teachers who want to teach the course and their preferences.
	 * 
	 * @param allCourses This is a complete set of Courses.
	 * @param prefs      This is a complete set of Preferences.
	 * @return A document competed with all the courses and all the preferences of
	 *         the teachers for each course.
	 * @throws Throwable if the document could not be correctly completed
	 */

	public static SpreadsheetDocument createTeachersPreferences(ImmutableSet<Course> allCourses,
			ImmutableSet<CoursePref> prefs) throws Throwable {

		// A ameliorer: regrouper les cours en fonction du semestre

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
						line++;
					}
				}

				if (!courseHasTeacher) {
					line++;
				}

			}

		}
		document.save("target//TeachersPreferences.ods"); // ligne à supprimer avant de PR
		return document;

	}

	public static SpreadsheetDocument addNewTeacher(SpreadsheetDocument document, Teacher teacher,
			ImmutableSet<Course> allCourses, ImmutableSet<CoursePref> prefs) throws Throwable {

		// TO DO MAYBE
		// possible que ImmutableSet<Course> allCourses soit pas utile

		return document;
	}

	public static SpreadsheetDocument addNewCourse(SpreadsheetDocument document, Course cours,
			ImmutableSet<CoursePref> prefs) throws Throwable {

		// TO DO MAYBE

		return document;
	}
}
