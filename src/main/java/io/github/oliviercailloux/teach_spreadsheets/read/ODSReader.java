package io.github.oliviercailloux.teach_spreadsheets.read;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;


/**
 * This class implements SpreadsheetReader : it allows to read a Spreadsheet
 * document, get the value of a cell, check if a cell is a diagonal border or
 * not.
 * 
 * @author Victor CHEN (Kantoki), Louis Fontaine (fontlo15)
 * @version Version : 2.2 Last update : 24/05/2018
 */
public class ODSReader implements SpreadsheetReader, AutoCloseable {

	private SpreadsheetDocument document = null;

	private List<Table> sheetList = null;

	public ODSReader(SpreadsheetDocument spreadsheetDocument) {
		this.document = Objects.requireNonNull(spreadsheetDocument);
		this.sheetList = this.document.getTableList();
	}

	@Override
	public void close() {
		this.document.close();
	}

	public SpreadsheetDocument getDocument() {
		return document;
	}

	@Override
	public void setDocument(InputStream document) throws Exception {
		this.document = Objects.requireNonNull(SpreadsheetDocument.loadDocument(document));
	}

	public Table getTable(String sheet) {
		return this.document.getSheetByName(sheet);
	}

	@Override
	public String getCellValue(String sheet, String cellPosition) {
		Table currentSheet = Objects.requireNonNull(this.document.getSheetByName(sheet));
		Cell cell = currentSheet.getCellByPosition(cellPosition);
		boolean isDiagonalBorder = isDiagonalBorder(sheet, cellPosition);
		if (cell == null) {
			return "";
		}
		if (isDiagonalBorder) {
			return null;
		}
		return cell.getDisplayText();
	}

	public List<Table> getSheetList() {
		return sheetList;
	}

	public void setSheetList(List<Table> sheetList) {
		this.sheetList = sheetList;
	}

	@Override
	public boolean isDiagonalBorder(String sheet, String cellPosition) {
		/*
		 * There is a problem with ODFTookit, their function getBorder return NULL if
		 * the border doesn't exists, but if there is a border, It doesn't return the
		 * description but a NumberFormatException, so the catch fix it
		 */
		Table currentSheet = Objects.requireNonNull(this.document.getSheetByName(sheet));
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

	@Override
	public boolean isDiagonalBorder(String sheet, int columnIndex, int rowIndex) {
		Table currentSheet = Objects.requireNonNull(this.document.getSheetByName(sheet));
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

}
