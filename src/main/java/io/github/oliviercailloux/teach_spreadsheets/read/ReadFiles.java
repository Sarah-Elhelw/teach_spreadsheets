package io.github.oliviercailloux.teach_spreadsheets.read;

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

public class ReadFiles {
	Boolean firstRead = false;
	Set<Course> Courses = new LinkedHashSet<>();

	public Set<CalcData> readFilesFromFolder(Path pathToFolder) throws Exception {
		Set<CalcData> calcDataSet = new LinkedHashSet<>();
		try (Stream<Path> walk = Files.walk(pathToFolder)) {
			List<Path> result = walk.filter(f -> f.endsWith(".ods")).collect(Collectors.toList());

			for (Path filePath : result) {
				try (InputStream fileStream = Files.newInputStream(filePath)) {
					CalcData calcData = CalcData.getData(fileStream);
					calcDataSet.add(calcData);
					/**
					 * add the courses if it is the first read else we verify that we have the same
					 * courses as the first read
					 */
					if (!firstRead) {
						Courses.addAll(extractCoursesFromCalcData(calcData));
						firstRead = true;
					} else {
						Set<Course> CoursesTemp = extractCoursesFromCalcData(calcData);
						if (!verifyCourses(Courses, CoursesTemp)) {
							throw new IOException("The files in the ods files doesn't have the same courses");
						}
					}
				}
			}
		}
		return calcDataSet;
	}

	private boolean verifyCourses(Set<Course> baseCourses, Set<Course> coursesTemp) {
		return baseCourses.equals(coursesTemp);
	}

	private Set<Course> extractCoursesFromCalcData(CalcData calcData) {
		Set<Course> extractedCourses = new LinkedHashSet<>();
		for (CoursePref coursePref : calcData.getCoursePrefs()) {
			extractedCourses.add(coursePref.getCourse());
		}
		return extractedCourses;
	}

	public Set<Course> getCourses() {
		if (!firstRead) {
			throw new IllegalStateException("No file is read");
		}
		return Courses;
	}
}
