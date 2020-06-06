package io.github.oliviercailloux.teach_spreadsheets.assignment;
import java.util.logging.Level;

import java.util.logging.Logger;
 


public class logger {

 

/**

* @param args

*/

public static void main(String[] args) {

Logger logger = Logger.getLogger("logger");

logger.log(Level.INFO, "The CourseAssignment class represents the assignment of only one course to a number of teachers. "
		+ "\n" +
		"The TeacherAssignment class stores the number of TD, TP, CM groups assigned to one teacher in the selected course.");
 

}
 

}
