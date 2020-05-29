package io.github.oliviercailloux.teach_spreadsheets.read;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;

import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeacherReaderTests {

	@Test
	void testcreateTeacherFromCalc() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				TeacherReader teacherReader = TeacherReader.newInstance();
				Teacher teacher = teacherReader.createTeacherFromCalc(document);
				assertEquals("Doe", teacher.getLastName());
				assertEquals("John", teacher.getFirstName());
				assertEquals("19 rue Jacques Louvel-Tessier", teacher.getAddress());
				assertEquals("75010", teacher.getPostCode());
				assertEquals("Paris", teacher.getCity());
				assertEquals("123456789", teacher.getPersonalPhone());
				assertEquals("987654321", teacher.getMobilePhone());
				assertEquals("john.doe@outlook.com", teacher.getPersonalEmail());
				assertEquals("john.doe@dauphine.eu", teacher.getDauphineEmail());
				assertEquals("MCF", teacher.getStatus());
				assertEquals("1928373645", teacher.getDauphinePhoneNumber());
				assertEquals("B048", teacher.getOffice());
			}
		}
	}
}
