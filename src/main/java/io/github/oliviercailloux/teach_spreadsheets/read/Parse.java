package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.table.Cell;

import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
/**
 * 
 * if the fiedl
 * @author guedidi hedi
 *
 */
public class Parse {
	private final static String NON_APPLICABLE_STRING="NON_APPLICABLE";
	private final static String EMPTY_STRING="EMPTY";
	
	private final static int NON_APPLICABLE_INT=-1;
	private final static int EMPTY_INT=-2;
	
	private final static char NON_APPLICABLE_CHAR='>';
	private final static char EMPTY_CHAR='<';
	
	public static int parseCountGroupsCM(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseCountGroupsCMTP(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseCountGroupsCMTD(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseCountGroupsTD(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseNbHoursCM(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseNbHoursCMTD(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseNbHoursCMTP(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseNbHoursTD(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseNbHoursTP(Cell cell) {
		TODO();
		return 0;
	}

	public static int parseSemester(Cell cell) {
		TODO();
		return 0;
	}

	public static String parseStudyYear(Cell cell) {
		TODO();
		return null;
	}

	public static String parseName(Cell cell) {
		return cell.getDisplayText();
	}


	public static Preference parsePrefChar(Cell cell) {
		TODO();
		return null;
	}

	public static int parsePrefInt(Cell cell) {
		TODO();
		return 0;
	}
}
