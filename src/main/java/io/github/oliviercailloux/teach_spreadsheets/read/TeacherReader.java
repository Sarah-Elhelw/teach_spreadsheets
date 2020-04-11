package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.teach_spreadsheets.base.*;

/**
 * This class reads the informations about a teacher based on the sheet "Emplois du temps".
 * For this reading to work, the document loaded and read must follow the same format as "AA - 
 * Saisie des voeux 2016-2017.ods" : there needs to be a sheet "Emploi du temps" were are stored
 * the informations about a teacher. Those informations must be in specific cells listed in the
 * final static attributes.
 * 
 *
 */
public class TeacherReader{
	private final static String sheet="Emplois du temps";
	private final static String lastNamePosition="B2";
	private final static String firstNamePosition="F2";
	private final static String addressPosition="B4";
	private final static String postCodePosition="B5";
	private final static String cityPosition="D5";
	private final static String personalPhonePosition="B6";
	private final static String mobilePhonePosition="E6";
	private final static String personalEmailPosition="B8";
	private final static String dauphineEmailPosition="B9";
	private final static String statusPosition="B11";
	private final static String dauphinePhoneNumberPosition="E11";
	private final static String officePosition="H11";
	
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
	

	public static TeacherReader newInstance(){
		return new TeacherReader();
	}
	
	private TeacherReader() {
		
	}
	
	private void readValues(SpreadsheetDocument document){
		ODSReader reader= new ODSReader(document);
		lastName=reader.getCellValue(sheet, lastNamePosition);
		firstName=reader.getCellValue(sheet, firstNamePosition);
		address=reader.getCellValue(sheet, addressPosition);
		postCode=reader.getCellValue(sheet, postCodePosition);
		city=reader.getCellValue(sheet, cityPosition);
		personalPhone=reader.getCellValue(sheet, personalPhonePosition);
		mobilePhone=reader.getCellValue(sheet, mobilePhonePosition);
		personalEmail=reader.getCellValue(sheet, personalEmailPosition);
		dauphineEmail=reader.getCellValue(sheet, dauphineEmailPosition);
		status=reader.getCellValue(sheet, statusPosition);
		dauphinePhoneNumber=reader.getCellValue(sheet, dauphinePhoneNumberPosition);
		office=reader.getCellValue(sheet, officePosition);
		reader.close();
	}
	
	public Teacher createTeacherFromCalc(SpreadsheetDocument document){
		readValues(document);
		Teacher.Builder teacherBuilder=Teacher.Builder.newInstance();
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