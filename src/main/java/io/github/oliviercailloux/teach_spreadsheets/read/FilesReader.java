package io.github.oliviercailloux.teach_spreadsheets.read;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class FilesReader {
	Boolean firstRead = false;
	Set<Course> courses = new LinkedHashSet<>();
	
	public static FilesReader newInstance() {
		return new FilesReader();
	}
	
	private FilesReader() {
		
	}
	

	public Set<CalcData> readFilesFromFolder(Path pathToFolder) throws Exception {
		checkNotNull(pathToFolder);
		Set<CalcData> calcDataSet = new LinkedHashSet<>();
		try (Stream<Path> walk = Files.walk(pathToFolder)) {
			List<Path> result = walk.filter(f -> f.toString().endsWith(".ods")).collect(Collectors.toList());
			for (Path filePath : result) {
				try (InputStream fileStream = Files.newInputStream(filePath)) {
					CalcData calcData = CalcData.getData(fileStream);
					/**
					 * add the courses if it is the first read else we verify that we have the same
					 * courses as the first read
					 */
					if (!firstRead) {
						courses.addAll(extractCoursesFromCalcData(calcData));
						firstRead = true;
					} else {
						Set<Course> coursesTemp = extractCoursesFromCalcData(calcData);
						if (!verifyCourses(courses, coursesTemp)) {
							throw new IOException("The file in the ods files doesn't have the same courses as the first file read");
						}
					}
					calcDataSet.add(calcData);
				}
			}
		}
		return calcDataSet;
	}

	private boolean verifyCourses(Set<Course> baseCourses, Set<Course> coursesTemp) {
		checkNotNull(baseCourses,coursesTemp);
		return baseCourses.equals(coursesTemp);
	}

	private Set<Course> extractCoursesFromCalcData(CalcData calcData) {
		checkNotNull(calcData);
		Set<Course> extractedCourses = new LinkedHashSet<>();
		for (CoursePref coursePref : calcData.getCoursePrefs()) {
			extractedCourses.add(coursePref.getCourse());
		}
		return extractedCourses;
	}

	public Set<Course> getCourses() {
		checkState(firstRead);
		return courses;
	}
}
