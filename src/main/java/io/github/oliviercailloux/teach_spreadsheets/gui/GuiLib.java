package io.github.oliviercailloux.teach_spreadsheets.gui;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.swt.SWT;
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

public class GuiLib {
	
	public static ImmutableSet<TableItem> extractPreferenceItems(Table t,ImmutableSet<CalcData> calcs,Map<TableItem,CoursePref> mapPreference) {
		checkNotNull(mapPreference);
		LinkedHashSet<TableItem> result= new LinkedHashSet<>();
		for(CalcData calc : calcs) {
			for(CoursePref coursePref : calc.getCoursePrefs()) {
			if(!coursePref.getPrefCM().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem(t,"CM",coursePref.getPrefCM(),coursePref,mapPreference));
				
			}
			if(!coursePref.getPrefTD().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem(t,"TD",coursePref.getPrefTD(),coursePref,mapPreference));
	
			}
			if(!coursePref.getPrefCMTD().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem(t,"CMTD",coursePref.getPrefCMTD(),coursePref,mapPreference));
			}
			if(!coursePref.getPrefTP().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem(t,"TP",coursePref.getPrefTP(),coursePref,mapPreference));
			}
			if(!coursePref.getPrefCMTP().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem(t,"CMTP",coursePref.getPrefCMTP(),coursePref,mapPreference));
	
			}
			}
		}
		return ImmutableSet.copyOf(result);
	}

	private static TableItem convertCoursePrefToItem(Table t,String courseType,Preference preference, CoursePref coursePref,Map<TableItem,CoursePref> mapPreference) {
		String[] temp = new String[4];
		temp[0]=coursePref.getTeacher().getFirstName()+" "+coursePref.getTeacher().getLastName();
		temp[1]=coursePref.getCourse().getName();
		temp[2]=courseType;
		temp[3]=preference.toString();
		TableItem item = new TableItem(t, SWT.NONE);
		item.setText(temp);
		mapPreference.put(item, coursePref);
		return item;
	}
	
	public static ImmutableSet<TeacherAssignment> createAssignments(ImmutableSet<TableItem> tableItems,ImmutableMap<TableItem,CoursePref> mapPreference) {
		LinkedHashMap<String,TeacherAssignment.Builder> mapTeacherBuilder=new LinkedHashMap<>();
		for(TableItem tableItem : tableItems) {
		if(!mapTeacherBuilder.containsKey(tableItem.getText(0))) {
		TeacherAssignment.Builder assignmentBuilder=TeacherAssignment.Builder.newInstance(mapPreference.get(tableItem).getTeacher());
		assignmentBuilder.setCountGroupsCM(0);
		assignmentBuilder.setCountGroupsCMTD(0);
		assignmentBuilder.setCountGroupsCMTP(0);
		assignmentBuilder.setCountGroupsTD(0);
		assignmentBuilder.setCountGroupsTP(0);
		assignToCourseType(assignmentBuilder,tableItem.getText(2));
		//assignmentBuilder.setCourse(mapPreference.get(tableItem).getCourse());
		}
		else {
			TeacherAssignment.Builder assignmentBuilder=mapTeacherBuilder.get(tableItem.getText(0));
			assignToCourseType(assignmentBuilder,tableItem.getText(2));
			//assignmentBuilder.setCourse(mapPreference.get(tableItem).getCourse());
		}
			
	}
		LinkedHashSet<TeacherAssignment> result= new LinkedHashSet<>();
		for(TeacherAssignment.Builder builder : mapTeacherBuilder.values()) {
			result.add(builder.build());
		}
		return ImmutableSet.copyOf(result);

}
	// ajouter la fonctionalité pour incrementer la valeur du nb de groupe affectés
	private static void assignToCourseType(TeacherAssignment.Builder assignmentBuilder, String choiceGroup) {
		if(choiceGroup.equals("CM")) {
			//increment countGroupCM
		}
		if(choiceGroup.equals("CMTD")) {
			//increment countGroupCMTD
		}
		if(choiceGroup.equals("TD")) {
			//increment countGroupTD
		}
		if(choiceGroup.equals("CMTP")) {
			//increment countGroupCMTP
		}
		if(choiceGroup.equals("TP")) {
			//increment countGroupTP
		}
		
	}


}
