package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;


public class CalcDataInitializer {

	public static CalcDataInitializer newInstance(){
		return new CalcDataInitializer();	
	}
	
	private CalcDataInitializer() {
		
	}
	
	public CalcData createCalcData(SpreadsheetDocument document){
		TeacherReader teacherReader=TeacherReader.newInstance();
		Teacher teacher=teacherReader.createTeacherFromCalc(document);
		PrefsInitializer prefsInitializer=PrefsInitializer.newInstance();
		ImmutableSet<CoursePref> coursePrefs=prefsInitializer.createPrefslist();
		return CalcData.newInstance(coursePrefs, teacher);
	}
	
	

}
