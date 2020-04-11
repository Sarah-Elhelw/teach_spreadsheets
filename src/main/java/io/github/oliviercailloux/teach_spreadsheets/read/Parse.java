package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
/**
 * 
 * if the fiedl
 * @author guedidi hedi
 *
 */
public class Parse {
	private final static String NON_APPLICABLE_STRING="NON_APPLICABLE";
	private final static String EMPTY_STRING="EMPTY";
	
	private final static int NON_APPLICABLE_INT=-1;
	private final static int EMPTY_INT=-2;
	
	private final static char NON_APPLICABLE_CHAR='>';
	private final static char EMPTY_CHAR='<';
	
	
	private static ImmutableList<String> courseTypeList = ImmutableList.<String>builder() 
			.add("CMTD","TD", "TP") 
			.build();
	
	
	private ImmutableMap<String,Integer> readIntInfo(String stringToParse) {
		LinkedHashMap<String,Integer> list = new LinkedHashMap<>();
		Integer value=EMPTY_INT;
		String key;
		char ch;
		for(int i=0;i<stringToParse.length();i++) {
			key="";
			ch=stringToParse.charAt(i);
			if(ch=='h') {
				continue;
			}
			if(Character.isDigit(ch)) {
				value=Integer.parseInt(String.valueOf(ch));
			}
			else if(ch == ' ' || ch == '\n') {
				if(courseTypeList.contains(key)) {
				list.put(key, value);}
				key="";
			}
			else {
				key=key+ch;
			}
		}
		
		return ImmutableMap.copyOf(list);
		
	}
	

}
