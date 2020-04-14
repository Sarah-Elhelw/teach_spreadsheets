package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.Preference;

class CourseAndPrefReaderLib {
	static boolean isThereANextCourse(Table sheet,int currentCol,int currentRow) {
		Cell cell;
		cell=sheet.getCellByPosition(currentCol,currentRow);
		String test=cell.getDisplayText();
		return !"".equals(test) && test!=null;
	}
	static Preference readPref(Table sheet, int j, int i, boolean flag) {
		if(!flag) {
			return Preference.UNSPECIFIED;
		}
		if (ReaderLib.isDiagonalBorder(sheet, j, i)) {
			return Preference.UNSPECIFIED;
		}
		Cell actualCell = sheet.getCellByPosition(j, i);
		String cellText = actualCell.getDisplayText();
		if(cellText==null || cellText.equals("")) {
			return Preference.UNSPECIFIED;
		}
		String[] choice = cellText.split(" ");
		if(choice.length !=2) {
			throw new IllegalStateException("Preference at "+sheet.getTableName()+" "+i+","+j+" is not in a valid format");
		}
		if(choice[1].equals("A")){
			return Preference.A;
		}
		else if(choice[1].equals("B")){
			return Preference.B;
		}
		else if(choice[1].equals("C")){
			return Preference.C;
		}
		else {
			return Preference.UNSPECIFIED;
		}
	}
}
