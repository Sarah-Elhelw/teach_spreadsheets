package io.github.oliviercailloux.teach_spreadsheets.json;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;

import com.google.common.collect.ImmutableSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

public class JsonRead {

	/**
	 * This method formats a json text, that contains a single array, to extract
	 * this array from it.
	 * 
	 * @param textArray - the json text containing an array.
	 * 
	 * @return the formatted json text to suit Jsonb deserializer.
	 * 
	 * @throws NullPointerException     if the parameter is null
	 * @throws IllegalArgumentException if the parameter does not contain an array
	 *                                  or contains several arrays.
	 */
	public static String formatToArray(String textArray) {
		checkNotNull(textArray, "The String must not be null.");
		int leftSquareBracket = textArray.indexOf('[');
		int rightSquareBracket = textArray.indexOf(']');
		checkArgument(leftSquareBracket != -1 && rightSquareBracket != -1,
				"The parameter must be a json text containing an array.");
		checkArgument(
				leftSquareBracket == textArray.lastIndexOf('[') && rightSquareBracket == textArray.lastIndexOf(']'),
				"The parameter must contain a single array.");
		return textArray.substring(leftSquareBracket, rightSquareBracket + 1);
	}

	/**
	 * This method deserializes a json string into an ImmutableSet of courses. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>. The json
	 * string read must contain a single array.
	 * 
	 * @param fileContent - the json string containing a list of courses.
	 * 
	 * @return an ImmutableSet of courses.
	 * 
	 */
	public static ImmutableSet<Course> getSetOfCoursesInfo(String fileContent) {
		try (Jsonb jsonb = JsonbBuilder.create()) {
			/**
			 * We first build each course to make sure they represent proper and acceptable
			 * Course objects :
			 */
			List<Course.Builder> coursesB = jsonb.fromJson(fileContent, new ArrayList<Course.Builder>() {
				private static final long serialVersionUID = -7485196487129232750L;
			}.getClass().getGenericSuperclass());
			List<Course> courses = new ArrayList<>();
			for (Course.Builder cb : coursesB) {
				courses.add(cb.build());
			}
			ImmutableSet<Course> is = ImmutableSet.copyOf(courses);
			return is;
		}
		/**
		 * If an exception is thrown by the initialization of coursesB, we want to throw
		 * it as it is (and not as a new IllegalArgumentException) to get the root
		 * cause.
		 */
		catch (JsonbException je) {
			throw je;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}