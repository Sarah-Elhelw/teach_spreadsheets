package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

/**
 * This class gathers basic methods that help reading an ods file.Some of These
 * methods were coded by two members of the former group that had to work on
 * <a href="https://github.com/oliviercailloux/Teach-spreadsheets">teach
 * spreadsheets</a> subject : Victor CHEN (Kantoki) and Louis Fontaine
 * (fontlo15). The original class these methods are extracted from is <a href=
 * "https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/java/io/github/oliviercailloux/y2018/teach_spreadsheets/odf/ODSReader.java">ODSReader</a>.
 *
 */
class ReaderLib {
	/**
	 * Displays the content of a cell.
	 * 
	 * @param currentSheet - the current sheet
	 * @param cellPosition - the position of the cell we want to get the content
	 *                     from.
	 * @return the cell value as a String, null if the cell contains a diagonal
	 *         border see :
	 *         {@link #isDiagonalBorder(Table currentSheet, String cellPosition)
	 *         isDiagonalBorder}.
	 */

	static String getCellValue(Table currentSheet, String cellPosition) {
		Cell cell = currentSheet.getCellByPosition(cellPosition);
		boolean isDiagonalBorder = isDiagonalBorder(currentSheet, cellPosition);
		if (cell == null) {
			return "";
		}
		if (isDiagonalBorder) {
			return null;
		}
		return cell.getDisplayText();
	}
	

	/**
	 * Detects if there is a diagonal border in the cell at cellPosition in the
	 * current sheet. This method is written by Victor CHEN (Kantoki) and Louis
	 * FONTAINE (fontlo15) for the project
	 * <a href="https://github.com/oliviercailloux/Teach-spreadsheets"> y2018 teach
	 * spreadsheets</a>.
	 * 
	 * @param currentSheet - the current sheet
	 * @param cellPosition - the position of the cell where we would like to know if
	 *                     there is a border or not.
	 */
	static boolean isDiagonalBorder(Table currentSheet, String cellPosition) {
		/*
		 * There is a problem with ODFTookit, their function getBorder return NULL if
		 * the border doesn't exists, but if there is a border, It doesn't return the
		 * description but a NumberFormatException, so the catch fix it
		 */
		Cell cell = currentSheet.getCellByPosition(cellPosition);
		return verifyBorder(cell);
	}

	

	/**
	 * Detects if there is a diagonal border in the cell at j i position in the
	 * current sheet. This method is written by Victor CHEN (Kantoki) and Louis
	 * FONTAINE (fontlo15) for the project
	 * <a href="https://github.com/oliviercailloux/Teach-spreadsheets">
	 * y2018.teach.Sspreadsheets</a>. It can be found in the class <a href=
	 * "https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/java/io/github/oliviercailloux/y2018/teach_spreadsheets/odf/ODSReader.java">ODSReader</a>
	 * of this project.
	 * 
	 * @param currentSheet - the current sheet
	 * @param columnIndex  - the cell column
	 * @param rowIndex     - the cell row
	 */
	static boolean isDiagonalBorder(Table currentSheet, int columnIndex, int rowIndex) {
		Cell cell = currentSheet.getCellByPosition(columnIndex, rowIndex);
		return verifyBorder(cell);
	}
	
	private static boolean verifyBorder(Cell cell) {
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

	/**
	 * Converts a String containing a number of hours to minutes.
	 * 
	 * @param hours a String containing only a real number
	 * @throws NullPointerException  if the string is null
	 * @throws NumberFormatException if the string does not contain a double that
	 *                               can be parsed.
	 * @return the equivalent number of minutes
	 * 
	 */

	static int hoursToMinutes(String hours) {
		return (int) (Double.parseDouble(hours) * 60);
	}

	public static void main(String[] args) {

	Logger logger = Logger.getLogger("logger");

	logger.log(Level.INFO, "à complter");
	}
}
