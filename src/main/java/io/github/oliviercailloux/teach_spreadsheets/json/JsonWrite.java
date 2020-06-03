package io.github.oliviercailloux.teach_spreadsheets.json;

import java.util.Set;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.common.base.VerifyException;

import static com.google.common.base.Verify.verify;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import static com.google.common.base.Preconditions.checkNotNull;

public class JsonWrite {

	/**
	 * Serializes a Set of Course objects
	 * 
	 * @param courses a collection of Course objects with no duplicate elements
	 * 
	 * @return the serialized set
	 * 
	 * @throws VerifyException if the conversion failed
	 */
	public static String serializeSet(Set<Course> courses) {
		checkNotNull(courses, "The courses must not be null");
		String serialized = null;
		try (Jsonb jsonb = JsonbBuilder.create()) {
			serialized = jsonb.toJson(courses);
		} catch (Exception e) {
			throw new VerifyException(e);
		}
		verify(serialized != null, "The serialization process returned null");
		return serialized;
	}
}
