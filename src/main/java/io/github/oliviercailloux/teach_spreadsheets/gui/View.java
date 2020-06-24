package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

/**
 * The View class creates the shell and manages it. Some of the code in this
 * class is inspired from
 * <a href="https://github.com/oliviercailloux/Teach-spreadsheets">teach
 * spreadsheets</a> class and more specifically from the class <a href=
 * "https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/java/io/github/oliviercailloux/y2018/teach_spreadsheets/gui/GUIPref.java">GUIPref</a>.
 */
public class View {

	private Display display;
	private Shell shell;

	private Composite allPreferencesComposite;
	private Composite chosenPreferencesComposite;
	private Composite coursesComposite;

	private Table allPreferencesTable;
	private Table chosenPreferencesTable;
	private Table coursesTable;
	
	private Button buttonSubmit;

	private View() {}
	
	/**
	 * Creates the Gui components.
	 */
	public static View initializeGui() {
		View view = new View();
		
		view.display = new Display();
		view.shell = new Shell(view.display, SWT.SHELL_TRIM);
		view.shell.setMaximized(true);
		view.shell.setText("Preferences selection");
		view.shell.setLayout(new GridLayout(3, false));
		Image logo = new Image(view.display, View.class.getResourceAsStream("configuration.png"));
		view.shell.setImage(logo);

		view.allPreferencesComposite = new Composite(view.shell, SWT.BORDER);
		Composite allPreferencesContent = new Composite(view.allPreferencesComposite, SWT.NONE);
		Image logoAllPreferences = new Image(view.display, View.class.getResourceAsStream("paper.png"));
		view.allPreferencesTable = setCompositePreferenceTable(view.allPreferencesComposite, allPreferencesContent, "All preferences",
				logoAllPreferences);

		view.chosenPreferencesComposite = new Composite(view.shell, SWT.BORDER);
		Composite chosenPreferencesContent = new Composite(view.chosenPreferencesComposite, SWT.NONE);
		Image logoChosenPreferences = new Image(view.display, View.class.getResourceAsStream("check.png"));
		view.chosenPreferencesTable = setCompositePreferenceTable(view.chosenPreferencesComposite, chosenPreferencesContent,
				"Chosen preferences", logoChosenPreferences);

		view.coursesComposite = new Composite(view.shell, SWT.BORDER);
		Composite coursesContent = new Composite(view.coursesComposite, SWT.NONE);
		Image logoCourses = new Image(view.display, View.class.getResourceAsStream("education.png"));
		view.coursesTable = setCompositeCourseTable(view.coursesComposite, coursesContent, "Courses", logoCourses);

		view.prefShell();
		view.createAndSetSubmitButton();
		
		return view;
	}
	
	public Table getAllPreferencesTable() {
		return allPreferencesTable;
	}
	
	public Table getChosenPreferencesTable() {
		return chosenPreferencesTable;
	}
	
	public Button getSubmitButton() {
		return buttonSubmit;
	}
	
	public Shell getShell() {
		return shell;
	}
	
	public Display getDisplay() {
		return display;
	}

	/**
	 * moves a table item from "All preferences" table to "Chosen preferences"
	 * table, or the other way around.
	 * 
	 * @param tableItem           the table item that needs to be moved
	 * @param texts               the strings shown from the table item. Its size is
	 *                            4 : first element is teacher name, second is
	 *                            course name, third is group type and fourth is
	 *                            teacher choice
	 * @param toChosenPreferences true iff the table item needs to be moved to
	 *                            "Chosen preferences" table
	 */
	public void moveTableItem(TableItem tableItem, String[] texts, boolean toChosenPreferences) {
		checkNotNull(tableItem);
		checkNotNull(texts);
		checkArgument(texts.length == 4);

		tableItem.dispose();
		TableItem newItem;

		if (toChosenPreferences) {
			newItem = new TableItem(chosenPreferencesTable, SWT.NONE);
		} else {
			newItem = new TableItem(allPreferencesTable, SWT.NONE);
		}
		newItem.setText(texts);
	}

	/**
	 * Adds one course to "Courses" table
	 * 
	 * @param nbGroups   the number of groups for this course
	 * @param courseName the name of the course
	 * @param courseType the type of the course : CM, TD, TP, CMTD or CMTP
	 */
	private void addCourseToCoursesTable(int nbGroups, String courseName, CourseType courseType) {
		checkNotNull(courseName);
		checkNotNull(courseType);

		if (nbGroups != 0) {
			TableItem tableItem = new TableItem(coursesTable, SWT.NONE);
			tableItem.setText(new String[] { courseName, courseType.name(), String.valueOf(nbGroups) });
		}
	}
	
