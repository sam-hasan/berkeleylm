package edu.berkeley.nlp.lm.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import edu.berkeley.nlp.lm.WordIndexer;
import edu.berkeley.nlp.lm.collections.Iterators;
import edu.berkeley.nlp.lm.util.Logger;
import edu.berkeley.nlp.lm.util.LongRef;

/**
 * Class for reading raw text files.
 * 
 * @author adampauls
 * 
 * @param <W>
 */
public class TextReader<W> implements LmReader<LongRef, LmReaderCallback<LongRef>>
{
	private final WordIndexer<W> wordIndexer;

	private final Iterable<String> lineIterator;

	public TextReader(final List<String> inputFiles, final WordIndexer<W> wordIndexer) {
		this(getLineIterator(inputFiles), wordIndexer);

	}

	public TextReader(Iterable<String> lineIterator, final WordIndexer<W> wordIndexer) {
		this.lineIterator = lineIterator;
		this.wordIndexer = wordIndexer;

	}

	/**
	 * Reads newline-separated plain text from inputFiles, and writes an ARPA lm
	 * file to outputFile. If files have a .gz suffix, then they will be
	 * (un)zipped as necessary.
	 * 
	 * @param inputFiles
	 * @param outputFile
	 */
	@Override
	public void parse(final LmReaderCallback<LongRef> callback) {
		readFromFiles(callback);
	}

	private void readFromFiles(final LmReaderCallback<LongRef> callback) {
		Logger.startTrack("Reading in ngrams from raw text");

		countNgrams(lineIterator, callback);
		Logger.endTrack();

	}

	/**
	 * @param <W>
	 * @param wordIndexer
	 * @param maxOrder
	 * @param allLinesIterator
	 * @param callback
	 * @param ngrams
	 * @return
	 */
	private void countNgrams(final Iterable<String> allLinesIterator, final LmReaderCallback<LongRef> callback) {
		long numLines = 0;

		for (final String line : allLinesIterator) {
			if (numLines % 10000 == 0) Logger.logs("On line " + numLines);
			numLines++;

			// This line replaces the previous transformation logic
			int[] sent = WordIndexer.StaticMethods.getIndexedSentence(wordIndexer, line);

			callback.call(sent, 0, sent.length, new LongRef(1L), line);
		}
		callback.cleanup();
	}


	/**
	 * @param files
	 * @return
	 */
	private static Iterable<String> getLineIterator(final Iterable<String> files) {
		final Iterable<String> allLinesIterator = Iterators.flatten(new Iterators.Transform<String, Iterator<String>>(files.iterator())
		{

			@Override
			protected Iterator<String> transform(final String file) {
				try {
					if (file.equals("-")) {
						return IOUtils.lineIterator(IOUtils.getReader(System.in));
					} else
						return IOUtils.lineIterator(file);
				} catch (final IOException e) {
					throw new RuntimeException(e);

				}
			}
		});
		return allLinesIterator;
	}

}
