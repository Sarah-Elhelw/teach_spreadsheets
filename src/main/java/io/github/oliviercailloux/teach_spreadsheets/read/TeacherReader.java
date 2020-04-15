package io.github.oliviercailloux.teach_spreadsheets.read;

import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.*;

/**
 * This class reads the informations about a teacher based on the sheet "Emplois
 * du temps". For this reading to work, the document loaded and read must follow
 * the same format as "AA - Saisie des voeux 2016-2017.ods" : there needs to be
 * a sheet "Emploi du temps" were are stored the informations about a teacher.
 * Those informations must be in specific cells listed in the final static
 * attributes.
 * 
 *
 */
public class TeacherReader {
	private final static String SHEET_NAME = "Emplois du temps";
	private final static String LAST_NAME_POSITION = "B2";
	private final static String FIRST_NAME_POSITION = "F2";
	private final static String ADDRESS_POSITION = "B4";
	private final static String POST_CODE_POSITION = "B5";
	private final static String CITY_POSITION = "D5";
	private final static String PERSONAL_PHONE_POSITION = "B6";
	private final static String MOBILE_PHONE_POSITION = "E6";
	private final static String PERSONAL_EMAIL_POSITION = "B8";
	private final static String DAUPHINE_EMAIL_POSITION = "B9";
	private final static String STATUS_POSITION = "B11";
	private final static String DAUPHINE_PHONE_NUMBER_POSITION = "E11";
	private final static String OFFICE_POSITION = "H11";

	private String lastName;
	private String firstName;
	private String address;
	private String postCode;
	private String city;
	private String personalPhone;
	private String mobilePhone;
	private String personalEmail;
	private String dauphineEmail;
	private String status;
	private String dauphinePhoneNumber;
	private String office;

	public static TeacherReader newInstance() {
		return new TeacherReader();
	}

	private TeacherReader() {

	}
	/**
	 * Sets all the variables that will be later used to create a teacher.
	 * @param sheet - the current sheet
	 */

	private void readValues(Table sheet) {
		lastName = ReaderLib.getCellValue(sheet, LAST_NAME_POSITION);
		firstName = ReaderLib.getCellValue(sheet, FIRST_NAME_POSITION);
		address = ReaderLib.getCellValue(sheet, ADDRESS_POSITION);
		postCode = ReaderLib.getCellValue(sheet, POST_CODE_POSITION);
		city = ReaderLib.getCellValue(sheet, CITY_POSITION);
		personalPhone = ReaderLib.getCellValue(sheet, PERSONAL_PHONE_POSITION);
		mobilePhone = ReaderLib.getCellValue(sheet, MOBILE_PHONE_POSITION);
		personalEmail = ReaderLib.getCellValue(sheet, PERSONAL_EMAIL_POSITION);
		dauphineEmail = ReaderLib.getCellValue(sheet, DAUPHINE_EMAIL_POSITION);
		status = ReaderLib.getCellValue(sheet, STATUS_POSITION);
		dauphinePhoneNumber = ReaderLib.getCellValue(sheet, DAUPHINE_PHONE_NUMBER_POSITION);
		office = ReaderLib.getCellValue(sheet, OFFICE_POSITION);

	}
	
	/**
	 * Creates and returns a Teacher with the right informations set from an ods document.
	 * @param document - the document to be read to get the teacher's informations.
	 *  
	 */
	public Teacher createTeacherFromCalc(SpreadsheetDocument document) {
		Table sheet = Objects.requireNonNull(document.getSheetByName(SHEET_NAME));
		readValues(sheet);
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setLastName(lastName);
		teacherBuilder.setFirstName(firstName);
		teacherBuilder.setAddress(address);
		teacherBuilder.setPostCode(postCode);
		teacherBuilder.setCity(city);
		teacherBuilder.setPersonalPhone(personalPhone);
		teacherBuilder.setMobilePhone(mobilePhone);
		teacherBuilder.setPersonalEmail(personalEmail);
		teacherBuilder.setDauphineEmail(dauphineEmail);
		teacherBuilder.setStatus(status);
		teacherBuilder.setDauphinePhoneNumber(dauphinePhoneNumber);
		teacherBuilder.setOffice(office);
		return teacherBuilder.build();
	}
}