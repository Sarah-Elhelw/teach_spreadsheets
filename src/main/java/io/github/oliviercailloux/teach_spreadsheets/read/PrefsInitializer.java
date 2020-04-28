package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.base.Preconditions;

import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

/**
 * This class reads all the courses in an ods file and the preferences of a
 * teacher for these courses. For this class and its methods to work properly,
 * the file read must have the same structure as "AA - Saisie des voeux
 * 2016-2017.ods".
 *
 */
public class PrefsInitializer {
	private static ImmutableList<String> TABLE_LIST = ImmutableList.<String>builder()
			.add("DE1", "DE2", "L3 Informatique", "L3 Mathématiques", "M1 Mathématiques", "M1 Informatique").build(); //all the tables that follow the standard format

	/**
	 * Creates and returns a list of {@link CoursePref} from an ods document
	 * following the standard format.
	 * 
	 * @param document - the document to be read
	 * @param teacher  - whose preferences are read
	 */
	public static ImmutableSet<CoursePref> createPrefslist(SpreadsheetDocument document, Teacher teacher) {
		LinkedHashSet<CoursePref> prefsList = new LinkedHashSet<>();
		Table sheet;
		List<Table> listOfTables = document.getTableList();
		List<String> listOfTablesNames = new ArrayList<>();
		for (Table table : listOfTables) {
			listOfTablesNames.add(table.getTableName());
		}
		for (String iteam : TABLE_LIST) {
			Preconditions.checkArgument(listOfTablesNames.contains(iteam)); // Checking the document contains all the
																			// sheets'names in tableList
			sheet = document.getTableByName(iteam);
			CourseAndPrefReader reader = CourseAndPrefReader.newInstance();
			prefsList.addAll(reader.readSemester(sheet, teacher));
			prefsList.addAll(reader.readSemester(sheet, teacher));
		}
		return ImmutableSet.copyOf(prefsList);
	}

}