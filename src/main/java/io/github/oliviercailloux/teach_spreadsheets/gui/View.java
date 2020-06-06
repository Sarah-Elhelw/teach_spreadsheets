package io.github.oliviercailloux.teach_spreadsheets.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class View {
	private final static Logger LOGGER = LoggerFactory.getLogger(View.class);

	private Display display;
	private Shell shell;

	private Composite allPrefrences;
	private Composite chosenPrefrences;
	private Composite courses;

	private Composite allPrefrencesContent = null;
	private Composite chosenPrefrencesContent = null;
	private Composite coursesContent = null;

	private Table allPreferencesTable;
	private Table chosenPreferencesTable;
	private Table coursesTable;

	private Composite submit;

	public void initializeGui() {
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setMaximized(true);
		shell.setText("Preferences selection");
		shell.setLayout(new GridLayout(3, false));
		Image logo = new Image(display, View.class.getResourceAsStream("configuration.png"));
		shell.setImage(logo);

		this.allPrefrences = new Composite(shell, SWT.BORDER);
		allPrefrencesContent = new Composite(allPrefrences, SWT.NONE);
		Image logoAllPreferences = new Image(display, View.class.getResourceAsStream("paper.png"));
		allPreferencesTable = setCompositePreferenceTable(allPrefrences, allPrefrencesContent, "All preferences",
				logoAllPreferences);

		this.chosenPrefrences = new Composite(shell, SWT.BORDER);
		chosenPrefrencesContent = new Composite(chosenPrefrences, SWT.NONE);
		Image logoChosenPrefrences = new Image(display, View.class.getResourceAsStream("check.png"));
		chosenPreferencesTable = setCompositePreferenceTable(chosenPrefrences, chosenPrefrencesContent,
				"Chosen preferences", logoChosenPrefrences);

		addListenerPreferences(allPreferencesTable, true);
		addListenerPreferences(chosenPreferencesTable, false);

		this.courses = new Composite(shell, SWT.BORDER);
		coursesContent = new Composite(courses, SWT.NONE);
		Image logoCourses = new Image(display, View.class.getResourceAsStream("education.png"));
		coursesTable = setCompositeCourseTable(courses, coursesContent, "Courses", logoCourses);

		prefShell();
		setAndCreateBoutonSubmit();
	}

	public void show() {
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
		LOGGER.info("Display well closed");
	}

	private static void addListenerPreferences(Table source, boolean toChosenPreferences) {
		source.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Point pt = new Point(event.x, event.y);
				TableItem item = source.getItem(pt);
				Controller.callbackListener(item, toChosenPreferences);
			}
		});
	}

	// https://stackoverflow.com/questions/5374311/convert-arrayliststring-to-string-array
	public void moveTableItem(TableItem tableItem, ArrayList<String> texts, boolean toChosenPreferences) {
		tableItem.dispose();
		TableItem newItem;

		if (toChosenPreferences) {
			newItem = new TableItem(chosenPreferencesTable, SWT.NONE);
		} else {
			newItem = new TableItem(allPreferencesTable, SWT.NONE);
		}
		newItem.setText(texts.toArray(new String[0]));
	}

	public void initPreferences(Set<CoursePrefElement> allPreferences) {
		ArrayList<Course> coursesShown = new ArrayList<>();

		for (CoursePrefElement coursePrefElement : allPreferences) {
			CoursePref coursePref = coursePrefElement.getCoursePref();
			Course course = coursePref.getCourse();

			if (!coursesShown.contains(course)) {
				coursesShown.add(course);
				if (course.getCountGroupsCM() != 0) {
					TableItem tableItem = new TableItem(coursesTable, SWT.NONE);
					tableItem.setText(new String[] { course.getName(), CourseType.CM.name(),
							String.valueOf(course.getCountGroupsCM()) });
				}
				if (course.getCountGroupsTD() != 0) {
					TableItem tableItem = new TableItem(coursesTable, SWT.NONE);
					tableItem.setText(new String[] { course.getName(), CourseType.TD.name(),
							String.valueOf(course.getCountGroupsTD()) });
				}
				if (course.getCountGroupsTP() != 0) {
					TableItem tableItem = new TableItem(coursesTable, SWT.NONE);
					tableItem.setText(new String[] { course.getName(), CourseType.TP.name(),
							String.valueOf(course.getCountGroupsTP()) });
				}
				if (course.getCountGroupsCMTP() != 0) {
					TableItem tableItem = new TableItem(coursesTable, SWT.NONE);
					tableItem.setText(new String[] { course.getName(), CourseType.CMTP.name(),
							String.valueOf(course.getCountGroupsCMTP()) });
				}
				if (course.getCountGroupsCMTD() != 0) {
					TableItem tableItem = new TableItem(coursesTable, SWT.NONE);
					tableItem.setText(new String[] { course.getName(), CourseType.CMTD.name(),
							String.valueOf(course.getCountGroupsCMTD()) });
				}
			}

			String teacher = coursePref.getTeacher().getLastName() + " " + coursePref.getTeacher().getFirstName();
			String courseName = course.getName();
			String groupType = coursePrefElement.getCourseType().name();
			String choice = ""; // A, B, C ou autres
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
			TableItem tableItem = new TableItem(allPreferencesTable, SWT.NONE);
			tableItem.setText(new String[] { teacher, courseName, groupType, choice });
		}
	}

	private void prefShell() {
		GridData prefData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData chosenPrefData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData coursesData = new GridData(SWT.FILL, SWT.FILL, true, true);
		Point size = shell.getSize();
		prefData.widthHint = size.x / 3;
		chosenPrefData.widthHint = size.x / 3;
		coursesData.widthHint = size.x - (prefData.widthHint + chosenPrefData.widthHint);
		this.allPrefrences.setLayoutData(prefData);
		this.chosenPrefrences.setLayoutData(chosenPrefData);
		this.courses.setLayoutData(coursesData);

	}

	/**
	 * This method closes the application.
	 */
	public void exitApplication() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to quit the application?");
		messageBox.setText("Closing the application");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("The application has been closed.");
			System.exit(0);
		}
	}

	private Table setCompositePreferenceTable(Composite parentComposite, Composite content, String headerText,
			Image logo) {
		parentComposite.setLayout(new GridLayout(1, true));

		Composite header = new Composite(content, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logo);
		Label txt = new Label(header, SWT.RIGHT);
		txt.setText(headerText);

		content.setLayout(new GridLayout(1, false));

		Table t = new Table(content, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		// à modifier pour qu'elle s'adapte automatiquement!!!!
		gd_table.heightHint = 400;
		t.setLayoutData(gd_table);

		TableColumn teacher = new TableColumn(t, SWT.NONE);
		TableColumn course = new TableColumn(t, SWT.NONE);
		TableColumn groupType = new TableColumn(t, SWT.NONE);
		TableColumn choice = new TableColumn(t, SWT.NONE);

		teacher.setText("Teacher");
		course.setText("Course");
		groupType.setText("Group type");
		choice.setText("Choice");

		teacher.setWidth(70);
		course.setWidth(130);
		groupType.setWidth(80);
		choice.setWidth(70);
		t.setHeaderVisible(true);

		return t;
	}

	private Table setCompositeCourseTable(Composite parentComposite, Composite content, String headerText, Image logo) {
		parentComposite.setLayout(new GridLayout(1, true));

		Composite header = new Composite(content, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logo);
		Label txt = new Label(header, SWT.RIGHT);
		txt.setText(headerText);

		content.setLayout(new GridLayout(1, false));

		Table t = new Table(content, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		// à modifier pour qu'elle s'adapte automatiquement!!!!
		gd_table.heightHint = 400;
		t.setLayoutData(gd_table);

		TableColumn course = new TableColumn(t, SWT.NONE);
		TableColumn groupType = new TableColumn(t, SWT.NONE);
		TableColumn count = new TableColumn(t, SWT.NONE);

		course.setText("Course");
		groupType.setText("Group type");
		count.setText("Count");

		course.setWidth(130);
		groupType.setWidth(80);
		count.setWidth(70);

		t.setHeaderVisible(true);

		return t;
	}

	@SuppressWarnings("unused")
	private void setAndCreateBoutonSubmit() {
		// skip two columns of the grid ,expliquer le supess warning

		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		submit = new Composite(shell, SWT.NONE);
		GridLayout gl = new GridLayout(1, true);
		submit.setLayout(gl);
		submit.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		// Button to submit the preferences for a specified course
		Button buttonSubmit;

		buttonSubmit = new Button(submit, SWT.NONE);
		buttonSubmit.setText("Submit ");

		buttonSubmit.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ImmutableSet<TeacherAssignment> t = Controller.createAssignments();
				System.out.println(t);
			}
		});
	}

}
