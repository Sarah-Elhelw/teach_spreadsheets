package io.github.oliviercailloux.teach_spreadsheets.json;

import javax.json.bind.annotation.JsonbCreator;

import com.google.common.base.MoreObjects;

public class Person {
	private String firstName;
	private String lastName;
	
	@JsonbCreator
	public Person(){
		firstName = "";
		lastName = "";
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("firstName", firstName).add("lastName", lastName)
				.toString();
	}
	
}