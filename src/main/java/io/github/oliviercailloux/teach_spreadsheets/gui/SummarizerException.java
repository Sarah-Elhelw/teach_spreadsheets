package io.github.oliviercailloux.teach_spreadsheets.gui;

/**
 * This exception is to be used in the catch part of the try-catch clause surrounding the OdsSummarizer.createSummary() function.
 * @author guedidi hedi
 *
 */
public class SummarizerException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SummarizerException(Exception e) {
		super("An error has occured during the writing of the assignment Files.",e);
	}
}
