package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CourseAndPrefReaderApprentissage {
	public static CourseAndPrefReaderApprentissage newInstance() {
		return new CourseAndPrefReaderApprentissage();
	}
	private CourseAndPrefReaderApprentissage() {
		
	}
	private final static int FIRST_COURSE_S1_COL_L3_S1 = 0;
	private final static int FIRST_COURSE_S1_ROW_L3_S1 = 4;
	private final static int FIRST_COURSE_S1_COL_L3_S2 = 6;
	private final static int FIRST_COURSE_S1_ROW_L3_S2 = 4;
	private final static int FIRST_COURSE_S1_COL_M1_S1 = 0;
	private final static int FIRST_COURSE_S1_ROW_M1_S1 = 18;
	private final static int FIRST_COURSE_S1_COL_M1_S2 = 6;
	private final static int FIRST_COURSE_S1_ROW_M1_S2 = 18;
		
	public ImmutableSet<CoursePref> readApprentissage(Table sheet, Teacher teacher) {
		Set<CoursePref> coursePrefs=new LinkedHashSet<>();
		setInfo(sheet,teacher,coursePrefs,FIRST_COURSE_S1_COL_L3_S1,FIRST_COURSE_S1_ROW_L3_S1,1);
		setInfo(sheet,teacher,coursePrefs,FIRST_COURSE_S1_COL_L3_S2,FIRST_COURSE_S1_ROW_L3_S2,2);
		setInfo(sheet,teacher,coursePrefs,FIRST_COURSE_S1_COL_M1_S1,FIRST_COURSE_S1_ROW_M1_S1,1);
		setInfo(sheet,teacher,coursePrefs,FIRST_COURSE_S1_COL_M1_S2,FIRST_COURSE_S1_ROW_M1_S2,2);
		return ImmutableSet.copyOf(coursePrefs);
	}
	

	private void setInfo(Table sheet, Teacher teacher,Set<CoursePref> coursePrefs,int currentCol, int currentRow, int semester) {
		int i = currentRow;
		while (CourseAndPrefReaderLib.isThereANextCourse(sheet, currentCol, i)) {
		Course.Builder courseBuilder = Course.Builder.newInstance();
		setInfoCourseApprentissage(sheet, courseBuilder, currentCol, i,semester);
		Course course = courseBuilder.build();
		CoursePref.Builder prefBuilder = CoursePref.Builder.newInstance(course, teacher);
		setInfoPrefApprentissage(sheet, prefBuilder, currentCol + 3, i);
		coursePrefs.add(prefBuilder.build());
		i++;
		}
	}

	
	public void setInfoCourseApprentissage(Table currentSheet, Course.Builder courseBuilder, int currentCol, int currentRow,
			int semester) {
		int j = currentCol, i = currentRow;
		String[] cellData;
		Cell actualCell = currentSheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		courseBuilder.setStudyYear("APPRENTISSAGE");
		courseBuilder.setSemester(semester);
		courseBuilder.setName(cellText.replaceAll("\n", " "));
		j+=2;

		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();
		cellData = cellText.split(" ");
		if(cellData.length==2 && StringUtils.isNumeric(cellData[0])) {
			courseBuilder.setnbMinutesCMTD(ReaderLib.hoursToMinutes(cellData[0]));
			
			courseBuilder.setnbMinutesCM(0);
			courseBuilder.setnbMinutesTD(0);
			courseBuilder.setnbMinutesTP(0);
			courseBuilder.setnbMinutesCMTP(0);
		}
		else {
			throw new IllegalStateException("The also tab has at least one value in the 'Nb heures Cours-TD' column that isn't in the right format at "+i+","+j);
		}
			courseBuilder.setCountGroupsCMTD(1);	
			courseBuilder.setCountGroupsCM(0);
			courseBuilder.setCountGroupsTD(0);
			courseBuilder.setCountGroupsTP(0);
			courseBuilder.setCountGroupsCMTP(0);
		
	}
	
	public void setInfoPrefApprentissage(Table sheet, CoursePref.Builder prefBuilder, int j, int i) {
		Cell actualCell = sheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText(); 
		cellText=cellText.trim();
		if(cellText.equals("")) {
			prefBuilder.setPrefCMTD(Preference.UNSPECIFIED);
			prefBuilder.setPrefNbGroupsCMTD(0);
		}
		else if(cellText.equals("Choix A")) {
			prefBuilder.setPrefCMTD(Preference.A);
			prefBuilder.setPrefNbGroupsCMTD(1);
		}
		else if(cellText.equals("Choix B")) {
			prefBuilder.setPrefCMTD(Preference.B);
			prefBuilder.setPrefNbGroupsCMTD(1);
		}
		else if(cellText.equals("Choix C")) {
			prefBuilder.setPrefCMTD(Preference.C);
			prefBuilder.setPrefNbGroupsCMTD(1);
		}
		else {
			throw new IllegalStateException("error at "+i+","+j);
		}
		prefBuilder.setPrefCM(Preference.UNSPECIFIED);
		prefBuilder.setPrefTD(Preference.UNSPECIFIED);
		prefBuilder.setPrefCMTP(Preference.UNSPECIFIED);
		prefBuilder.setPrefTP(Preference.UNSPECIFIED);
		
		prefBuilder.setPrefNbGroupsCM(0);
		prefBuilder.setPrefNbGroupsTD(0);
		prefBuilder.setPrefNbGroupsTP(0);
		prefBuilder.setPrefNbGroupsCMTP(0);

	}
}
