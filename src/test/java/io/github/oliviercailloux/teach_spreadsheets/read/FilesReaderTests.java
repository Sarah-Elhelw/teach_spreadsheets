package io.github.oliviercailloux.teach_spreadsheets.read;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;


public class FilesReaderTests {
	@Test
	void testReadFilesFromFolder() throws Exception { 
		URL resourceUrl = PrefsInitializer.class.getResource("testFolder");
		FilesReader filesReader= FilesReader.newInstance();
		Set<CalcData> calcDatas =filesReader.readFilesFromFolder(Path.of(resourceUrl.toURI()));
		assertEquals(2, calcDatas.size());
	
}
}
