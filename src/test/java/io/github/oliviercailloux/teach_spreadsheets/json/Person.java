package io.github.oliviercailloux.teach_spreadsheets.json;

import java.util.Objects;

import javax.json.bind.annotation.JsonbCreator;

import com.google.common.base.MoreObjects;


public class Person {
	private String name;

	private Person() {
		name = "";
	}

	@JsonbCreator
	public static Person newInstance() {
		return new Person();
	}

	public String getName() {
		return name;
	}

	public Person setName(String name) {
		this.name = name;
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

		return name.equals(p2.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).toString();
	}

}