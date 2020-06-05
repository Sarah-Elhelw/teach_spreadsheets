package io.github.oliviercailloux.teach_spreadsheets.read;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;

/**
 * this class reads 
 */
public class MultipleOdsPrefReader {
	/**
	 * Read the Ods Pref files from the path in the parameter
	 * @param pathToFolder the path to the folder where all the ods files
	 * @return a set containing all the CalcDatas from all the files reads
	 * @throws Exception
	 */
	public static Set<CalcData> readFilesFromFolder(Path pathToFolder) throws Exception {
		checkNotNull(pathToFolder);
		Set<CalcData> calcDataSet = new LinkedHashSet<>();
		try (Stream<Path> walk = Files.walk(pathToFolder)) {
			Set<Path> result = walk.filter(f -> f.toString().endsWith(".ods")).collect(Collectors.toSet());
			for (Path filePath : result) {
				try (InputStream fileStream = Files.newInputStream(filePath)) {
					CalcData calcData = CalcData.getData(fileStream);
					calcDataSet.add(calcData);
				}
			}
		}
		return calcDataSet;
	}
}
