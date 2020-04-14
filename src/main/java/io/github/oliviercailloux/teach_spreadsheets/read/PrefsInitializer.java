package io.github.oliviercailloux.teach_spreadsheets.read;


import java.util.LinkedHashSet;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class PrefsInitializer {
	private static ImmutableList<String> tableList = ImmutableList.<String>builder() 
            						.add("DE1", "DE2", "L3 Informatique","L3 Mathématiques","M1 Mathématiques","M1 Informatique") 
            						.build();

	public static PrefsInitializer newInstance() {
		return new PrefsInitializer();
	}
	
	private PrefsInitializer() {
	}

	public ImmutableSet<CoursePref> createPrefslist(SpreadsheetDocument document, Teacher teacher){
		LinkedHashSet<CoursePref> prefsList=new LinkedHashSet<>();
		Table sheet;
		for(String iteam :tableList){
			CourseAndPrefReader reader=CourseAndPrefReader.newInstance();
			sheet=document.getTableByName(iteam);
			prefsList.addAll(reader.readSemester(sheet,teacher));
			prefsList.addAll(reader.readSemester(sheet,teacher));
		}
		return ImmutableSet.copyOf(prefsList);
	}

}
