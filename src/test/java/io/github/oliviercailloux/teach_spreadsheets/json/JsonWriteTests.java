package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

public class JsonWriteTests {
	
	@Test
	void testWriteCoursesInAJsonFile() throws Exception {
		URL url = JsonWriteTests.class.getResource("coursesJsonWrite.json");
		final Path infile = Path.of(url.toURI());
		
		try (Jsonb jsonb = JsonbBuilder.create()) {
			Set<Course> deserialized = jsonb.fromJson(Files.newInputStream(infile), Set.class);
					
			try (FileSystem inMemoryFs = Jimfs.newFileSystem(Configuration.unix())) {
				Path jsonPath = inMemoryFs.getPath("courses.json");
				JsonWrite.writeCoursesInAJsonFile(jsonPath, deserialized);	
				
				String expected = jsonb.toJson(deserialized.toArray());
				String actual = Files.readString(jsonPath, StandardCharsets.UTF_8);
				assertEquals(expected, actual);
			}
		}
	}
}
