package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import java.util.*; 
import java.io.*; 
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.Course;
import io.github.oliviercailloux.teach_spreadsheets.CoursePref;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FichierAgrege {
	
	private static FichierAgrege newInstance() {
		return new FichierAgrege();
	}
	
	public void createAnEmptyOds() { 
		SpreadsheetDocument document;
		document = SpreadsheetDocument.newSpreadsheetDocument();
		document.save("FichierAgrege.ods");
	}
	
	private SpreadsheetDocument doc = document;
	
	private final static String YEAR_POSITION = "A1";
	private final static String SEMESTER_POSITION = "B1";
	private final static String COURSE_POSITION = "C1";
	private final static String TEACHER_FIRST_NAME_POSITION = "D1";
	private final static String TEACHER_LAST_NAME_POSITION = "E1";
	private final static String CM_POSITION = "F1";
	private final static String CMTD_POSITION = "G1";
	private final static String TD_POSITION = "H1";
	private final static String CMTP_POSITION = "I1";
	private final static String TP_POSITION = "J1";
	
	
	public void headersToOds(SpreadsheetDocument doc){
		
		doc.getCellByPosition(YEAR_POSITION).setStringValue("Year of study");
		doc.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");
		doc.getCellByPosition(COURSE_POSITION).setStringValue("Course name");
		doc.getCellByPosition(TEACHER_FIRST_NAME_POSITION).setStringValue("Teacher's first name");
		doc.getCellByPosition(TEACHER_LAST_NAME_POSITION).setStringValue("Teacher's last name");
		doc.getCellByPosition(CM_POSITION).setStringValue("Choix Cours");
		doc.getCellByPosition(CMTD_POSITION).setStringValue("Choix CMTD");
		doc.getCellByPosition(TD_POSITION).setStringValue("Choix TD");
		doc.getCellByPosition(CMTP_POSITION).setStringValue("Choix CMTP");
		doc.getCellByPosition(TP_POSITION).setStringValue("Choix TP");
		
	}
	
	public void completeOds(SpreadsheetDocument doc, HashSet <Course> allCourses, HashSet <CoursePref> prefs) {
		
		//Iterator <Course> c = allCourses.iterator();
		//Iterator <CoursePref> p = prefs.iterator();
		
		int ligne=1;
		boolean test=false; // this test helps us to see when there is no teachers for a course
		
		for (Course c : allCourses) {
			doc.setValueAt(c.getStudyYear(), ligne, 0);
			doc.setValueAt(c.getSemester(), ligne, 1);
			doc.setValueAt(c.getName(), ligne, 2);
			
			for (CoursePref p : prefs) {
				
				if (c.getName().equals(p.getCourse().getName())) { 
					test = true; 
					doc.setValueAt(p.getTeacher().getFirstName(), ligne, 3);
					doc.setValueAt(p.getTeacher().getLastName(), ligne, 4);
					doc.setValueAt(p.getPrefCM(), ligne, 5);
					doc.setValueAt(p.getPrefCMTD(), ligne, 6);
					doc.setValueAt(p.getPrefTD(), ligne, 7);
					doc.setValueAt(p.getPrefCMTP(), ligne, 8);
					doc.setValueAt(p.getPrefTP(), ligne, 9);
					
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
