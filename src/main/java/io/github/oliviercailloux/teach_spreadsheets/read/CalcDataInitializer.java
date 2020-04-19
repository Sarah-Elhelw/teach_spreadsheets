package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * This class creates a {@link CalcData} by reading an ods file with a structure
 * similar to <a href="../../../../../../../../src/test/resources/io/github/oliviercailloux/teach_spreadsheets/read/AA - Saisie des voeux 2016-2017_Emplois-du-temps.ods">"AA - Saisie des voeux 2016-2017.ods"</a>.
 *
 */
public class CalcDataInitializer {

	/**
	 * Creates a {@link CalcData} from the document passed as a parameter.
	 * 
	 * @param document - the document to be read
	 * @return a {@link CalcData} gathering the informations read in the document
	 */

	public static CalcData createCalcData(SpreadsheetDocument document) {
		TeacherReader teacherReader = TeacherReader.newInstance();
		Teacher teacher = teacherReader.createTeacherFromCalc(document);
		PrefsInitializer prefsInitializer = PrefsInitializer.newInstance();
		ImmutableSet<CoursePref> coursePrefs = prefsInitializer.createPrefslist(document, teacher);
		return CalcData.newInstance(coursePrefs, teacher);
	}



}
