package io.github.oliviercailloux.teach_spreadsheets.json;

import javax.json.bind.annotation.JsonbCreator;

import com.google.common.base.MoreObjects;

public class Person {
	private String firstName;
	private String lastName;
	
	public Person(){
		firstName = "";
		lastName = "";
	}
	
	@JsonbCreator
	public static Person newInstance() {
		return new Person();
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
	public String toString() {
		return MoreObjects.toStringHelper(this).add("firstName", firstName).add("lastName", lastName)
				.toString();
	}
	
}