	/**
	 * Populates the courses table.
	 * 
	 * @param allPreferences a set of courses containing all the
	 *                       information to show
	 */
	public void populateCourses(Set<Course> courses) {
		checkNotNull(courses);

		for (Course course : courses) {
			addCourseToCoursesTable(course.getCountGroupsCM(), course.getName(), CourseType.CM);
			addCourseToCoursesTable(course.getCountGroupsTD(), course.getName(), CourseType.TD);
			addCourseToCoursesTable(course.getCountGroupsTP(), course.getName(), CourseType.TP);
			addCourseToCoursesTable(course.getCountGroupsCMTD(), course.getName(), CourseType.CMTD);
			addCourseToCoursesTable(course.getCountGroupsCMTP(), course.getName(), CourseType.CMTP);
		}
	}

	/**
	 * Populates the All Preferences Table
	 * @param stringsToShow a list of arrays of strings to set the texts for table items
	 */
	public void populateAllPreferences(List<String[]> stringsToShow) {
		checkNotNull(stringsToShow);
		
		for (String[] coursePrefElement : stringsToShow) {
			TableItem tableItem = new TableItem(allPreferencesTable, SWT.NONE);
			tableItem.setText(coursePrefElement);
		}
	}

	/**
	 * Creates the 3 tables of the Gui (allPreferences,chosenPreferences,courses).
	 */
	private void prefShell() {
		GridData prefData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData chosenPrefData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData coursesData = new GridData(SWT.FILL, SWT.FILL, true, true);
		Point size = shell.getSize();
		prefData.widthHint = size.x / 3;
		chosenPrefData.widthHint = size.x / 3;
		coursesData.widthHint = size.x - (prefData.widthHint + chosenPrefData.widthHint);
		this.allPreferencesComposite.setLayoutData(prefData);
		this.chosenPreferencesComposite.setLayoutData(chosenPrefData);
		this.coursesComposite.setLayoutData(coursesData);
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
	private static Table setCompositePreferenceTable(Composite parentComposite, Composite content, String headerText,
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
		
		Listener sortListener = new Listener() {  
	         public void handleEvent(Event e) {  
	             TableItem[] items = table.getItems();  
	             Collator collator = Collator.getInstance(Locale.getDefault());  
	             TableColumn column = (TableColumn)e.widget;
	             int index = (column == teacher ? 0 : (column == course ? 1 : (column == groupType ? 2 : 3)));
	             for (int i = 1; i < items.length; i++) {  
	                 String value1 = items[i].getText(index);  
	                 for (int j = 0; j < i; j++){  
	                     String value2 = items[j].getText(index);  
	                     if (collator.compare(value1, value2) < 0) {  
	                         String[] values = {items[i].getText(0), items[i].getText(1), items[1].getText(2), items[i].getText(3)};  
	                         items[i].dispose();  
	                         TableItem item = new TableItem(table, SWT.NONE, j);  
	                         item.setText(values);  
	                         items = table.getItems();  
	                         break;  
	                     }  
	                 }  
	             }  
	             table.setSortColumn(column);  
	         }  
	     };  
	     teacher.addListener(SWT.Selection, sortListener);
	     course.addListener(SWT.Selection, sortListener);
	     groupType.addListener(SWT.Selection, sortListener);
	     choice.addListener(SWT.Selection, sortListener);

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
	private static Table setCompositeCourseTable(Composite parentComposite, Composite content, String headerText, Image logo) {
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

		Table table = new Table(content, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_table.heightHint = 400;
		table.setLayoutData(gd_table);

		TableColumn course = new TableColumn(table, SWT.NONE);
		TableColumn groupType = new TableColumn(table, SWT.NONE);
		TableColumn count = new TableColumn(table, SWT.NONE);

		course.setText("Course");
		groupType.setText("Group type");
		count.setText("Count");

		course.setWidth(130);
		groupType.setWidth(80);
		count.setWidth(70);

		table.setHeaderVisible(true);

		return table;
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

		Composite submit = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, true);
		submit.setLayout(gridLayout);
		submit.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		buttonSubmit = new Button(submit, SWT.NONE);
		buttonSubmit.setText("Submit");
	}

}
