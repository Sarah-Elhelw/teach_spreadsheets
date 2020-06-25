package io.github.oliviercailloux.teach_spreadsheets.json;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.stream.JsonParsingException;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

public class PersonTests {
	/**
	 * This method deserializes a json array string into an ImmutableList of
	 * persons. The lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>.
	 * 
	 * @param json - the json array string.
	 * 
	 * @return an ImmutableList of persons.
	 * 
	 */
	private static ImmutableList<Person> toPersons(String json) throws Exception {
		checkNotNull(json, "The String must not be null.");
		try (Jsonb jsonb = JsonbBuilder.create()) {
			List<Person> list = jsonb.fromJson(json, new ArrayList<Person>() {
				private static final long serialVersionUID = -7485196487128234751L;
			}.getClass().getGenericSuperclass());
			return ImmutableList.copyOf(list);
		}
	}

	@Test
	void testToPersons() throws Exception {

		/** CASE 1 : Deserialization with a json that is not a json array: */

			/** With a json object */
		String json0 = "{\"name\": \"John Doe\"}";

		List<Person> list0 = PersonTests.toPersons(json0);

			/** The deserialization process returns an empty list instead of failing fast: */
		assertEquals(List.of(), list0);
		
		
			/** With a json object containing a json array: */
		String json1 = "{\"persons\":[{\"name\": \"John Doe\"}]}";

		List<Person> list1 = PersonTests.toPersons(json1);

			/** The deserialization process returns an empty list instead of failing fast: */
		assertEquals(List.of(), list1);
		

		/** CASE 2 : Control. Deserialization with a json array: */

		String json2 = "[{\"name\": \"John Doe\"}]";

		List<Person> list2 = PersonTests.toPersons(json2);

			/** The deserialization process works properly: */

		assertTrue(list2.size() == 1);

		Person actual = list2.get(0);
		Person expected = Person.newInstance().setName("John Doe");
		assertEquals(expected, actual);
		
		
		/** CASE 3 : Deserialization process fails fast if the json is wrongly formatted: */
		
			/** When the closing square bracket is missing in the json array: */
		String json3 = "[{\"name\": \"John Doe\"}";
		assertThrows(JsonParsingException.class, () -> {
			PersonTests.toPersons(json3);
		});

			/** When the closing curly bracket is missing in the json array: */
		String json4 = "[{\"name\": \"John Doe\"]";
		assertThrows(JsonParsingException.class, () -> {
			PersonTests.toPersons(json4);
		});
		
			/** When the closing square bracket is missing in the json object: */
		String json5 = "{\"persons\":[{\"name\": \"John Doe\"}}";
		assertThrows(JsonParsingException.class, () -> {
			PersonTests.toPersons(json5);
		});
		
			/** When the closing curly bracket is missing in the json object: */
		String json6 = "{\"persons\":[{\"name\": \"John Doe\"}]";
		assertThrows(JsonParsingException.class, () -> {
			PersonTests.toPersons(json6);
		});

	}

}