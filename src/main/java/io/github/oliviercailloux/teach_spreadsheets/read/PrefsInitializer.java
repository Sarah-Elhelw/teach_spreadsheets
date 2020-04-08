package io.github.oliviercailloux.teach_spreadsheets.read;


import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class PrefsInitializer {
	private static int numberOfDocumentsOpened=0;
	private static ArrayList<Course> courseList;
	ImmutableList<String> tableList = ImmutableList.<String>builder() 
            						.add("DE1", "DE2", "L3 Informatique","L3 Mathématiques","M1 Mathématiques","M1 Informatique") 
            						.build();

	public static PrefsInitializer newInstance() {
		return new PrefsInitializer();
	}
	
	private PrefsInitializer() {
		courseList=new ArrayList<>();
	}

	public ImmutableSet<CoursePref> createPrefslist(SpreadsheetDocument document){
		LinkedHashSet<CoursePref> prefsList=new LinkedHashSet<>();
		CourseAndPrefReader reader=CourseAndPrefReader.newInstance();
		Table sheet;
		for(String iteam :tableList){
			sheet=document.getTableByName(iteam);
			while(reader.isThereANextCourse(sheet)) {
			courseList.add(reader.nextCourse(sheet));
			prefsList.add(reader.nextPref(sheet));
			}
		}
		return ImmutableSet.copyOf(prefsList);
	}

}
