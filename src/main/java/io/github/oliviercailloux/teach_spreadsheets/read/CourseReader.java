package io.github.oliviercailloux.teach_spreadsheets.read;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheetMetadata;

/**
 * This class allow you to read a file of courses for Dauphine University
 * teachers from an instance of {@link ODSReader}.
 * 
 * WARNING ! This class only read sheets following the template of "DE1", where
 * the tables of courses starts at cells "B4" and "P4".
 * 
 * @author Victor CHEN (Kantoki), Louis FONTAINE (fontlo15)
 * @version Version 1.0 Last Update : 13/05/2018.
 * 
 */
public class CourseReader implements AutoCloseable {
	private final static String COURSTD = "CMTD";
	private final static String COURSTP = "CMTP";
	private final static String TD = "TD";
	private final static String TP = "TP";
	/**
	 * Assuming that the Begin year and the end are at cells CELLYEAR.
	 */
	private final static String CELLYEAR = "G1";

	/**
	 * Assuming that the tables of courses starts at cells STARTCELLCOURSE1 and
	 * STARTCELLCOURSE2 for each sheet
	 */
	private final static String STARTCELLCOURSE1 = "B4";
	/**
	 * Assuming that the tables of courses starts at cells STARTCELLCOURSE1 and
	 * STARTCELLCOURSE2 for each sheet
	 */
	private final static String STARTCELLCOURSE2 = "P4";

	private final static String CELLCOMPLETEYEAR = "B1";
	private final static String CELLFIRSTSEMESTER = "B2";
	private final static String CELLSTUDENTNUMBER = "I1";
	private final static String CELLYEARBEGIN = "G1";

	private final static Logger LOGGER = LoggerFactory.getLogger(CourseReader.class);

	private ODSReader reader;

	public CourseReader(InputStream source) throws Exception {
		this.reader = new ODSReader(SpreadsheetDocument.loadDocument(source));
	}

	private CourseSheetMetadata readMetadataSheet(Table table) {
		CourseSheetMetadata courseSheetMetadata = new CourseSheetMetadata();
		String tableName = table.getTableName();

		courseSheetMetadata.setYearOfStud(tableName);
		courseSheetMetadata.setCompleteYearOfStudyName(reader.getCellValue(tableName, CELLCOMPLETEYEAR));
		String cellValue = reader.getCellValue(tableName, CELLFIRSTSEMESTER);
		String firstSemesterNumber = cellValue.split(" ")[2]; // We retrieve the semester number from "ENSEIGNEMENT
																// SEMESTRE 1"
		courseSheetMetadata.setFirstSemesterNumber(Integer.parseInt(firstSemesterNumber));
		cellValue = reader.getCellValue(tableName, CELLSTUDENTNUMBER);
		String studentNumber = cellValue.split(" ")[1];
		courseSheetMetadata.setStudentNumber(Integer.parseInt(studentNumber));
		cellValue = reader.getCellValue(tableName, CELLYEARBEGIN);
		String yearBegin = cellValue.split(" ")[1].split("/")[0];
		courseSheetMetadata.setYearBegin(Integer.parseInt(yearBegin));

		return courseSheetMetadata;
	}

	public List<CourseSheet> readCourseSheets() {
		List<CourseSheet> courseSheets = new ArrayList<>();

		List<Table> tables = reader.getSheetList();

		for (Table table : tables) {
			if (!table.getCellByPosition("B3").getDisplayText().equals("Matière")) {
				// if the table corresponds to a course table
				continue;
			}
			CourseSheetMetadata sheetMetadata = this.readMetadataSheet(table);
			List<CoursePref> coursePrefS1 = CoursePref.toCoursePref(setSemesters(
					this.readCoursesFromCell(STARTCELLCOURSE1, table), sheetMetadata.getFirstSemesterNumber()));
			List<CoursePref> coursePrefS2 = CoursePref.toCoursePref(setSemesters(
					this.readCoursesFromCell(STARTCELLCOURSE2, table), sheetMetadata.getFirstSemesterNumber() + 1));

			CourseSheet courseSheet = new CourseSheet(sheetMetadata, coursePrefS1, coursePrefS2);
			courseSheets.add(courseSheet);
		}
		return courseSheets;
	}

