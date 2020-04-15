package io.github.oliviercailloux.teach_spreadsheets.read;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * This class creates a {@link CalcData} by reading an ods file with a structure
 * similar to "AA - Saisie des voeux 2016-2017.ods".
 *
 */
public class CalcDataInitializer {

	public static CalcDataInitializer newInstance() {
		return new CalcDataInitializer();
	}

	private CalcDataInitializer() {

	}

	/**
	 * Creates a {@link CalcData} from the document passed as a parameter.
	 * 
	 * @param document - the document to be read
	 * @return a {@link CalcData} gathering the informations read in the document
	 */

	private CalcData createCalcData(SpreadsheetDocument document) {
		TeacherReader teacherReader = TeacherReader.newInstance();
		Teacher teacher = teacherReader.createTeacherFromCalc(document);
		PrefsInitializer prefsInitializer = PrefsInitializer.newInstance();
		ImmutableSet<CoursePref> coursePrefs = prefsInitializer.createPrefslist(document, teacher);
		return CalcData.newInstance(coursePrefs, teacher);
	}

	/**
	 * Opens and creates a {@link CalcData} from a document whose path is passed as
	 * a parameter.
	 * 
	 * @param documentPath - the path of the file to be read
	 * @return a {@link CalcData} gathering the informations read in the document
	 * @throws Exception to handle the exception type IOException
	 */

	public CalcData readDocument(Path documentPath) throws Exception {
		try (InputStream stream = Files.newInputStream(documentPath)) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				return createCalcData(document);
			}
		}

	}

}
