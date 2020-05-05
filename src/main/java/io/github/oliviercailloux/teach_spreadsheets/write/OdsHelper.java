package io.github.oliviercailloux.teach_spreadsheets.write;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;



public class OdsHelper {

	// This class implements the basic methods we need for
	// TeahcersPreferencesAndAssigment and AssigmentPerTeacher classes

	/**
	 * This method creates an empty Ods and removes the default sheet
	 * 
	 * @return a new Ods empty document
	 * @throws Exception if the Ods could not be created
	 */

	protected static SpreadsheetDocument createAnEmptyOds() throws Exception { // we are thinking about other exception
																				// more precise to throw ...
		SpreadsheetDocument document = SpreadsheetDocument.newSpreadsheetDocument();
		document.removeSheet(0);// remove the default sheet
		return document;
	}

	/**
	 * This method sets a value at a specific cell of an ods document
	 * 
	 * @param table  This is the main table of the ods document that we want to
	 *               complete
	 * @param info   This is the string that we would like to set at a specific cell
	 * @param row    The value is set at this specific row number
	 * @param column The value is set at this specific column number
	 */

	protected static void setValueAt(Table table, String info, int row, int column) {
		Cell cell = table.getCellByPosition(column, row);
		cell.setDisplayText(info);

	}

	/**
	 * This method convert and ods file to pdf
	 * 
	 * @param doc
	 * @return a pdf doc
	 */
	protected static void odsToPdf(SpreadsheetDocument doc) {
	}

}
