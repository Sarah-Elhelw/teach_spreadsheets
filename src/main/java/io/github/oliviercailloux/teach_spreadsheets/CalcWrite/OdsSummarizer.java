package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class OdsSummarizer {

	
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
	
	
	private static SpreadsheetDocument createAnEmptyOds() throws Throwable { 
		SpreadsheetDocument document = SpreadsheetDocument.newSpreadsheetDocument();
		document.removeSheet(0);// remove the default sheet
		return document;
	}
	
	
	private static void headersToOds(Table table){
		
		table.getCellByPosition(YEAR_POSITION).setStringValue("Year of study");
		table.getCellByPosition(SEMESTER_POSITION).setStringValue("Semester");
		table.getCellByPosition(COURSE_POSITION).setStringValue("Course name");
		table.getCellByPosition(TEACHER_FIRST_NAME_POSITION).setStringValue("Teacher's first name");
		table.getCellByPosition(TEACHER_LAST_NAME_POSITION).setStringValue("Teacher's last name");
		table.getCellByPosition(CM_POSITION).setStringValue("Choix Cours");
		table.getCellByPosition(CMTD_POSITION).setStringValue("Choix CMTD");
		table.getCellByPosition(TD_POSITION).setStringValue("Choix TD");
		table.getCellByPosition(CMTP_POSITION).setStringValue("Choix CMTP");
		table.getCellByPosition(TP_POSITION).setStringValue("Choix TP");
		
	}
	
	private static void setValueAt(Table table, String info, int row, int column) {
		Cell cell= table.getCellByPosition(column, row);
		cell.setDisplayText(info);
		
	}
	
	public static SpreadsheetDocument createSummarizedOds(ImmutableSet <Course> allCourses, ImmutableSet <CoursePref> prefs) throws Throwable {
		
		SpreadsheetDocument document =createAnEmptyOds();
		Table summary=document.appendSheet("Summary");
		headersToOds(summary);
		int line=1;
		boolean test=false; // this test helps us to see when there is no teachers for a course
		
		for (Course c : allCourses) {
			setValueAt(summary,c.getStudyYear(), line, 0);
			setValueAt(summary,String.valueOf(c.getSemester()), line, 1);
			setValueAt(summary,c.getName(), line, 2);
			
			for (CoursePref p : prefs) {
				
				if (c.getName().equals(p.getCourse().getName())) { 
					test = true; 
					setValueAt(summary,p.getTeacher().getFirstName(), line, 3);
					setValueAt(summary,p.getTeacher().getLastName(), line, 4);
					setValueAt(summary,p.getPrefCM().toString(), line, 5);
					setValueAt(summary,p.getPrefCMTD().toString(), line, 6);
					setValueAt(summary,p.getPrefTD().toString(), line, 7);
					setValueAt(summary,p.getPrefCMTP().toString(), line, 8);
					setValueAt(summary,p.getPrefTP().toString(), line, 9);
					
					line++;
				}
				
			
			}
			
			if (test==false) {
				line++;
			}
			test=false;
		
		}
		document.save("target//FichierAgrege.ods");
		return document;
		
	}

}
