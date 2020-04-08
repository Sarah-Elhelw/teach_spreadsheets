package io.github.oliviercailloux.teach_spreadsheets.read;

import java.io.InputStream;

/**
 * <b>This interface allows to read data from a spreadsheet.</b>
 * <p>
 * All methods need the currentSheet name.
 * </p>
 * 
 * @author tuannamdavaux, samuelcohen75
 * @see io.github.oliviercailloux.y2018.teach_spreadsheets.odf.SpreadsheetShower
 */
public interface SpreadsheetReader {

	/**
	 * get cell value at cellPosition at sheet
	 */
	public String getCellValue(String sheet, String cellPosition);

	/**
	 * detect if there is a diagonal border in cell at cellPosition at sheet
	 * 
	 * @param cellPosition
	 */
	public boolean isDiagonalBorder(String sheet, String cellPosition);

	/**
	 * detect if there is a diagonal border in cell at j i cellPosition at sheet
	 * 
	 * @param cellPosition
	 */
	public boolean isDiagonalBorder(String sheet, int columnIndex, int rowIndex);

	/**
	 * specify the input file
	 * 
	 * @throws Exception
	 */
	public void setDocument(InputStream document) throws Exception;

	/**
	 * Close the document
	 */
	public void close();
}
