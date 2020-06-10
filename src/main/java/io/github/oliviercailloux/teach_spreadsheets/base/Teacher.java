package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;

import io.github.oliviercailloux.teach_spreadsheets.assignment.CourseAssignment;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

/**
 * Immutable. Class used to store a teacher's information. The minimum
 * information required is lastName. Uses Builder pattern implementation.
 * 
 * @see https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
 */
public class Teacher {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseAssignment.class);

	private static final String EXCEPTION = "String must not be null.";

	private String lastName;
	private String firstName;

	private String address;
	private String postCode;
	private String city;

	private String personalPhone;
	private String mobilePhone;
	private String dauphinePhoneNumber;

	private String personalEmail;
	private String dauphineEmail;
	private String status;

	private String office;

	private Teacher() {
		lastName = "";
		firstName = "";
		address = "";
		postCode = "";
		city = "";
		personalPhone = "";
		mobilePhone = "";
		dauphinePhoneNumber = "";
		personalEmail = "";
		dauphineEmail = "";
		status = "";
		office = "";
	}

	public static class Builder {
		private Teacher teacherToBuild;

		public static Builder newInstance() {
			return new Builder();
		}

		private Builder() {
			teacherToBuild = new Teacher();
		}

		public Teacher build() {
			checkNotNull(teacherToBuild.lastName);
			if (teacherToBuild.lastName.isEmpty())
				throw new IllegalArgumentException();
			Teacher teacherBuilt = teacherToBuild;
			teacherToBuild = new Teacher();
			return teacherBuilt;
		}

		public Builder setLastName(String lastName) {
			checkNotNull(lastName, EXCEPTION);
			this.teacherToBuild.lastName = lastName;
			return this;
		}

		public Builder setFirstName(String firstName) {
			checkNotNull(firstName, EXCEPTION);
			this.teacherToBuild.firstName = firstName;
			return this;
		}

		public Builder setAddress(String address) {
			checkNotNull(address, EXCEPTION);
			this.teacherToBuild.address = address;
			return this;
		}

		public Builder setPostCode(String postCode) {
			checkNotNull(postCode, EXCEPTION);
			this.teacherToBuild.postCode = postCode;
			return this;
		}

		public Builder setCity(String city) {
			checkNotNull(city, EXCEPTION);
			this.teacherToBuild.city = city;
			return this;
		}

		public Builder setPersonalPhone(String personalPhone) {
			checkNotNull(personalPhone, EXCEPTION);
			this.teacherToBuild.personalPhone = personalPhone;
			return this;
		}

		public Builder setMobilePhone(String mobilePhone) {
			checkNotNull(mobilePhone, EXCEPTION);
			this.teacherToBuild.mobilePhone = mobilePhone;
			return this;
		}

		public Builder setDauphinePhoneNumber(String dauphinePhoneNumber) {
			checkNotNull(dauphinePhoneNumber, EXCEPTION);
			this.teacherToBuild.dauphinePhoneNumber = dauphinePhoneNumber;
			return this;
		}

		public Builder setPersonalEmail(String personalEmail) {
			checkNotNull(personalEmail, EXCEPTION);
			this.teacherToBuild.personalEmail = personalEmail;
			return this;
		}

		public Builder setDauphineEmail(String dauphineEmail) {
			checkNotNull(dauphineEmail, EXCEPTION);
			this.teacherToBuild.dauphineEmail = dauphineEmail;
			return this;
		}

		public Builder setStatus(String status) {
			checkNotNull(status, EXCEPTION);
			this.teacherToBuild.status = status;
			return this;
		}

		public Builder setOffice(String office) {
			checkNotNull(office, EXCEPTION);
			this.teacherToBuild.office = office;
			return this;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getAddress() {
		return address;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getCity() {
		return city;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getDauphinePhoneNumber() {
		return dauphinePhoneNumber;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public String getDauphineEmail() {
		return dauphineEmail;
	}

	public String getStatus() {
		return status;
	}

	public String getOffice() {
		return office;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("lastName", lastName).add("firstName", firstName)
				.add("address", address).add("postCode", postCode).add("city", city).add("personalPhone", personalPhone)
				.add("mobilePhone", mobilePhone).add("personalEmail", personalEmail).add("dauphineEmail", dauphineEmail)
				.add("status", status).add("dauphinePhoneNumber", dauphinePhoneNumber).add("office", office).toString();
	}
	
	public static void main(String[] args) {

	Logger logger = Logger.getLogger("logger");

	logger.log(Level.INFO, "The classes Course, Teacher and CoursePref that are created by this process are also returned in order to be used for other purposes (like storing in JSON format the list of courses available in the input file).");
	}
}
