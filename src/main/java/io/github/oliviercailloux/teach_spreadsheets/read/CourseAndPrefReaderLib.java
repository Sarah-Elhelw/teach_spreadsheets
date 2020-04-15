package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.teach_spreadsheets.base.Preference;

/**
 * This class gathers basic methods that help reading preferences in an ods file
 * and knowing if there is still a course to read.
 *
 */
class CourseAndPrefReaderLib {
	/**
	 * Tests if there is a next course in the semester to read.
	 * @param sheet - the current sheet
	 * @param currentCol - the current column of the cell to test
	 * @param currentRow - the current row of the cell to test
	 * @return true if there is a next curse to read and false otherwise.
	 */

	static boolean isThereANextCourse(Table sheet, int currentCol, int currentRow) {
		Cell cell;
		cell = sheet.getCellByPosition(currentCol, currentRow);
		String test = cell.getDisplayText();
		return !"".equals(test) && test != null;
	}
	/**
	 * Reads the preference from a cell.
	 * @param sheet - the current sheet
	 * @param j - the cell column containing the preference 
	 * @param i - the cell row containing the preference 
	 * @param flag - used to know if reading the preference is applicable in the context or not
	 * @return the preference 
	 */

	static Preference readPref(Table sheet, int j, int i, boolean flag) {
		if (!flag) {
			return Preference.UNSPECIFIED;
		}
		if (ReaderLib.isDiagonalBorder(sheet, j, i)) {
			return Preference.UNSPECIFIED;
		}
		Cell actualCell = sheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		if (cellText == null || cellText.equals("")) {
			return Preference.UNSPECIFIED;
		}
		String[] choice = cellText.split(" ");
		Preconditions.checkState(choice.length == 2,
				"Preference at " + sheet.getTableName() + " " + i + "," + j + " is not in a valid format");
		if (choice[1].equals("A")) {
			return Preference.A;
		} else if (choice[1].equals("B")) {
			return Preference.B;
		} else if (choice[1].equals("C")) {
			return Preference.C;
		} else {
			return Preference.UNSPECIFIED;
		}
	}
}
