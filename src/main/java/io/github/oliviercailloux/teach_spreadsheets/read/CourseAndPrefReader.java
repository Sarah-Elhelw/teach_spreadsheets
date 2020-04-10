package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.ArrayList;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CourseAndPrefReader {
	private final static int FIRST_COURSE_S1_COL=2;
	private final static int FIRST_COURSE_S1_ROW=4;
	private final static int FIRST_COURSE_S2_COL=14;
	private final static int FIRST_COURSE_S2_ROW=4;
	
	int currentCol=FIRST_COURSE_S1_COL;
	int currentRow=FIRST_COURSE_S1_ROW;
	int currentSemester=1;
	
	ArrayList<Course> courselist;
	
	public static CourseAndPrefReader newInstance(){
		return new CourseAndPrefReader();
	}
	private CourseAndPrefReader() {
		
	}
	public CoursePref readCoursesAndPrefsOfASemester(Table sheet){
		Cell cell;
		cell=sheet.getCellByPosition(currentCol,currentRow);
		Course.Builder courseBuilder=Course.Builder.newInstance();
		CoursePref.Builder Prefbuilder=CoursePref.Builder.newInstance();
	}
	
	public boolean isThereANextCourse(Table sheet) {
		Cell cell;
		cell=sheet.getCellByPosition(currentCol,currentRow);
		String test=cell.getDisplayText();
		if(currentSemester==1) {
			cell=sheet.getCellByPosition(FIRST_COURSE_S2_COL,FIRST_COURSE_S2_ROW);
			test=cell.getDisplayText();
		}
		return !test.equals("");
	}
	
	



}
