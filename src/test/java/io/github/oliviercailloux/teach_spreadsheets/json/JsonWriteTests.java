package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.read.CalcDataInitializerTests;

public class JsonWriteTests {
	@Test
	void testWriteCoursesInAJsonFile() throws Exception {
		URL url = CalcDataInitializerTests.class.getResource("Saisie_des_voeux_format simple.ods");
		Path odsPath = Path.of(url.toURI());
		
		try (InputStream odsStream = Files.newInputStream(odsPath)) {
			CalcData calcData = CalcData.getData(odsStream);

			ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
			HashSet<Course> courses = new HashSet<>();
			for (CoursePref coursePref : coursePrefs) {
				courses.add(coursePref.getCourse());
			}

			try (FileSystem inMemoryFs = Jimfs.newFileSystem(Configuration.unix())) {
				Path jsonPath = inMemoryFs.getPath("courses.json");
				JsonWrite.writeCoursesInAJsonFile(jsonPath, courses);

				try (Jsonb jsonb = JsonbBuilder.create()) {
					String expected = jsonb.toJson(courses.toArray());
					String actual = Files.readString(jsonPath, StandardCharsets.UTF_8);
					assertEquals(expected, actual);
				}
			}
		}
	}
}
