package io.github.oliviercailloux.teach_spreadsheets.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.teach_spreadsheets.base.AggregatedData;
import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.gui.Controller;
import io.github.oliviercailloux.teach_spreadsheets.json.JsonSerializer;
import io.github.oliviercailloux.teach_spreadsheets.read.MultipleOdsPrefReader;
import io.github.oliviercailloux.teach_spreadsheets.write.OdsSummarizer;

public class App {
	static private Path outputFolderPath = Path.of("output");
	static private Path inputFolderPath = Path.of("input");

	/**
	 * The main that connect all the components of this program.
	 */
	public static void main(String[] args) throws Exception {
		/**
		 * Reading part.
		 */
		Set<CalcData> calcDatas = MultipleOdsPrefReader.readFilesFromFolder(inputFolderPath);
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		for (CalcData calcData : calcDatas) {
			aggregatedDataBuilder.addCalcData(calcData);
		}
		/**
		 * Writing the Ods summary and the Json courses part.
		 */
		AggregatedData aggregatedData = aggregatedDataBuilder.build();
		Set<Course> courses = aggregatedData.getCourses();
		Set<CoursePref> CoursePrefs = new LinkedHashSet<>();
		for (Course course : courses) {
			CoursePrefs.addAll(aggregatedData.getCoursePrefs(course));
		}
		OdsSummarizer odsPrefSummary = OdsSummarizer.newInstance(courses);
		odsPrefSummary.addPrefs(CoursePrefs);

		try (SpreadsheetDocument odsPrefSummaryDocument = odsPrefSummary.createSummary()) {
			if (!Files.exists(outputFolderPath)) {
				Files.createDirectory(outputFolderPath);
			}
			odsPrefSummaryDocument.save(outputFolderPath.toString() + "//" + "odsPrefSummary.ods");
		}

		String coursesJson = JsonSerializer.serializeSet(courses);
		Files.writeString(Path.of(outputFolderPath.toString() + "//" + "courses.json"), coursesJson);

		Controller.initializeAndLaunchGui(calcDatas, courses, CoursePrefs, outputFolderPath);
	}
}