	private List<Course> setSemesters(List<Course> courses, int semester) {
		for (Course course : courses) {
			course.setSemester(semester);
		}
		return courses;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by the
	 * {@link ODSReader} in the attribute.
	 * 
	 * This method reads all the Courses from all the sheets of the ODF document
	 *
	 */
	@SuppressWarnings("resource")
	public List<Course> readCourses() {
		List<Course> courses = new ArrayList<>();

		List<Table> tables = reader.getSheetList();

		for (Table table : tables) {
			courses.addAll(this.readCoursesFromSheet(table));
		}

		return courses;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by the
	 * {@link ODSReader} in the attribute and a sheet of the file.
	 * 
	 * This method returns a void list if the sheet has not the correct format
	 * 
	 * @see the template in resources
	 * 
	 * @param sheet:
	 *            the sheet of the spreadsheet document where you want to read the
	 *            courses.
	 * 
	 */
	public List<Course> readCoursesFromSheet(Table actualSheet) {
		List<Course> courses = new ArrayList<>();

		// if the table format is correct
		if (actualSheet.getCellByPosition("B3").getDisplayText().equals("Matière")) {
			courses.addAll(this.readCoursesFromCell(STARTCELLCOURSE1, actualSheet));
			courses.addAll(this.readCoursesFromCell(STARTCELLCOURSE2, actualSheet));
			LOGGER.info("Table " + actualSheet.getTableName() + " courses have been added successfully\n");
		} else {
			LOGGER.info("Table " + actualSheet.getTableName() + " doesn't have courses or the table format is wrong\n");
		}

		return courses;
	}

	/**
	 * This method returns a List of {@link Course} from the ODS file read by the
	 * {@link ODSReader} in the attribute and the cell Position in argument.
	 * 
	 * @param cellPosition:
	 *            the position of the first Cell of the Table where are the
	 *            courses(ex: B2)
	 * 
	 */
	public List<Course> readCoursesFromCell(String cellPosition, Table currentSheet) {
		List<Course> courses = new ArrayList<>();
		String yearOfStudy = currentSheet.getTableName();

		Cell startCell = currentSheet.getCellByPosition(cellPosition);

		int startCellColumnIndex = startCell.getColumnIndex();
		int startCellRowIndex = startCell.getRowIndex();

		Cell actualCell = startCell;
		String cellContent = reader.getCellValue(currentSheet.getTableName(), CELLYEAR);

		Integer yearBegin = Integer.parseInt(cellContent.split(" ")[1].split("/")[0]);

		Course.setYearBegin(yearBegin);
		for (int i = startCellRowIndex; i < currentSheet.getRowCount(); i++) {
			Course course = new Course();
			course.setYearOfStud(yearOfStudy);

			int j = startCellColumnIndex;
			actualCell = currentSheet.getCellByPosition(j, i);
			String cellText = actualCell.getDisplayText();

			if ("".equals(cellText) && j == startCellColumnIndex) {
				break;
			}
			course.setName(cellText.replaceAll("\n", " "));

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			course.setapogeeCode(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			course.setSupervisor(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			course.setTeachers(cellText);

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i) || "".equals(cellText)) {
				course.setCM_Hour(0);
			} else {
				String hourStr = cellText.replaceAll(",", ".");
				String[] hourTab = hourStr.split("h");
				course.setCM_Hour(Double.parseDouble(hourTab[0]));
			}

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i) || "".equals(cellText)) {
				course.setTD_Hour(0);
				course.setCMTD_Hour(0);
			} else {
				String hourStr = cellText.replaceAll(",", ".");
				String[] hourTab = hourStr.split("h");
				if (hourStr.contains(COURSTD)) {
					course.setCMTD_Hour(Double.parseDouble(hourTab[0]));
				} else if (hourStr.contains(TD)) {
					course.setTD_Hour(Double.parseDouble(hourTab[0]));
				}
			}

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i) || "".equals(cellText)) {
				course.setTP_Hour(0);
				course.setCMTP_Hour(0);
			} else {
				String hourStr = cellText.replaceAll(",", ".");
				String[] hourTab = hourStr.split("h");
				if (hourStr.contains(COURSTP)) {
					course.setCMTP_Hour(Double.parseDouble(hourTab[0]));
				} else if (hourStr.contains(TP)) {
					course.setTP_Hour(Double.parseDouble(hourTab[0]));
				}

			}

			j++;
			actualCell = currentSheet.getCellByPosition(j, i);
			cellText = actualCell.getDisplayText();

			if (this.reader.isDiagonalBorder(currentSheet.getTableName(), j, i) || "".equals(cellText)) {
				course.setGrpsNumber("");
			} else {
				course.setGrpsNumber(cellText);
			}
			if (j == startCellColumnIndex) {
				break;
			}
			courses.add(course);

		}

		return courses;

	}

	@Override
	public void close() {
		this.reader.close();
	}

	public ODSReader getReader() {
		return reader;
	}

	public void setReader(ODSReader reader) {
		this.reader = Objects.requireNonNull(reader);
	}

}
