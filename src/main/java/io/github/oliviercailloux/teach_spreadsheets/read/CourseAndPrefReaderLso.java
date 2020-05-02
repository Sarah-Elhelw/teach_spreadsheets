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

/**
 * This class reads the courses in a LSO sheet and the preferences of a teacher for
 * these courses. 
 *
 */
public class CourseAndPrefReaderLso {
	private final static int FIRST_COURSE_S1_COL_LSO = 0;
	private final static int FIRST_COURSE_S1_ROW_LSO = 3;
	
	int currentCol = FIRST_COURSE_S1_COL_LSO;
	int currentRow = FIRST_COURSE_S1_ROW_LSO;
	
	Set<Course> courses;
	public static CourseAndPrefReaderLso newInstance() {
		return new CourseAndPrefReaderLso();
	}
	
	private CourseAndPrefReaderLso() {
		courses = new LinkedHashSet<>();	
	}
	/**
	 * Reads and returns the {@link CoursePref} objects corresponding to the information on the LSO sheet
	 * 
	 * @param sheet   - contains the courses and preferences of a specific study
	 *                year.
	 * @param teacher - whose courses'preferences are to read.
	 * @return an ImmutableSet of {@link CoursePref}
	 */
	public ImmutableSet<CoursePref> readLso(Table sheet, Teacher teacher) {
		Set<CoursePref> coursePrefs=new LinkedHashSet<>();
		while(!testSemester2(sheet,currentCol,currentRow)){
			setInfo(sheet, teacher,coursePrefs,1);
		}
		currentRow+=2;
		while (CourseAndPrefReaderLib.isThereANextCourse(sheet, currentCol, currentRow)) {
			setInfo(sheet, teacher,coursePrefs,2);
		}
		return ImmutableSet.copyOf(coursePrefs);
	}
	/**
	 * Test whether the a Cell contain the String "semestre2" 
	 */
	private boolean testSemester2(Table sheet,int col,int row) {
		Cell actualCell = sheet.getCellByPosition(col, row);
		String cellText = actualCell.getDisplayText();
		cellText=cellText.trim();
		return cellText.equals("semestre 2");
		
	}
	/**
	 *  Sets information from a LSO sheet.
	 */
	private void setInfo(Table sheet, Teacher teacher,Set<CoursePref> coursePrefList,int semester) {
		Course.Builder courseBuilder = Course.Builder.newInstance();
		setInfoCourseLso(sheet, courseBuilder, currentCol, currentRow,semester);
		Course course = courseBuilder.build();
		courses.add(course);
		CoursePref.Builder prefBuilder = CoursePref.Builder.newInstance(course, teacher);
		setInfoPrefLso(sheet, prefBuilder, currentCol + 4, currentRow);
		coursePrefList.add(prefBuilder.build());
		currentRow++;
	}
	/**
	 * Sets the course information from a LSO sheet.
	 */
	public void setInfoCourseLso(Table currentSheet, Course.Builder courseBuilder, int currentCol, int currentRow,
			int semester) {
		int j = currentCol, i = currentRow;
		String[] cellData;
		Cell actualCell = currentSheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		courseBuilder.setSemester(semester);
		courseBuilder.setName(cellText.replaceAll("\n", " "));
		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();
		courseBuilder.setStudyYear(cellText.replaceAll("\n", " "));
		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();
		cellText=cellText.trim();
		cellData = cellText.split(" ");
		if(cellData.length==2 && StringUtils.isNumeric(cellData[0])) {
			courseBuilder.setnbMinutesTD(ReaderLib.hoursToMinutes(cellData[0]));
			
			courseBuilder.setnbMinutesCM(0);
			courseBuilder.setnbMinutesCMTD(0);
			courseBuilder.setnbMinutesTP(0);
			courseBuilder.setnbMinutesCMTP(0);
		}
		else {
			throw new IllegalStateException("The lso tab has at least one value in the TD column that isn't in the right format at "+i+","+j);
		}
		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();
		if(StringUtils.isNumeric(cellText)) {
			courseBuilder.setCountGroupsTD(Integer.parseInt(cellText));
			
			courseBuilder.setCountGroupsCM(0);
			courseBuilder.setCountGroupsCMTD(0);
			courseBuilder.setCountGroupsTP(0);
			courseBuilder.setCountGroupsCMTP(0);
		}
		else {
			throw new IllegalStateException("The lso tab has at least one value in the 'Nombre de groupe' column that isn't in the right format at "+i+","+j);
		}
		
	}
	/**
	 * Sets the preference information from a LSO sheet.
	 */
	public void setInfoPrefLso(Table sheet, CoursePref.Builder prefBuilder, int j, int i) {
		Cell actualCell = sheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		cellText=cellText.trim();
		prefBuilder.setPrefCM(Preference.UNSPECIFIED);
		prefBuilder.setPrefCMTD(Preference.UNSPECIFIED);
		prefBuilder.setPrefCMTP(Preference.UNSPECIFIED);
		prefBuilder.setPrefTP(Preference.UNSPECIFIED);
		
		prefBuilder.setPrefNbGroupsCM(0);
		prefBuilder.setPrefNbGroupsCMTD(0);
		prefBuilder.setPrefNbGroupsTP(0);
		prefBuilder.setPrefNbGroupsCMTD(0);
		if(StringUtils.isNumeric(cellText)) {
			prefBuilder.setPrefNbGroupsTD(Integer.parseInt(cellText));
			/** As there is no case to choose the preference in the LSO tab, we assume it is A by default */
			prefBuilder.setPrefTD(Preference.A);
		}
		else if(cellText.equals("")) {
			prefBuilder.setPrefNbGroupsTD(0);
			prefBuilder.setPrefTD(Preference.UNSPECIFIED);
		}
		else {
			throw new IllegalStateException("The lso tab has at least one value in the 'Nombre de groupes souhaités' column that isn't in the right format at "+i+","+j);
		}
	}

}
