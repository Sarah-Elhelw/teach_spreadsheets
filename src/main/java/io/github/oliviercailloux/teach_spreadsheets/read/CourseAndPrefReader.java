package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * This class reads the courses in a sheet and the preferences of a teacher for
 * these courses. For this class and its methods to work properly, the file read
 * must have the same structure as "AA - Saisie des voeux 2016-2017.ods".
 *
 */
public class CourseAndPrefReader {

	private final static String COURSETD = "CMTD";
	private final static String COURSETP = "CMTP";
	private final static String TD = "TD";
	private final static String TP = "TP";

	private final static int FIRST_COURSE_S1_COL = 1;
	private final static int FIRST_COURSE_S1_ROW = 3;
	private final static int FIRST_COURSE_S2_COL = 15;
	private final static int FIRST_COURSE_S2_ROW = 3;

	private final static String SEMESTER_POSITION = "G1";

	private boolean flagCM;
	private boolean flagTD;
	private boolean flagCMTD;
	private boolean flagTP;
	private boolean flagCMTP;

	int currentCol = FIRST_COURSE_S1_COL;
	int currentRow = FIRST_COURSE_S1_ROW;

	int currentSemester = 1;

	public static CourseAndPrefReader newInstance() {
		return new CourseAndPrefReader();
	}

	private CourseAndPrefReader() {
	}

	/**
	 * Reads and returns the {@link CoursePref} objects corresponding to a semester
	 * of the standard format.
	 * 
	 * @param sheet   - contains the courses and preferences of a specific study
	 *                year.
	 * @param teacher - whose courses'preferences are to read.
	 * @return an ImmutableSet of {@link CoursePref}
	 */
	public ImmutableSet<CoursePref> readSemester(Table sheet, Teacher teacher) {
		Set<CoursePref> coursePrefs = new LinkedHashSet<>();
		while (CourseAndPrefReaderLib.isThereANextCourse(sheet, currentCol, currentRow)) {

			Course.Builder courseBuilder = Course.Builder.newInstance();
			setInfoCourse(sheet, courseBuilder, currentCol, currentRow, currentSemester);
			Course course = courseBuilder.build();

			CoursePref.Builder prefBuilder = CoursePref.Builder.newInstance(course, teacher);
			/** Beware, there are hidden columns in the odsfile. */
			setInfoPref(sheet, prefBuilder, currentCol + 8, currentRow);
			coursePrefs.add(prefBuilder.build());

			currentRow++;
		}
		currentCol = FIRST_COURSE_S2_COL;
		currentRow = FIRST_COURSE_S2_ROW;
		currentSemester = 2;
		ImmutableSet.Builder<CoursePref> coursePrefsBuilder=new ImmutableSet.Builder<>();
		coursePrefsBuilder.addAll(coursePrefs);
		return coursePrefsBuilder.build();

	}

	/**
	 * Sets the informations of a {@link CoursePref} from a table.
	 * 
	 * @param sheet       - the sheet where the preferences are read.
	 * @param prefBuilder - the {@link CoursePref} to be completed
	 * @param j           - the column of the cell containing the first preference
	 *                    cell of the line
	 * @param i           - the row of the cell containing the first preference cell
	 *                    of the line
	 */
	public void setInfoPref(Table sheet, CoursePref.Builder prefBuilder, int j, int i) {
		prefBuilder.setPrefCM(CourseAndPrefReaderLib.readPref(sheet, j, i, flagCM));
		prefBuilder.setPrefTD(CourseAndPrefReaderLib.readPref(sheet, j + 1, i, flagTD));
		prefBuilder.setPrefCMTD(CourseAndPrefReaderLib.readPref(sheet, j + 1, i, flagCMTD));
		prefBuilder.setPrefTP(CourseAndPrefReaderLib.readPref(sheet, j + 2, i, flagTP));
		prefBuilder.setPrefCMTP(CourseAndPrefReaderLib.readPref(sheet, j + 2, i, flagCMTP));

		Cell actualCell = sheet.getCellByPosition(j + 3, i);
		String cellText = actualCell.getDisplayText();
		if (!ReaderLib.isDiagonalBorder(sheet, j, i) && !"".equals(cellText) && cellText != null) {
			String[] cellData = cellText.split(" ");
			int value;
			for (int k = 0; k < cellData.length; k++) {
				if (k < cellData.length - 1 && StringUtils.isNumeric(cellData[k])) {
					value = Integer.parseInt(cellData[k]);
					if (cellData[k + 1].equals("CMTD")) {
						prefBuilder.setPrefNbGroupsCMTD(value);
					}
					if (cellData[k + 1].equals("CMTP")) {
						prefBuilder.setPrefNbGroupsCMTP(value);
					}
					if (cellData[k + 1].equals("TD")) {
						prefBuilder.setPrefNbGroupsTD(value);
					}
					if (cellData[k + 1].equals("TP")) {
						prefBuilder.setPrefNbGroupsTP(value);
					}
				}
			}
		}
	}

