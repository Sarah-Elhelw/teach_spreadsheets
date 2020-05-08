package io.github.oliviercailloux.teach_spreadsheets.gui;

import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.swt.widgets.TableItem;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;
import io.github.oliviercailloux.teach_spreadsheets.assignment.TeacherAssignment;
import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;

public class GuiLib {
	static Integer nextCourseprefId=1;
	
	public static ImmutableSet<String[]> extractPreferenceItems(ImmutableSet<CalcData> calcs,Map<Integer,CoursePref> mapPreference) {
		LinkedHashSet<String[]> result= new LinkedHashSet<>();
		int i=0;
		for(CalcData calc : calcs) {
			for(CoursePref coursePref : calc.getCoursePrefs()) {
			i++;
			if(!coursePref.getPrefCM().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem("CM",coursePref.getPrefCM(),coursePref));
				mapPreference.put(i, coursePref);
			}
			if(!coursePref.getPrefTD().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem("TD",coursePref.getPrefTD(),coursePref));
				mapPreference.put(i, coursePref);
			}
			if(!coursePref.getPrefCMTD().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem("CMTD",coursePref.getPrefCMTD(),coursePref));
				mapPreference.put(i, coursePref);
			}
			if(!coursePref.getPrefTP().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem("TP",coursePref.getPrefTP(),coursePref));
				mapPreference.put(i, coursePref);
			}
			if(!coursePref.getPrefCMTP().equals(Preference.UNSPECIFIED)) {
				result.add(convertCoursePrefToItem("CMTP",coursePref.getPrefCMTP(),coursePref));
				mapPreference.put(i, coursePref);
			}
			}
		}
		return ImmutableSet.copyOf(result);
	}

	private static String[] convertCoursePrefToItem(String courseType,Preference preference, CoursePref coursePref) {
		String[] temp = new String[5];
		temp[0]=nextCourseprefId.toString();
		temp[1]=coursePref.getTeacher().getFirstName()+" "+coursePref.getTeacher().getLastName();
		temp[2]=coursePref.getCourse().getName();
		temp[3]=courseType;
		temp[4]=preference.toString();
		nextCourseprefId++;
		return temp;
	}
	
	public static ImmutableSet<CourseAssignment> createAssignments(ImmutableSet<TableItem> tableItems,ImmutableMap<Integer,CoursePref> mapPreference) {
		LinkedHashSet<CourseAssignment> result= new LinkedHashSet<>();
		for(TableItem tableItem : tableItems) {
		TeacherAssignment.Builder assignmentBuilder=TeacherAssignment.Builder.newInstance(mapPreference.get(Integer.parseInt(tableItem.getText(0))).getTeacher());
	}
		return ImmutableSet.copyOf(result);

}

	private static boolean exists(LinkedHashSet<CourseAssignment> result, String text) {

		
		return false;
	}
}
