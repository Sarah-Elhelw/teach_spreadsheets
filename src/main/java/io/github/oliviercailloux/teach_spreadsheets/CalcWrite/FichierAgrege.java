package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import java.util.*; 
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.Course;
import io.github.oliviercailloux.teach_spreadsheets.CoursePref;

import java.io.*; 


import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FichierAgrege {
	
	public static FichierAgrege newInstance() {
		return new FichierAgrege();
	}
	
	public void createAnEmptyOds() { 
		OdfSpreadsheetDocument doc;
		doc = OdfSpreadsheetDocument.newSpreadsheetDocument();
		doc.save("FichierAgrege.ods");
	}
	
	Set <String> headers = new HashSet<>();
	
	headers.add("Year of study");
	headers.add("Semester");
	headers.add("Course name");
	headers.add("Teacher's first name");
	headers.add("Teacher's last name");
	headers.add("Choix Cours");
	headers.add("Choix CMTD");
	headers.add("Choix TD");
	headers.add("Choix CMTP");
	headers.add("Choix TP");
	
	public void headersToOds(<String> headers, OdfSpreadsheetDocument doc) {
		
		int column=1; //by the way, at the end, column = headers.size() + 1
		Iterator<String> i = headers.iterator(); 
        while (i.hasNext()) 
            doc.setValueAt(i.next(), 1, column);
        	column++;
	}
	
	public void completeOds(OdfSpreadsheetDocument doc, <Course> allCourses, <CoursePref> prefs) {
		
		//Iterator <Course> c = allCourses.iterator();
		//Iterator <CoursePref> p = prefs.iterator();
		
		int ligne=2;
		boolean test=false; // this test helps us to see when there is no teachers for a course
		
		for (Course c : allCourses) {
			doc.setValueAt(c.getStudyYear(), ligne, 1);
			doc.setValueAt(c.getSemester(), ligne, 2);
			doc.setValueAt(c.getName(), ligne, 3);
			
			for (CoursePref p : prefs) {
				
				if (c.getName.equals(p.getCourse().getName())) { 
					test = true; 
					doc.setValueAt(p.getTeacher().getFirstName(), ligne, 4);
					doc.setValueAt(p.getTeacher().getLastName(), ligne, 5);
					doc.setValueAt(p.getPrefCM, ligne, 6);
					doc.setValueAt(p.getPrefCMTD, ligne, 7);
					doc.setValueAt(p.getPrefTD, ligne, 8);
					doc.setValueAt(p.getPrefCMTP, ligne, 9);
					doc.setValueAt(p.getPrefTP, ligne, 10);
					
					ligne++;
				}
				
			
			}
			
			if (test==false) {
				ligne++;
			}
			test=false;
		
		}
		
		
	}
}
