package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import java.io.FileOutputStream;
import java.io.IOException;

//import org.apache.poi.xwpf.usermodel.Document;
//import org.odftoolkit.simple.SpreadsheetDocument;
//import org.odftoolkit.simple.text.Paragraph;
// import com.lowagie.text.pdf.PdfWriter;


public class OdsHelper {
	// This class implements the basic methods we need for
	// TeahcersPreferencesAndAssigment and AssigmentPerTeacher classes

	/**
	 * This method creates an empty Ods and removes the default sheet
	 * 
	 * @return a new Ods empty document
	 * @throws Throwable if the Ods could not be created
	 */

	protected static SpreadsheetDocument createAnEmptyOds() throws Exception {
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
//		Document document = new Document(PageSize.A4);
//	    try {
//	      PdfWriter.getInstance(doc,
//	          new FileOutputStream("c:/test.pdf"));
//	      doc.open();
//	      doc.add(new Paragraph("Hello World"));
//	    } catch (DocumentException de) {
//	      de.printStackTrace();
//	    } catch (IOException ioe) {
//	      ioe.printStackTrace();
//	    }

//	    doc.close();
	}

}
