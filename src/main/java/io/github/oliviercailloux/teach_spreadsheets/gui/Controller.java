package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.read.MultipleOdsPrefReader;
import io.github.oliviercailloux.teach_spreadsheets.read.PrefsInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the View and Model classes.
 */
public class Controller {

	private View view;
	private Model model;
	private final static Logger LOGGER = LoggerFactory.getLogger(View.class);
	
	public static Controller newInstance(View view, Model model) {
		checkNotNull(view);
		checkNotNull(model);
		
		Controller controller = new Controller();
		controller.view = view;
		controller.model = model;
		return controller;
	}
	
	private Controller() {}
	
	/**
	 * Registers a listener for the widget in parameter. This listener will be triggered depending on eventType.
	 * @param listener
	 * @param widget
	 * @param eventType
	 */
	public void registerListener(Listener listener, Widget widget, int eventType) {
		checkNotNull(widget);
		checkNotNull(listener);
		
		widget.addListener(eventType, listener);
	}
	
	/**
	 * Creates a new listener for a preferences Table in the GUI.
	 * @param source the table for which we want to create the listener. It must one of the tables from view.
	 * @return a listener that retrieves the table item that has been clicked from source and calls callbackListener
	 */
	public Listener createListenerPreferences(Table source) {
		checkNotNull(source);
		checkNotNull(view);
		checkArgument(source == view.getAllPreferencesTable() || source == view.getChosenPreferencesTable(), "The table needs to be one of the two tables stored in view.");
		
		boolean toChosenPreferences = (view.getAllPreferencesTable() == source); 
		
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = source.getItem(pt);
				callbackListener(item, toChosenPreferences);
			}
		};
	}
	
	/**
	 * Creates a listener for the submit button.
	 * @return a listener that calls createAssignments, logs the results and prompts the user to exit the application.
	 */
	public Listener createListenerSubmitButton() {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				LOGGER.info("Submitted assignments: " + createAssignments().toString());
				view.exitApplication();
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
	public void callbackListener(TableItem item, boolean toChosenPreferences) {
		checkNotNull(item);
		checkNotNull(view);
		checkNotNull(model);

		int i = 0;
		ArrayList<String> texts = new ArrayList<>();
		while (!item.getText(i).equals("")) {
			texts.add(item.getText(i));
			i += 1;
		}

		model.updatePreferences(texts, toChosenPreferences);
		view.moveTableItem(item, texts.toArray(new String[0]), toChosenPreferences);
	}
	

	/**
	 * Populates Model data with the ods files
	 * 
	 * @throws Exception
	 */
	public void setModelData() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("multipleOdsFolder");
		try (InputStream stream = resourceUrl.openStream()) {
			Set<CalcData> calcDatas = MultipleOdsPrefReader.readFilesFromFolder(Path.of(resourceUrl.toURI()));
			model.setDataFromSet(calcDatas);
		}
	}
	
	/**
	 * callback function called when the user clicks on submit button in the GUI.
	 * 
	 * @return an ImmutableSet of teacher assignments corresponding to the chosen
	 *         preferences table in the GUI.
	 */
	public ImmutableSet<TeacherAssignment> createAssignments() {

		Set<CoursePrefElement> chosenPreferences = model.getChosenPreferences();
		com.google.common.collect.Table<Teacher, Course, TeacherAssignment.Builder> teacherAssignmentMapTable = HashBasedTable
				.create();

		for (CoursePrefElement coursePrefElement : chosenPreferences) {

			Teacher teacher = coursePrefElement.getCoursePref().getTeacher();
			String courseType = coursePrefElement.getCourseType().name();
			Course course = coursePrefElement.getCoursePref().getCourse();

			if (!teacherAssignmentMapTable.contains(teacher, course)) {
				TeacherAssignment.Builder assignmentBuilder = TeacherAssignment.Builder.newInstance(course,teacher);
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
	 * the only purpose of this main is to test the gui.This is not the main
	 * function of this program.
	 */
	public static void main(String[] args) throws Exception {
		View view = View.initializeGui(); 
		Model model = Model.newInstance();
		Controller controller = Controller.newInstance(view, model);
		
		Table allPreferencesTable = view.getAllPreferencesTable();
		Table chosenPreferencesTable = view.getChosenPreferencesTable();
		Button submitButton = view.getSubmitButton();
		
		controller.setModelData();
		Set<CoursePrefElement> allPreferences = model.getAllPreferences();
		view.initPreferences(allPreferences);
		
		controller.registerListener(controller.createListenerPreferences(allPreferencesTable), allPreferencesTable, SWT.MouseDoubleClick);
		controller.registerListener(controller.createListenerPreferences(chosenPreferencesTable), chosenPreferencesTable, SWT.MouseDoubleClick);
		controller.registerListener(controller.createListenerSubmitButton(), submitButton, SWT.MouseDown);

		view.show();
	}

}
