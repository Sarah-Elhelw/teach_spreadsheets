package io.github.oliviercailloux.teach_spreadsheets.CalcWrite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*; 
import java.io.*; 
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.Course;
import io.github.oliviercailloux.teach_spreadsheets.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.CalcData;
import java.nio.file.Path;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FichierAgregeTest {
	
	@Test
	void testFichierAgrege() throws Exception{
		FichierAgrege fichier = FichierAgrege.newInstance();
		final Path infile = Path.of(
				"src\\test\\resources\\io.github.oliviercailloux.teach_spreadsheets.read\\FihierAgrege.ods");
		SpreadsheetDocument doc; 
		doc.loadDocument(infile);
		
		String expectedHeader = "Year of study";
		String actualHeader = doc.getCellByPosition( "A1" );
		assertEquals(expectedHeader, actualHeader);
	
	}
	
}
