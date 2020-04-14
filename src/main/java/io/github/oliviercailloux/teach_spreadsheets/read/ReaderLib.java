package io.github.oliviercailloux.teach_spreadsheets.read;


import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

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
	 * detect if there is a diagonal border in cell at j i cellPosition at sheet
	 * This function was taken from file Class ODSReader
	 * @param cellPosition
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
	 * detect if there is a diagonal border in cell at cellPosition at sheet
	 * This function was taken from 
	 * @param cellPosition
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
	static int hoursToMinutes(String hours){
		return (int)(Double.parseDouble(hours)*60); 	
	}

}
