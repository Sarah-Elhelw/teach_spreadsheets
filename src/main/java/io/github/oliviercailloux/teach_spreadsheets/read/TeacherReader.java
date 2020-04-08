package io.github.oliviercailloux.teach_spreadsheets.read;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.teach_spreadsheets.base.*;
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
		try(ODSReader reader= new ODSReader(document)){
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
		}
	}
	
	public Teacher createTeacherFromCalc(SpreadsheetDocument document){
		readValues(document);
		Teacher.Builder builder=Teacher.Builder.newInstance();
		builder.setLastName(lastName);
		builder.setFirstName(firstName);
		builder.setAddress(address);
		builder.setPostCode(postCode);
		builder.setCity(city);
		builder.setPersonalPhone(personalPhone);
		builder.setMobilePhone(mobilePhone);
		builder.setPersonalEmail(personalEmail);
		builder.setDauphineEmail(dauphineEmail);
		builder.setStatus(status);
		builder.setDauphinePhoneNumber(dauphinePhoneNumber);
		builder.setOffice(office);
		return builder.build();	
	}
}
