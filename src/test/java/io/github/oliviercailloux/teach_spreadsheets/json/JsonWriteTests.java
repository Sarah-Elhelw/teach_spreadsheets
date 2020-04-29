package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class JsonWriteTests {
	@Test
	void testWriteCoursesInAJsonFile() throws Exception {
		final Path infile = Path.of(
				"src/test/resources/io/github/oliviercailloux/teach_spreadsheets/read/Saisie_des_voeux_format simple.ods");
		CalcData calcData = CalcData.getData(infile);
		
		ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
		HashSet<Course> courses = new HashSet<>();
		for (CoursePref coursePref : coursePrefs) {
			courses.add(coursePref.getCourse());
		}
		
		Path jsonPath = Path.of("courses.json");
		JsonWrite.writeCoursesInAJsonFile(jsonPath, courses);
		
		try (Jsonb jsonb = JsonbBuilder.create()) {
			String expected = jsonb.toJson(courses.toArray());
			String actual = Files.readString(jsonPath, StandardCharsets.UTF_8);
			assertEquals(expected, actual);
		}
	}
}