	/**
	 * Sets the informations of a {@link Course} from a table. This method is
	 * inspired from <a href=
	 * "https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/java/io/github/oliviercailloux/y2018/teach_spreadsheets/odf/CourseReader.java">readCoursesFromCell</a>
	 * written by Victor CHEN (Kantoki) and Louis FONTAINE (fontlo15). for the
	 * project <a href="https://github.com/oliviercailloux/Teach-spreadsheets">
	 * y2018 teach spreadsheets</a>.
	 * 
	 * @param j - the column of the cell containing the first course cell of the
	 *          line
	 * @param i - the row of the cell containing the first course cell of the line
	 */
	public void setInfoCourse(Table currentSheet, Course.Builder courseBuilder, int currentCol, int currentRow,
			int semester) {
		flagCM = false;
		flagTD = false;
		flagCMTD = false;
		flagTP = false;
		flagCMTP = false;

		int j = currentCol, i = currentRow;
		Cell actualCell = currentSheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		String[] cellData;

		courseBuilder.setSemester(semester);
		courseBuilder.setStudyLevel(currentSheet.getTableName());
		courseBuilder.setName(cellText.replaceAll("\n", " "));

		actualCell = currentSheet.getCellByPosition(SEMESTER_POSITION);
		cellData = actualCell.getDisplayText().split(" ");
		if (cellData.length != 2) {
			throw new IllegalStateException("The semester cell isn't in the right format");
		}
		
		String studyYear = cellData[1];
		checkNotNull(studyYear);
		checkState(studyYear.indexOf('/') != -1);
		courseBuilder.setStudyYear(Integer.parseInt(studyYear.substring(0,studyYear.indexOf('/'))));

		j += 4;

		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (ReaderLib.isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbMinutesCM(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			courseBuilder.setNbMinutesCM(ReaderLib.hoursToMinutes(hourTab[0]));
			courseBuilder.setCountGroupsCM(ReaderLib.hoursToMinutes(hourTab[0]));
			flagCM = true;
		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (ReaderLib.isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbMinutesTD(0);
			courseBuilder.setNbMinutesCMTD(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			if (hourStr.contains(COURSETD)) {
				courseBuilder.setNbMinutesCMTD(ReaderLib.hoursToMinutes(hourTab[0]));
				flagCMTD = true;
			} else if (hourStr.contains(TD)) {
				courseBuilder.setNbMinutesTD(ReaderLib.hoursToMinutes(hourTab[0]));
				flagTD = true;
			}
		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		if (ReaderLib.isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setNbMinutesTP(0);
			courseBuilder.setCountGroupsCMTP(0);
		} else {
			String hourStr = cellText.replaceAll(",", ".");
			String[] hourTab = hourStr.split("h");
			if (hourStr.contains(COURSETP)) {
				courseBuilder.setNbMinutesCMTP(ReaderLib.hoursToMinutes(hourTab[0]));
				flagCMTP = true;
			} else if (hourStr.contains(TP)) {
				courseBuilder.setNbMinutesTP(ReaderLib.hoursToMinutes(hourTab[0]));
				flagTP = true;
			}

		}

		j++;
		actualCell = currentSheet.getCellByPosition(j, i);
		cellText = actualCell.getDisplayText();

		courseBuilder.setCountGroupsCM(1);

		if (ReaderLib.isDiagonalBorder(currentSheet, j, i) || "".equals(cellText)) {
			courseBuilder.setCountGroupsCMTD(0);
			courseBuilder.setCountGroupsCMTP(0);
			courseBuilder.setCountGroupsTD(0);
			courseBuilder.setCountGroupsTP(0);

		} else {
			cellData = cellText.split(" ");
			int value;
			for (int k = 0; k < cellData.length; k++) {
				if (k < cellData.length - 1 && StringUtils.isNumeric(cellData[k])) {
					value = Integer.parseInt(cellData[k]);
					if (cellData[k + 1].equals("CMTD")) {
						courseBuilder.setCountGroupsCMTD(value);
					}
					if (cellData[k + 1].equals("CMTP")) {
						courseBuilder.setCountGroupsCMTP(value);
					}
					if (cellData[k + 1].equals("TD")) {
						courseBuilder.setCountGroupsTD(value);
					}
					if (cellData[k + 1].equals("TP")) {
						courseBuilder.setCountGroupsTD(value);
					}
				}
			}

		}
	}
}