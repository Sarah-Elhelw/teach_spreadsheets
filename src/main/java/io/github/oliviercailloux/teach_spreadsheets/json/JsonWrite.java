package io.github.oliviercailloux.teach_spreadsheets.json;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;


import static com.google.common.base.Preconditions.checkNotNull;

public class JsonWrite {
	
	/**
	 * Serializes a Set of Course objects and writes it into a file
	 * @param filePath the file path where we store the serialized courses
	 * @param courses a collection of Course objects with no duplicate elements
	 * @throws Exception
	 */
	public static void writeCoursesInAJsonFile(Path filePath, Set<Course> courses) throws Exception {
		checkNotNull(filePath);
		checkNotNull(courses);
		
		try (Jsonb jsonb = JsonbBuilder.create()) {
			String serialized = jsonb.toJson(courses.toArray());
			Files.writeString(filePath, serialized, StandardCharsets.UTF_8);
		}
	}
}
