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
	public LinkedHashSet<CoursePref> readSemester(Table sheet, Teacher teacher) {
		LinkedHashSet<CoursePref> coursePrefList= new LinkedHashSet<>();
		while(isThereANextCourse(sheet)){
			
			Course.Builder courseBuilder=Course.Builder.newInstance();
			CoursePref.Builder prefBuilder=CoursePref.Builder.newInstance();
			courseBuilder.setName(Parse.parseName(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setCountGroupsCM(Parse.parseCountGroupsCM(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setCountGroupsCMTD(Parse.parseCountGroupsCMTD(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setCountGroupsCMTP(Parse.parseCountGroupsCMTP(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setCountGroupsTD(Parse.parseCountGroupsTD(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setNbHoursCM(Parse.parseNbHoursCM(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setNbHoursCMTD(Parse.parseNbHoursCMTD(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setNbHoursCMTP(Parse.parseNbHoursCMTP(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setNbHoursTD(Parse.parseNbHoursTD(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setNbHoursTP(Parse.parseNbHoursTP(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setSemester(Parse.parseSemester(sheet.getCellByPosition(currentCol,currentRow)));
			courseBuilder.setStudyYear(Parse.parseStudyYear(sheet.getCellByPosition(currentCol,currentRow)));
			
			Course course=courseBuilder.build();
			prefBuilder.setCourse(course);
			prefBuilder.setTeacher(teacher);
			prefBuilder.setPrefCM(Parse.parsePrefChar(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefCMTD(Parse.parsePrefChar(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefCMTP(Parse.parsePrefChar(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefNbGroupsCM(Parse.parsePrefInt(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefNbGroupsCMTD(Parse.parsePrefInt(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefNbGroupsCMTP(Parse.parsePrefInt(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefNbGroupsTD(Parse.parsePrefInt(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefNbGroupsTP(Parse.parsePrefInt(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefTD(Parse.parsePrefChar(sheet.getCellByPosition(currentCol,currentRow)));
			prefBuilder.setPrefTP(Parse.parsePrefChar(sheet.getCellByPosition(currentCol,currentRow)));
			
			
			
			
			courseList.add(course);
			coursePrefList.add(prefBuilder.build());			
			currentCol++;
			currentRow++;
		}
		numberOfDocumentsOpened++;
		currentCol=FIRST_COURSE_S2_COL;
		currentRow=FIRST_COURSE_S2_ROW;
		
		return coursePrefList;
		
	}

	
	



}
