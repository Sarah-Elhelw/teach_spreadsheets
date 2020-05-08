package io.github.oliviercailloux.teach_spreadsheets.gui;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class TableIteambis extends TableItem {

	public TableIteambis(Table parent, int style) {
		super(parent, style);
	}
	
	CoursePref pref;

}
