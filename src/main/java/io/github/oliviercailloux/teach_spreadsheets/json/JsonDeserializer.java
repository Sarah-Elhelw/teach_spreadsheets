package io.github.oliviercailloux.teach_spreadsheets.json;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.stream.JsonParsingException;

import com.google.common.base.VerifyException;
import com.google.common.collect.ImmutableSet;
import static com.google.common.base.Preconditions.checkNotNull;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

public class JsonDeserializer {

	/**
	 * This method checks that the String represents a JsonArray. Having only a
	 * JsonArray is necessary for the deserialization process to work properly,
	 * otherwise, it invariably returns an empty set which we do not want.
	 * 
	 * @param textArray - the json text we want to make sure contains only a JsonArray
	 * 
	 * @throws IllegalArgumentException if the parameter is not a single json array
	 */
	private static void checkFormat(String textArray) {
		try (JsonReader jr = Json.createReader(new StringReader(textArray))) {
			jr.readArray();
		} catch (JsonParsingException jpe) {
			throw new IllegalArgumentException("The string does not contain only a JsonArray.", jpe);
		}
	}

	/**
	 * This method deserializes a json string into an ImmutableSet of courses. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>. The json
	 * string read must contain a single array.
	 * 
	 * @param json - the json string containing only a list of courses.
	 * 
	 * @return an ImmutableSet of courses.
	 * 
	 * @throws RuntimeException if any unexpected error(s) occur(s) during
	 *                          deserialization.
	 * @throws VerifyException  if the conversion failed
	 * 
	 */
	public static ImmutableSet<Course> toCourses(String json) {
		checkNotNull(json, "The String must not be null.");
		checkFormat(json);

		try (Jsonb jsonb = JsonbBuilder.create()) {
			/**
			 * We first build each course to make sure they represent proper and acceptable
			 * Course objects :
			 */
			List<Course.Builder> coursesB = jsonb.fromJson(json, new ArrayList<Course.Builder>() {
				private static final long serialVersionUID = -7485196487129232751L;
			}.getClass().getGenericSuperclass());
			List<Course> courses = new ArrayList<>();
			for (Course.Builder cb : coursesB) {
				courses.add(cb.build());
			}
			ImmutableSet<Course> is = ImmutableSet.copyOf(courses);
			return is;
		}
		catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new VerifyException(e);
		}
	}

}