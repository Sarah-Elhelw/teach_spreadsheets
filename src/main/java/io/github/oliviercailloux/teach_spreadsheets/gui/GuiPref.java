package io.github.oliviercailloux.teach_spreadsheets.gui;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class GuiPref {
	private final static Logger LOGGER = LoggerFactory.getLogger(GuiPref.class);
	
	private Display display;
	private Shell shell;
	

	
	private Composite allPrefrences;
	private Composite chosenPrefrences;
	private Composite courses;
	
	private Composite allPrefrencesContent = null;
	private Composite chosenPrefrencesContent = null;
	private Composite coursesContent = null;
	
	private Composite submit;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
			GuiPref gui = new GuiPref();
			gui.initializeGui();
		}
	public void initializeGui() {
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setMaximized(true);
		shell.setText("Preferences selection");
		shell.setLayout(new GridLayout(3, false));
		Image logo = new Image(display, GuiPref.class.getResourceAsStream("configuration.png"));
		shell.setImage(logo);

		
		this.allPrefrences = new Composite(shell, SWT.BORDER);
		allPrefrencesContent = new Composite(allPrefrences, SWT.NONE);
		Image logoAllPreferences = new Image(display, GuiPref.class.getResourceAsStream("paper.png"));
		setCompositePreferenceTable(allPrefrences,allPrefrencesContent,"All prefrences",logoAllPreferences);
		
		this.chosenPrefrences = new Composite(shell, SWT.BORDER);
		chosenPrefrencesContent= new Composite(chosenPrefrences, SWT.NONE);
		Image logoChosenPrefrences  = new Image(display, GuiPref.class.getResourceAsStream("check.png"));
		setCompositePreferenceTable(chosenPrefrences,chosenPrefrencesContent,"Chosen prefrences",logoChosenPrefrences);
		
		this.courses = new Composite(shell, SWT.BORDER);
		coursesContent= new Composite(courses, SWT.NONE);
		Image logoCourses = new Image(display, GuiPref.class.getResourceAsStream("education.png"));
		setCompositeCourseTable(courses,coursesContent,"Courses",logoCourses);
		
		prefShell();
		setAndCreateBoutonSubmit();
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
		LOGGER.info("Display well closed");
	}
	
	private void prefShell() {
		GridData prefData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData chosenPrefData = new GridData(SWT.FILL, SWT.FILL, true, true);
		GridData coursesData = new GridData(SWT.FILL, SWT.FILL, true, true);
		Point size = shell.getSize();
		prefData.widthHint=size.x/3;
		chosenPrefData.widthHint=size.x/3;
		coursesData.widthHint=size.x-(prefData.widthHint+chosenPrefData.widthHint);
		this.allPrefrences.setLayoutData(prefData);
		this.chosenPrefrences.setLayoutData(chosenPrefData);
		this.courses.setLayoutData(coursesData);

		}
	
	/**
	 * This method closes a Shell if the user confirms it (by pressing YES button)
	 */
	private boolean exitShell() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to leave this window? All unsaved data will be lost!");
		messageBox.setText("Closing the window");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("The window has been closed.");
			return true;
		}
		return false;
	}
	/**
	 * This method closes the display.
	 */
	private void exitApplication() {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setMessage("Do you really want to quit the application?");
		messageBox.setText("Closing the application");
		int response = messageBox.open();
		if (response == SWT.YES) {
			LOGGER.info("The application has been closed.");
			System.exit(0);
		}
	}
	private void setCompositePreferenceTable(Composite parentComposite,Composite content, String headerText, Image logo) {
		parentComposite.setLayout(new GridLayout(1, true));
		
		Composite header = new Composite(content, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logo);
		Label txt = new Label(header, SWT.RIGHT);
		txt.setText(headerText);
		
		
		content.setLayout(new GridLayout(1, false));

		Table t = new Table(content,SWT.BORDER | SWT.V_SCROLL);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		//à modifier pour qu'elle s'adapte automatiquement!!!!
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
		course.setWidth(70);
		groupType.setWidth(70);
		choice.setWidth(70);

		t.setHeaderVisible(true);
		// à modifier !!!!!!!
		for(Integer i=0;i<50;i++) {
			LinkedHashMap<TableItem,CoursePref> mapPreference=new LinkedHashMap<>();
			TableItem item = new TableItem(t, SWT.NONE);
			item.setText(new String[] {i.toString(),"testTeacher","testCourse","testGroup","testchoice"});
		}
	}
	private void setCompositeCourseTable(Composite parentComposite,Composite content, String headerText, Image logo) {
		parentComposite.setLayout(new GridLayout(1, true));
		
		Composite header = new Composite(content, SWT.NONE);
		header.setLayout(new GridLayout(2, false));
		Label labelImg = new Label(header, SWT.LEFT);
		labelImg.setImage(logo);
		Label txt = new Label(header, SWT.RIGHT);
		txt.setText(headerText);
		
		content.setLayout(new GridLayout(1, false));

		Table t = new Table(content, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		//à modifier pour qu'elle s'adapte automatiquement!!!!
        gd_table.heightHint = 400;
        t.setLayoutData(gd_table);
		
		TableColumn course = new TableColumn(t, SWT.NONE);
		TableColumn groupType = new TableColumn(t, SWT.NONE);
		TableColumn count = new TableColumn(t, SWT.NONE);
		
		course.setText("Course");
		groupType.setText("Group type");
		count.setText("Count");


		course.setWidth(70);
		groupType.setWidth(90);
		count.setWidth(70);

		t.setHeaderVisible(true);
		// à modifier !!!!!!!
			TableItem item = new TableItem(t, SWT.NONE);
			item.setText(new String[] {"testCourse","testGroup","testcount"});
	}
	
	@SuppressWarnings("unused")
	private void setAndCreateBoutonSubmit() {
		//skip two columns of the grid ,expliquer le supess warning
		
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


	}
	
}