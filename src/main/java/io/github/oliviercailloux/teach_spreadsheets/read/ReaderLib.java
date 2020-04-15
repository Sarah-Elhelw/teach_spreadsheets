package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

/**
 * This class gathers basic methods that help reading an ods file. These methods
 * were coded by two members of the former group that had to work on teach
 * spreadsheets subject : Victor CHEN (Kantoki) and Louis Fontaine (fontlo15).
 * The original class these methods are extracted from is ODSReader that can be
 * found in
 * {@link https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/java/io/github/oliviercailloux/y2018/teach_spreadsheets/odf/ODSReader.java}.
 *
 */
class ReaderLib {

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
	 * current sheet.
	 *
	 */
	static boolean isDiagonalBorder(Table currentSheet, String cellPosition) {
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

	/**
	 * Detects if there is a diagonal border in the cell at j i position in the
	 * current sheet.
	 * 
	 */
	static boolean isDiagonalBorder(Table currentSheet, int columnIndex, int rowIndex) {
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

	static int hoursToMinutes(String hours) {
		return (int) (Double.parseDouble(hours) * 60);
	}

}
