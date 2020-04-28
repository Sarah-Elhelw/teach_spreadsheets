package io.github.oliviercailloux.teach_spreadsheets.json;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;


import static com.google.common.base.Preconditions.checkNotNull;

public class JsonWrite {
	public static void writeCoursesInAJsonFile(Path filePath, ImmutableSet<Course> courses) throws Exception {
		checkNotNull(filePath);
		checkNotNull(courses);
		
		try (Jsonb jsonb = JsonbBuilder.create()) {
			String serialized = jsonb.toJson(courses.toArray());
			Files.writeString(filePath, serialized, StandardCharsets.UTF_8);
		}
	}
}
