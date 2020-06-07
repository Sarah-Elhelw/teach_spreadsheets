package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

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

/**
 * The View class creates the shell and manages it. Some of the code in this
 * class is inspired from
 * <a href="https://github.com/oliviercailloux/Teach-spreadsheets">teach
 * spreadsheets</a> class and more specifically from the class <a href=
 * "https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/java/io/github/oliviercailloux/y2018/teach_spreadsheets/gui/GUIPref.java">GUIPref</a>.
 */
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

	/**
	 * Creates the Gui components.
	 */
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
		createAndSetSubmitButton();
	}

	/**
	 * Shows the gui.
	 */
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

	/**
	 * 
	 * @param source
	 * @param toChosenPreferences
	 */
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

	/**
	 * 
	 * @param tableItem
	 * @param texts
	 * @param toChosenPreferences
	 */
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

	/**
	 * Initializes the courses and allPreferences tables.
	 * 
	 * @param allPreferences a set of CoursePrefElement containing all the
	 *                       information about the preferences
	 */
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

			List<String> data = coursePrefElement.getDataForTableItem();
			TableItem tableItem = new TableItem(allPreferencesTable, SWT.NONE);
			tableItem.setText(data.toArray(new String[0]));
		}
	}

	/**
	 * Creates the 3 tables of the Gui (allPrefrences,chosenPrefrences,courses).
	 */
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
	private void exitApplication() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Your choices have been submitted. Do you want to exit the application?");
		messageBox.setText("Closing the application");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("The application has been closed.");
			System.exit(0);
		}
	}

	/**
	 * Sets a preference table composite parameters and creates the table for this
	 * composite.
	 * 
	 * @param parentComposite the parent of the composite to be set, should not be
	 *                        null
	 * @param content         the content composite of the PreferenceTable, should
	 *                        not be null
	 * @param headerText      a title to be displayed above the table
	 * @param logo            a logo to be displayed next to the title
	 * @return the table created
	 */
	private Table setCompositePreferenceTable(Composite parentComposite, Composite content, String headerText,
			Image logo) {
		checkNotNull(parentComposite);
		checkNotNull(content);
		checkNotNull(headerText);
		checkNotNull(logo);

		parentComposite.setLayout(new GridLayout(1, true));

		Composite header = new Composite(content, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logo);
		Label label = new Label(header, SWT.RIGHT);
		label.setText(headerText);

		content.setLayout(new GridLayout(1, false));

		Table table = new Table(content, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_table.heightHint = 400;
		table.setLayoutData(gd_table);

		TableColumn teacher = new TableColumn(table, SWT.NONE);
		TableColumn course = new TableColumn(table, SWT.NONE);
		TableColumn groupType = new TableColumn(table, SWT.NONE);
		TableColumn choice = new TableColumn(table, SWT.NONE);

		teacher.setText("Teacher");
		course.setText("Course");
		groupType.setText("Group type");
		choice.setText("Choice");

		teacher.setWidth(70);
		course.setWidth(130);
		groupType.setWidth(80);
		choice.setWidth(70);
		table.setHeaderVisible(true);

		return table;
	}

	/**
	 * Same as setCompositePreferenceTable but for courses. Sets a course table
	 * composite parameters and creates the table for this composite
	 * 
	 * @param parentComposite parentComposite the parent of the composite to be set,
	 *                        should not be null
	 * @param content         the content composite of the course table , should not
	 *                        be null
	 * @param headerText      headerText a title to be displayed above the table
	 * @param logo            a logo to be displayed next to the title
	 * @return the table created
	 */
	private Table setCompositeCourseTable(Composite parentComposite, Composite content, String headerText, Image logo) {
		checkNotNull(parentComposite);
		checkNotNull(content);
		checkNotNull(headerText);
		checkNotNull(logo);
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

	/**
	 * this @SuppressWarnings is needed because the two labels created won't be
	 * used.they are only used to skip two columns so that the submit button is on
	 * the bottom far right of the window.
	 */
	@SuppressWarnings("unused")
	/**
	 * Sets and creates the submit Button.
	 */
	private void createAndSetSubmitButton() {
		/**
		 * skip two columns of the grid
		 */
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		submit = new Composite(shell, SWT.NONE);
		GridLayout gl = new GridLayout(1, true);
		submit.setLayout(gl);
		submit.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		Button buttonSubmit;

		buttonSubmit = new Button(submit, SWT.NONE);
		buttonSubmit.setText("Submit ");

		buttonSubmit.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ImmutableSet<TeacherAssignment> teacherAssignments = Controller.createAssignments();
				// remplacer avec un log juste avant la PR
				System.out.println(teacherAssignments);
				exitApplication();
			}
		});
	}

}
