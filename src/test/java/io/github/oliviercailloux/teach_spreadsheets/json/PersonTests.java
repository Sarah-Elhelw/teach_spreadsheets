package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PersonTests {
	@Test
	void testToPersons() throws Exception {

		/** CASE 1 : Deserialization with a wrongly formatted json file: */

		URL resourceUrl1 = JsonDeserializer.class.getResource("WrongFormat.json");
		final Path path1 = Path.of(resourceUrl1.toURI());
		String json1 = Files.readString(path1);

		List<Person> list1 = Person.toPersons(json1);

		/** The deserialization process returns an empty list instead of failing fast: */
		assertEquals(List.of(), list1);
		

		/** CASE 2 : Control: */

		URL resourceUrl2 = JsonDeserializer.class.getResource("RightFormat.json");
		final Path path2 = Path.of(resourceUrl2.toURI());
		String json2 = Files.readString(path2);

		List<Person> list2 = Person.toPersons(json2);

		/** The deserialization process works properly: */

		assertTrue(list2.size() == 1);

		Person actual = list2.get(0);
		Person expected = Person.newInstance().setFirstName("John").setLastName("Doe");
		assertEquals(expected, actual);
	}

}