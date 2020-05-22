package io.github.oliviercailloux.teach_spreadsheets.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class Model {
	public static Set<CoursePref> allPreferencesData;
	public static Set<CoursePref> chosenPreferencesData;
	
	/**
	 * TODO: calls function of FilesReader to populate allPreferencesData
	 */
	public static void setData() {
		allPreferencesData = new HashSet<>();
		chosenPreferencesData = new HashSet<>();	
	}
	
	/**
	 * TODO: trouver le CoursePref qui correspond à texts, le retirer du Set dans lequel il est puis le mettre dans l'autre
	 * @param texts
	 * @param toChosenPreferences
	 */
	public static void updatePreferences(ArrayList<String> texts, boolean toChosenPreferences) {
		
	}
}
