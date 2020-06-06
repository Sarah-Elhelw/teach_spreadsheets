package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.logging.Level;

import java.util.logging.Logger;
 


public class logger {

 

/**

* @param args

*/

public static void main(String[] args) {

Logger logger = Logger.getLogger("logger");

logger.log(Level.INFO, 
		"The main goal of these classes is to read information from an ods file and create the corresponding CalcData object.");

}
 

}


