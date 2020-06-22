package io.github.oliviercailloux.teach_spreadsheets.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbCreator;

import com.google.common.base.MoreObjects;
import com.google.common.base.VerifyException;
import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

public class Person {
	private String firstName;
	private String lastName;

	private Person() {
		firstName = "";
		lastName = "";
	}

	@JsonbCreator
	public static Person newInstance() {
		return new Person();
	}

	/**
	 * This method deserializes a json array string into an ImmutableList of
	 * persons. The lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>.
	 * 
	 * @param json - the json array string.
	 * 
	 * @return an ImmutableList of persons.
	 * 
	 * @throws RuntimeException if any unexpected error(s) occur(s) during
	 *                          deserialization.
	 * @throws VerifyException  if the conversion failed
	 * 
	 */
	public static ImmutableList<Person> toPersons(String json) {
		checkNotNull(json, "The String must not be null.");
		try (Jsonb jsonb = JsonbBuilder.create()) {
			List<Person> list = jsonb.fromJson(json, new ArrayList<Person>() {
				private static final long serialVersionUID = -7485196487128234751L;
			}.getClass().getGenericSuperclass());
			return ImmutableList.copyOf(list);
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Person setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public Person setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Person)) {
			return false;
		}
		if (this == o2) {
			return true;
		}
		Person p2 = (Person) o2;

		return firstName.equals(p2.firstName) && lastName.equals(p2.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("firstName", firstName).add("lastName", lastName).toString();
	}

}