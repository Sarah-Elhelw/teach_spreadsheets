package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.ArrayList;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Course.Builder;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CourseAndPrefReader {
	private static int numberOfDocumentsOpened=0;
	
	private final static String COURSTD = "CMTD";
	private final static String COURSTP = "CMTP";
	private final static String TD = "TD";
	private final static String TP = "TP";
	
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
			setInfoCourse(sheet,courseBuilder,currentCol,currentRow);

			
			Course course=courseBuilder.build();
			prefBuilder.setCourse(course);
			prefBuilder.setTeacher(teacher);
			setInfoPref(sheet,prefBuilder,currentCol,currentRow+6);
			
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
	private void setInfoPref(Table sheet,CoursePref.Builder prefBuilder,int j,int i) {
		TODO();
		
	}
	private void setInfoCourse(Table currentSheet,Course.Builder courseBuilder, int currentCol,int currentRow) {
		int j =currentCol,i=currentRow;
		Cell actualCell = currentSheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();


		courseBuilder.setName(cellText.replaceAll("\n", " "));

		j+=2;
		
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbHoursCM(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			courseBuilder.setNbHoursCM(hoursToMinutes(hourTab[0]));
		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbHoursTD(0);
			courseBuilder.setNbHoursCMTD(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			if (hourStr.contains(COURSTD)) {
				courseBuilder.setNbHoursCMTD(hoursToMinutes(hourTab[0]));
			} else if (hourStr.contains(TD)) {
				courseBuilder.setNbHoursTD(hoursToMinutes(hourTab[0]));
			}
		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbHoursTP(0);
			courseBuilder.setCountGroupsCMTP(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			if (hourStr.contains(COURSTP)) {
				courseBuilder.setCountGroupsCMTP(hoursToMinutes(hourTab[0]));
			} else if (hourStr.contains(TP)) {
				courseBuilder.setNbHoursTP(hoursToMinutes(hourTab[0]));
			}

		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setCountGroupsCM(0);
			courseBuilder.setCountGroupsCMTD(0);
			courseBuilder.setCountGroupsCMTP(0);
			courseBuilder.setCountGroupsTD(0);
			courseBuilder.setCountGroupsTP(0);

		} else {
			//setting the group numbers
		}
		

	}
	public boolean isDiagonalBorder(Table currentSheet, String cellPosition) {
		/*
		 * There is a problem with ODFTookit, their function getBorder return NULL if
		 * the border doesn't exists, but if there is a border, It doesn't return the
		 * description but a NumberFormatException, so the catch fix it
		 */
		Cell cell = currentSheet.getCellByPosition(cellPosition);
		if (cell == null)
			return false;
		try {
			cell.getBorder(CellBordersType.DIAGONALBLTR);
		} catch (@SuppressWarnings("unused") NullPointerException e) {
			return false;
		} catch (@SuppressWarnings("unused") NumberFormatException z) {
			return true;
		}
		return false;
	}

	public boolean isDiagonalBorder(Table currentSheet, int columnIndex, int rowIndex) {
		Cell cell = currentSheet.getCellByPosition(columnIndex, rowIndex);
		if (cell == null)
			return false;
		try {
			cell.getBorder(CellBordersType.DIAGONALBLTR);
		} catch (@SuppressWarnings("unused") NullPointerException e) {
			return false;
		} catch (@SuppressWarnings("unused") NumberFormatException z) {
			return true;
		}
		return false;
	}
	
	private int hoursToMinutes(String hours){
		return (int)(Double.parseDouble(hours)*60); 	
	}
	
	
		
	}

	
	




