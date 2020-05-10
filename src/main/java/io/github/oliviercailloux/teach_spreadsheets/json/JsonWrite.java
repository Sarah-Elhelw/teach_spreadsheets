package io.github.oliviercailloux.teach_spreadsheets.json;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static com.google.common.base.Verify.verify;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import static com.google.common.base.Preconditions.checkNotNull;

public class JsonWrite {

	/**
	 * Serializes a Set of Course objects
	 * 
	 * @param courses a collection of Course objects with no duplicate elements
	 */
	private static String serializeSet(Set<Course> courses) {
		checkNotNull(courses, "The courses must not be null");
		String serialized = null;
		try (Jsonb jsonb = JsonbBuilder.create()) {
			serialized = jsonb.toJson(courses.toArray());
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		verify(serialized != null, "The serialization process returned null");
		return serialized;
	}

	/**
	 * Serializes a Set of Course objects and writes it into a file
	 * 
	 * @param filePath the file path where we store the serialized courses
	 * @param courses  a collection of Course objects with no duplicate elements
	 * 
	 * @throws if an I/O error occurs writing to or creating the file, or the text
	 *            cannot be encoded using the specified charset
	 */
	public static void writeCoursesInAJsonFile(Path filePath, Set<Course> courses) throws IOException {
		checkNotNull(filePath, "The filePath must not be null");
		checkNotNull(courses, "The courses must not be null");

		String serialized = serializeSet(courses);
		Files.writeString(filePath, serialized, StandardCharsets.UTF_8);
	}
	
	/**
	 * Serializes a Set of Course objects and writes it into a Writer
	 * 
	 * @param writer  the Writer where we store the serialized courses
	 * @param courses a collection of Course objects with no duplicate elements
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	public static void writeCoursesInAWriter(Writer writer, Set<Course> courses) throws IOException {
		checkNotNull(writer, "The writer must not be null.");
		checkNotNull(courses, "The courses must not be null");

		String serialized = serializeSet(courses);
		writer.write(serialized);
	}
}
