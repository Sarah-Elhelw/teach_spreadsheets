package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
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
import io.github.oliviercailloux.teach_spreadsheets.read.CalcDataInitializerTests;

public class JsonWriteTests {
	@Test
	void testWriteCourses() throws Exception {
		URL url = CalcDataInitializerTests.class.getResource("Saisie_des_voeux_format simple.ods");
		Path odsPath = Path.of(url.toURI());
		
		try (InputStream odsStream = Files.newInputStream(odsPath)) {
			CalcData calcData = CalcData.getData(odsStream);

			ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
			HashSet<Course> courses = new HashSet<>();
			for (CoursePref coursePref : coursePrefs) {
				courses.add(coursePref.getCourse());
			}
			
			Writer writer = new StringWriter();
			JsonWrite.writeCoursesInAWriter(writer, courses);
			
			try (Jsonb jsonb = JsonbBuilder.create()) {
				String expected = jsonb.toJson(courses.toArray());
				String actual = writer.toString();
				assertEquals(expected, actual);
			}
		}
	}
}
