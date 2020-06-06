package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment.Builder;
import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.read.PrefsInitializer;

public class Controller {
	
	private static View gui;

	public static ImmutableSet<TeacherAssignment> createAssignments(ImmutableSet<TableItem> tableItems,
			ImmutableMap<TableItem, CoursePref> mapPreference) {
		LinkedHashMap<String, TeacherAssignment.Builder> mapTeacherBuilder = new LinkedHashMap<>();
		for (TableItem tableItem : tableItems) {
			if (!mapTeacherBuilder.containsKey(tableItem.getText(0))) {
				TeacherAssignment.Builder assignmentBuilder = TeacherAssignment.Builder
						.newInstance(mapPreference.get(tableItem).getTeacher());
				assignmentBuilder.setCountGroupsCM(0);
				assignmentBuilder.setCountGroupsCMTD(0);
				assignmentBuilder.setCountGroupsCMTP(0);
				assignmentBuilder.setCountGroupsTD(0);
				assignmentBuilder.setCountGroupsTP(0);
				assignToCourseType(assignmentBuilder, tableItem.getText(2));
				// assignmentBuilder.setCourse(mapPreference.get(tableItem).getCourse());
			} else {
				TeacherAssignment.Builder assignmentBuilder = mapTeacherBuilder.get(tableItem.getText(0));
				assignToCourseType(assignmentBuilder, tableItem.getText(2));
				// assignmentBuilder.setCourse(mapPreference.get(tableItem).getCourse());
			}

		}
		LinkedHashSet<TeacherAssignment> result = new LinkedHashSet<>();
		for (TeacherAssignment.Builder builder : mapTeacherBuilder.values()) {
			result.add(builder.build());
		}
		return ImmutableSet.copyOf(result);

	}
	
	/**
	 * regarder à quel objet dans Model correspond le TableItem:
	 * enlever cet objet puis le mettre dans l'autre liste de Model,
	 * puis 
	 * @param item
	 * @param toChosenPreferences
	 */
	public static void callbackListener(TableItem item, boolean toChosenPreferences) {
		int i = 0;
		ArrayList<String> texts = new ArrayList<>();
		while (!item.getText(i).equals("")) {
			texts.add(item.getText(i));
			i += 1;
		}
		Model.updatePreferences(texts, toChosenPreferences);
		gui.moveTableItem(item, texts, toChosenPreferences);;
	}

	// ajouter la fonctionalité pour incrementer la valeur du nb de groupe affectés
	private static void assignToCourseType(TeacherAssignment.Builder assignmentBuilder, String choiceGroup) {
		if (choiceGroup.equals("CM")) {
			// increment countGroupCM
		}
		if (choiceGroup.equals("CMTD")) {
			// increment countGroupCMTD
		}
		if (choiceGroup.equals("TD")) {
			// increment countGroupTD
		}
		if (choiceGroup.equals("CMTP")) {
			// increment countGroupCMTP
		}
		if (choiceGroup.equals("TP")) {
			// increment countGroupTP
		}

	}
	
	public static void setModelData() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			CalcData calcData = CalcData.getData(stream);
			Model.setData(calcData);
		}
	}
	
	public static void main(String[] args) throws Exception {
		gui = new View();
		gui.initializeGui();
		setModelData();
		Set<CoursePrefElement> allPreferences = Model.getAllPreferences();
		gui.initPreferences(allPreferences);
		gui.show();
	}

}
