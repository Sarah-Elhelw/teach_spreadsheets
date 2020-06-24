package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.AggregatedData;
import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.json.JsonSerializer;
import io.github.oliviercailloux.teach_spreadsheets.read.MultipleOdsPrefReader;
import io.github.oliviercailloux.teach_spreadsheets.write.AssignmentPerTeacher;
import io.github.oliviercailloux.teach_spreadsheets.write.OdsSummarizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the View and Model classes.
 */
public class Controller {

	private View view;
	private Model model;
	private final static Logger LOGGER = LoggerFactory.getLogger(View.class);

	public static Controller newInstance() {
		Controller controller = new Controller();
		controller.view = View.initializeGui();
		controller.model = Model.newInstance();
		return controller;
	}

	private Controller() {
	}

	/**
	 * Creates a new listener for a preferences Table in the GUI.
	 * 
	 * @param source the table for which we want to create the listener. It must one
	 *               of the tables from view.
	 * @return a listener that retrieves the table item that has been clicked from
	 *         source and calls callbackListener
	 */

	private Listener createListenerPreferences(Table source) {
		checkNotNull(source);
		checkNotNull(view);
		checkArgument(source == view.getAllPreferencesTable() || source == view.getChosenPreferencesTable(),
				"The table needs to be one of the two tables stored in view.");

		boolean toChosenPreferences = (view.getAllPreferencesTable() == source);

		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = source.getItem(pt);
				itemClicked(item, toChosenPreferences);
			}
		};
	}

	/**
	 * Creates a listener for the submit button.This Listener writes the Ods output
	 * files (global assignment and assignment per teacher) to disk when the submit
	 * button is clicked.
	 * 
	 * @param courses     These are the courses to be written in the output files.
	 * @param CoursePrefs These are the courses preferences to be written in the
	 *                    output files.
	 * @return a listener for submit button
	 */
	private Listener createListenerSubmitButton(Set<Course> courses, Set<CoursePref> CoursePrefs) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				Set<TeacherAssignment> teacherAssignments = createAssignments();
				Set<CourseAssignment> courseAssignments = CourseAssignment
						.teacherAssignmentsToCourseAssignments(teacherAssignments);
				OdsSummarizer odsGlobalAssignment = OdsSummarizer.newInstance(courses);
				odsGlobalAssignment.addPrefs(CoursePrefs);
				odsGlobalAssignment.setAllCoursesAssigned(ImmutableSet.copyOf(courseAssignments));
				/**
				 * This try catch is necessary as this function doesn't throw non-runtime
				 * exceptions (it is the main function of a Listener).
				 */
				try (SpreadsheetDocument odsGlobalAssignmentDocument = odsGlobalAssignment.createSummary()) {
					if (!Files.exists(Path.of("output"))) {
						Files.createDirectory(Path.of("output"));
					}
					if (!Files.exists(Path.of("output//teacher_assignments"))) {
						Files.createDirectory(Path.of("output//teacher_assignments"));
					}
					/**
					 * we need to clean the teacher_assignments folder after each submit so not to
					 * have files concerning teachers with no assignments.
					 */
					else {
						FileUtils.cleanDirectory(new File("output//teacher_assignments"));
					}
					for (TeacherAssignment teacherAssignment : teacherAssignments) {
						Teacher teacher = teacherAssignment.getTeacher();
						try (SpreadsheetDocument assignmentToTeacherDocument = AssignmentPerTeacher
								.createAssignmentPerTeacher(teacher, courseAssignments)) {
							assignmentToTeacherDocument.save("output//teacher_assignments//" + teacher.getFirstName()
									+ "_" + teacher.getLastName() + ".ods");
						}
					}
					odsGlobalAssignmentDocument.save("output//GlobalAssignment.ods");
				} catch (Exception e) {
					LOGGER.error("An error has occured during the writing of the assignment Files.", e);
				}
			}
		};
	}

	/**
	 * Callback function for the Table listeners in View. Updates Model, then
	 * updates View according to the input of the user.
	 * 
	 * @param item                the table item that has been clicked
	 * @param toChosenPreferences true iff the table item that has been clicked was
	 *                            on the table All Preferences
	 */
	private void itemClicked(TableItem item, boolean toChosenPreferences) {
		int i = 0;
		ArrayList<String> texts = new ArrayList<>();
		while (!item.getText(i).equals("")) {
			texts.add(item.getText(i));
			i += 1;
		}

		updatePreferences(texts.toArray(new String[0]), toChosenPreferences);
		view.moveTableItem(item, texts.toArray(new String[0]), toChosenPreferences);
	}

	/**
	 * callback function called when the user clicks on submit button in the GUI.
	 * 
	 * @return an ImmutableSet of teacher assignments corresponding to the chosen
	 *         preferences table in the GUI.
	 */
	private ImmutableSet<TeacherAssignment> createAssignments() {

		Set<CoursePrefElement> chosenPreferences = model.getChosenPreferences();
		com.google.common.collect.Table<Teacher, Course, TeacherAssignment.Builder> teacherAssignmentMapTable = HashBasedTable
				.create();

		for (CoursePrefElement coursePrefElement : chosenPreferences) {

			Teacher teacher = coursePrefElement.getCoursePref().getTeacher();
			String courseType = coursePrefElement.getCourseType().name();
			Course course = coursePrefElement.getCoursePref().getCourse();

			if (!teacherAssignmentMapTable.contains(teacher, course)) {
				TeacherAssignment.Builder assignmentBuilder = TeacherAssignment.Builder.newInstance(course, teacher);
				assignmentBuilder.setCountGroupsCM(0);
				assignmentBuilder.setCountGroupsCMTD(0);
				assignmentBuilder.setCountGroupsCMTP(0);
				assignmentBuilder.setCountGroupsTD(0);
				assignmentBuilder.setCountGroupsTP(0);

				assignGroup(assignmentBuilder, courseType);

				teacherAssignmentMapTable.put(teacher, course, assignmentBuilder);
			} else {
				TeacherAssignment.Builder assignmentBuilder = teacherAssignmentMapTable.get(teacher, course);
				assignGroup(assignmentBuilder, courseType);
			}

		}
		LinkedHashSet<TeacherAssignment> result = new LinkedHashSet<>();
		for (TeacherAssignment.Builder builder : teacherAssignmentMapTable.values()) {
			result.add(builder.build());
		}
		return ImmutableSet.copyOf(result);

	}

	/**
	 * adds one group to a teacher assignment.
	 * 
	 * @param teacherAssignmentBuilder
	 * @param choiceGroup
	 */
	private void assignGroup(TeacherAssignment.Builder teacherAssignmentBuilder, String choiceGroup) {
		checkNotNull(teacherAssignmentBuilder);
		checkNotNull(choiceGroup);

		switch (choiceGroup) {
		case "CM":
			teacherAssignmentBuilder.setCountGroupsCM((teacherAssignmentBuilder.getCountGroupsCM() + 1));
			break;
		case "CMTD":
			teacherAssignmentBuilder.setCountGroupsCMTD((teacherAssignmentBuilder.getCountGroupsCMTD() + 1));
			break;
		case "TD":
			teacherAssignmentBuilder.setCountGroupsTD((teacherAssignmentBuilder.getCountGroupsTD() + 1));
			break;
		case "CMTP":
			teacherAssignmentBuilder.setCountGroupsCMTP((teacherAssignmentBuilder.getCountGroupsCMTP() + 1));
			break;
		case "TP":
			teacherAssignmentBuilder.setCountGroupsTP((teacherAssignmentBuilder.getCountGroupsTP() + 1));
			break;
		default:
		}
	}

	/**
	 * returns the strings that are needed to be shown in the GUI for these elements
	 * 
	 * @return a list of arrays of strings : first element of array is teacher name,
	 *         second is course name, third is group type and fourth is teacher
	 *         choice for this course
	 */
	private List<String[]> getDataForTableItems(Set<CoursePrefElement> coursePrefElements) {
		checkNotNull(coursePrefElements);

		ArrayList<String[]> stringsToShow = new ArrayList<>();

		for (CoursePrefElement coursePrefElement : coursePrefElements) {
			stringsToShow.add(getDataForTableItem(coursePrefElement));
		}

		return stringsToShow;
	}

	/**
	 * returns the strings that are needed to be shown in the GUI for this element
	 * 
	 * @return an array of 4 strings : first is teacher name, second is course name,
	 *         third is group type and fourth is teacher choice for this course
	 */
	private String[] getDataForTableItem(CoursePrefElement coursePrefElement) {
		ArrayList<String> strings = new ArrayList<>();

		CoursePref coursePref = coursePrefElement.getCoursePref();

		Teacher teacher = coursePref.getTeacher();
		Course course = coursePref.getCourse();

		String teacherName = teacher.getLastName() + " " + teacher.getFirstName();
		String courseName = course.getName();
		String groupType = coursePrefElement.getCourseType().name();

		String choice = "";

		switch (groupType) {
		case "CM":
			choice = coursePref.getPrefCM().name();
			break;
		case "TD":
			choice = coursePref.getPrefTD().name();
			break;
		case "CMTD":
			choice = coursePref.getPrefCMTD().name();
			break;
		case "TP":
			choice = coursePref.getPrefTP().name();
			break;
		case "CMTP":
			choice = coursePref.getPrefCMTP().name();
			break;
		default:
		}

		strings.add(teacherName);
		strings.add(courseName);
		strings.add(groupType);
		strings.add(choice);

		return strings.toArray(new String[0]);
	}

	/**
	 * Updates sets of CoursePrefElement thanks to the data retrieved from a table
	 * item
	 * 
	 * @param stringTableItem the strings shown from the table item. Its size is 4 :
	 *                        first element is teacher name, second is course name,
	 *                        third is group type and fourth is teacher choice
	 * @param source          the set where we want to find the CoursePrefElement
	 *                        object corresponding to stringTableItem
	 * @param target          the set where we want to add the CoursePrefElement
	 *                        object corresponding to stringTableItem
	 */
	private void updateSet(String[] stringTableItem, Set<CoursePrefElement> source, Set<CoursePrefElement> target) {
		checkNotNull(stringTableItem);
		checkArgument(stringTableItem.length == 4);
		checkNotNull(source);
		checkNotNull(target);

		for (CoursePrefElement coursePrefElement : source) {

			String[] stringCoursePrefElement = getDataForTableItem(coursePrefElement);

			if (stringCoursePrefElement[0].equals(stringTableItem[0])
					&& stringCoursePrefElement[1].equals(stringTableItem[1])
					&& stringCoursePrefElement[2].equals(stringTableItem[2])
					&& stringCoursePrefElement[3].equals(stringTableItem[3])) {
				source.remove(coursePrefElement);
				target.add(coursePrefElement);
				break;
			}
		}
	}

	/**
	 * This method is called from Controller class when a table item has been
	 * clicked. Updates the data from Model thanks to the table item data.In order
	 * to call this function the model must already be set.
	 * 
	 * @param texts               the strings shown from the table item. texts' size
	 *                            is 4 : first element is teacher name, second is
	 *                            course name, third is group type and fourth is
	 *                            teacher choice
	 * @param toChosenPreferences true iff the element that has been clicked is on
	 *                            the Table named all preferences
	 */
	private void updatePreferences(String[] texts, boolean toChosenPreferences) {
		checkNotNull(texts);
		checkArgument(texts.length == 4);
		checkNotNull(model);

		if (toChosenPreferences) {
			updateSet(texts, model.getAllPreferences(), model.getChosenPreferences());
		} else {
			updateSet(texts, model.getChosenPreferences(), model.getAllPreferences());
		}
	}

	/**
	 * Shows the gui.
	 */
	public void show(Shell shell, Display display) {
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
		LOGGER.info("Display well closed");
	}

	/**
	 * The main that connect all the components of this program.
	 */
	public static void main(String[] args) throws Exception {
		/**
		 * Reading part.
		 */
		Set<CalcData> calcDatas = MultipleOdsPrefReader.readFilesFromFolder(Path.of("input"));
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		for (CalcData calcData : calcDatas) {
			aggregatedDataBuilder.addCalcData(calcData);
		}
		/**
		 * Writing the Ods summary and the Json courses part.
		 */
		
		AggregatedData aggregatedData = aggregatedDataBuilder.build();
		Set<Course> courses = aggregatedData.getCourses();
		Set<CoursePref> CoursePrefs = new LinkedHashSet<>();
		for (Course course : courses) {
			CoursePrefs.addAll(aggregatedData.getCoursePrefs(course));
		}
		OdsSummarizer odsPrefSummary = OdsSummarizer.newInstance(courses);
		odsPrefSummary.addPrefs(CoursePrefs);

		try (SpreadsheetDocument odsPrefSummaryDocument = odsPrefSummary.createSummary()) {
			if (!Files.exists(Path.of("output"))) {
				Files.createDirectory(Path.of("output"));
			}
			odsPrefSummaryDocument.save("output//odsPrefSummary.ods");
		}

		String coursesJson = JsonSerializer.serializeSet(courses);
		Files.writeString(Path.of("output//courses.json"), coursesJson);

		/**
		 * Gui management.
		 */
		Controller controller = Controller.newInstance();

		Table allPreferencesTable = controller.view.getAllPreferencesTable();
		Table chosenPreferencesTable = controller.view.getChosenPreferencesTable();
		Button submitButton = controller.view.getSubmitButton();

		controller.model.setDataFromSet(calcDatas);

		List<String[]> stringsToShowAllPreferences = controller
				.getDataForTableItems(controller.model.getAllPreferences());
		controller.view.populateCourses(controller.model.getCourses());
		controller.view.populateAllPreferences(stringsToShowAllPreferences);

		allPreferencesTable.addListener(SWT.MouseDoubleClick,
				controller.createListenerPreferences(allPreferencesTable));
		chosenPreferencesTable.addListener(SWT.MouseDoubleClick,
				controller.createListenerPreferences(chosenPreferencesTable));
		/**
		 * The writing process of the Ods output files (global assignment and assignment
		 * per teacher) are defined in the Listener of the submit button.
		 */
		submitButton.addListener(SWT.MouseDown, controller.createListenerSubmitButton(courses, CoursePrefs));
		controller.show(controller.view.getShell(), controller.view.getDisplay());
	}
}
