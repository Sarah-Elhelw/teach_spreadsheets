package io.github.oliviercailloux.teach_spreadsheets.base;

import java.util.logging.Level;

import java.util.logging.Logger;
 


public class logger {

 

/**

* @param args

*/

public static void main(String[] args) {

Logger logger = Logger.getLogger("logger");

logger.log(Level.INFO, "CalcData represents the data that we can get from the files that the university gives us."
		+ "\n" +
		"The classes Course, Teacher and CoursePref are used in that matter."
		+ "\n "
		+ "CoursePref represents preferences from a teacher for a specified course.");
 

}
 

}