package io.github.oliviercailloux.teach_spreadsheets.read;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class PrefsInitializer {
	ImmutableList<String> tableList = ImmutableList.<String>builder() 
            						.add("DE1", "DE2", "L3 Informatique","L3 Mathématiques","M1 Mathématiques","M1 Informatique") 
            						.build();

	public static PrefsInitializer newInstance() {
		return new PrefsInitializer();
	}
	
	private PrefsInitializer() {
	}

	public ImmutableSet<CoursePref> createPrefslist() {
		TODO();
		return null;
	}

}
