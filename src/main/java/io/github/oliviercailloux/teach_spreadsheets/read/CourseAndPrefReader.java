package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CourseAndPrefReader {
	private static int numberOfDocumentsOpened=0;
	
	private final static int FIRST_COURSE_S1_COL=2;
	private final static int FIRST_COURSE_S1_ROW=4;
	private final static int FIRST_COURSE_S2_COL=14;
	private final static int FIRST_COURSE_S2_ROW=4;
	
	int currentCol=FIRST_COURSE_S1_COL;
	int currentRow=FIRST_COURSE_S1_ROW;
	int currentSemester=1;
	
	LinkedHashSet<Course> courseList;
	
	public static CourseAndPrefReader newInstance(){
		return new CourseAndPrefReader();
	}
	private CourseAndPrefReader() {
		
	}

	public boolean isThereANextCourse(Table sheet) {
		Cell cell;
		cell=sheet.getCellByPosition(currentCol,currentRow);
		String test=cell.getDisplayText();
		return !test.equals("");
	}
	public LinkedHashSet<CoursePref> readSemester(Table sheet) {
		Cell cell;
		cell=sheet.getCellByPosition(currentCol,currentRow);
		LinkedHashSet<CoursePref> coursePrefList= new LinkedHashSet<>();
		while(isThereANextCourse(sheet)){
			Course.Builder courseBuilder=Course.Builder.newInstance();
			CoursePref.Builder prefBuilder=CoursePref.Builder.newInstance();

			
			
			
			courseList.add(courseBuilder.build());
			coursePrefList.add(prefBuilder.build());			
			currentCol++;
			currentRow++;
		}
		currentCol=FIRST_COURSE_S2_COL;
		currentRow=FIRST_COURSE_S2_ROW;
		
		return coursePrefList;
		
	}

	
	



}
