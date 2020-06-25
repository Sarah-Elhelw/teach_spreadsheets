package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.SpreadsheetDocument;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * This class creates a {@link TeacherPrefs} by reading an ods file with a structure
 * similar to <a href=
 * "https://github.com/oliviercailloux/Teach-spreadsheets/blob/master/src/main/resources/io/github/oliviercailloux/y2018/teach_spreadsheets/odf/Saisie_voeux_dauphine.ods">"AA
 * - Saisie des voeux 2016-2017.ods"</a>.
 *
 */
public class TeacherPrefsInitializer {

	/**
	 * Creates a {@link TeacherPrefs} from the document passed as a parameter.
	 * 
	 * @param document - the document to be read
	 * @return a {@link TeacherPrefs} gathering the informations read in the document
	 */

	public static TeacherPrefs createTeacherPrefs(SpreadsheetDocument document) {
		TeacherReader teacherReader = TeacherReader.newInstance();
		Teacher teacher = teacherReader.createTeacherFromCalc(document);
		ImmutableSet<CoursePref> coursePrefs = PrefsInitializer.createPrefslist(document, teacher);
		return TeacherPrefs.newInstance(coursePrefs, teacher);
	}

}
