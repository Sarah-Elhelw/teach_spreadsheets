package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment.Builder;
import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.read.MultipleOdsPrefReader;
import io.github.oliviercailloux.teach_spreadsheets.read.PrefsInitializer;

/**
 * Manages the View and Model classes via callback functions.
 */
public class Controller {

	private static View gui;

	/**
	 * callback function called when the user clicks on submit button in the GUI.
	 * 
	 * @return an ImmutableSet of teacher assignments corresponding to the chosen
	 *         preferences table in the GUI.
	 */
	public static ImmutableSet<TeacherAssignment> createAssignments() {

		Set<CoursePrefElement> chosenPreferences = Model.getChosenPreferences();
		com.google.common.collect.Table<Teacher, Course, TeacherAssignment.Builder> teacherAssignmentMapTable = HashBasedTable
				.create();

		for (CoursePrefElement coursePrefElement : chosenPreferences) {

			Teacher teacher = coursePrefElement.getCoursePref().getTeacher();
			String courseType = coursePrefElement.getCourseType().name();
			Course course = coursePrefElement.getCoursePref().getCourse();

			if (!teacherAssignmentMapTable.contains(teacher, course)) {
				TeacherAssignment.Builder assignmentBuilder = TeacherAssignment.Builder.newInstance(teacher);
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
	 * Callback function for the Table listeners in View. Updates Model, then
	 * updates View according to the input of the user.
	 * 
	 * @param item                the table item that has been clicked
	 * @param toChosenPreferences true iff the table item that has been clicked was
	 *                            on the table All Preferences
	 */
	public static void callbackListener(TableItem item, boolean toChosenPreferences) {
		checkNotNull(item);
		checkNotNull(toChosenPreferences);

		int i = 0;
		ArrayList<String> texts = new ArrayList<>();
		while (!item.getText(i).equals("")) {
			texts.add(item.getText(i));
			i += 1;
		}

		Model.updatePreferences(texts, toChosenPreferences);
		gui.moveTableItem(item, texts, toChosenPreferences);
	}

	/**
	 * adds one group to a teacher assignment.
	 * 
	 * @param teacherAssignmentBuilder
	 * @param choiceGroup
	 */
	private static void assignGroup(TeacherAssignment.Builder teacherAssignmentBuilder, String choiceGroup) {
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
	 * Populates Model data with the ods files
	 * 
	 * @throws Exception
	 */
	public static void setModelData() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("multipleOdsFolder");
		try (InputStream stream = resourceUrl.openStream()) {
			Set<CalcData> calcDatas = MultipleOdsPrefReader.readFilesFromFolder(Path.of(resourceUrl.toURI()));
			Model.setDataFromSet(calcDatas);
		}
	}

	/**
	 * the only purpose of this main is to test the gui.This is not the main
	 * function of this program.
	 */
	public static void main(String[] args) throws Exception {
		gui = new View();
		gui.initializeGui();

		Model.initData();
		setModelData();

		Set<CoursePrefElement> allPreferences = Model.getAllPreferences();
		gui.initPreferences(allPreferences);
		gui.show();
	}

}
