package io.github.oliviercailloux.teach_spreadsheets.write;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

class OdsHelper {

	private Table table;

	public Table getTable() {
		return table;
	}

	private OdsHelper(Table tableGiven) {
		table = tableGiven;
	}

	/**
	 * This is the factory of this class.
	 * 
	 * @param tableGiven - the table of a document that we want to modify
	 * @return a new instance of OdsHelper
	 */
	public static OdsHelper newInstance(Table tableGiven) {
		checkNotNull(tableGiven, "The table should not be null.");
		return new OdsHelper(tableGiven);
	}

	/**
	 * This method creates an empty Ods and removes the default sheet
	 * 
	 * @return a new empty Ods document
	 * @throws IOException if the Ods could not be created
	 */
	public static SpreadsheetDocument createAnEmptyOds() throws IOException {
		try {
			SpreadsheetDocument document = SpreadsheetDocument.newSpreadsheetDocument();
			/** The default sheet should be removed : */
			document.removeSheet(0);
			return document;
		} catch (Exception e) {
			throw new IOException(e);
		}

	}

	/**
	 * This method sets a value at a specific cell of an ods document
	 * 
	 * @param info   - the string that we would like to set at a specific cell
	 * @param row    - the row number
	 * @param column - the column number
	 */
	public void setValueAt(String info, int row, int column) {
		Cell cell = table.getCellByPosition(column, row);
		cell.setDisplayText(info);

	}

}
