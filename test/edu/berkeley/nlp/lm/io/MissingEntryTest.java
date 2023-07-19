package edu.berkeley.nlp.lm.io;

import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.berkeley.nlp.lm.ArrayEncodedNgramLanguageModel;
import edu.berkeley.nlp.lm.ArrayEncodedProbBackoffLm;
import edu.berkeley.nlp.lm.ConfigOptions;
import edu.berkeley.nlp.lm.ContextEncodedNgramLanguageModel;
import edu.berkeley.nlp.lm.ContextEncodedProbBackoffLm;
import edu.berkeley.nlp.lm.StringWordIndexer;

public class MissingEntryTest
{

	private static final double TOL = 1e-5;

	public static final String BIG_TEST_ARPA = "missing_test_fourgram.arpa";

	@Test
	public void testArrayEncoded() {

		final ArrayEncodedProbBackoffLm<String> lm = getLm(false);
		testArrayEncodedLogProb(lm);
	}

	@Test
	public void testCompressedEncoded() {

		final ArrayEncodedProbBackoffLm<String> lm = getLm(true);
		testArrayEncodedLogProb(lm);
	}

	@Test
	public void testContextEncoded() {

		final ContextEncodedProbBackoffLm<String> lm = getContextEncodedLm();
		testContextEncodedLogProb(lm);
	}

	/**
	 * @return
	 */
	private ContextEncodedProbBackoffLm<String> getContextEncodedLm() {
		final File lmFile = FileUtils.getFile(BIG_TEST_ARPA);
		final ConfigOptions configOptions = new ConfigOptions();
		configOptions.unknownWordLogProb = 0.0f;
		final ContextEncodedProbBackoffLm<String> lm = LmReaders.readContextEncodedLmFromArpa(lmFile.getPath(), new StringWordIndexer(), configOptions,
			Integer.MAX_VALUE);
		return lm;
	}

	/**
	 * @return
	 */
	private ArrayEncodedProbBackoffLm<String> getLm(boolean compress) {
		final File lmFile = FileUtils.getFile(BIG_TEST_ARPA);
		final ConfigOptions configOptions = new ConfigOptions();
		configOptions.unknownWordLogProb = 0.0f;
		final ArrayEncodedProbBackoffLm<String> lm = LmReaders.readArrayEncodedLmFromArpa(lmFile.getPath(), compress, new StringWordIndexer(), configOptions,
			Integer.MAX_VALUE);
		return lm;
	}

	public static void testArrayEncodedLogProb(final ArrayEncodedNgramLanguageModel<String> lm_) {

		double result1 = lm_.getLogProb(Arrays.asList("This another test is".split(" ")));
		if (!Double.isNaN(result1)) {
			Assert.assertEquals(result1, -0.67443009, TOL);
		} else {
			Assert.assertEquals(result1, Double.NaN, 0.0);
		}

		double result2 = lm_.getLogProb(Arrays.asList("another test sentence.".split(" ")));
		if (!Double.isNaN(result2)) {
			Assert.assertEquals(result2, -0.07443009, TOL);
		} else {
			Assert.assertEquals(result2, Double.NaN, 0.0);
		}

		double result3 = lm_.getLogProb(Arrays.asList("is another test".split(" ")));
		if (!Double.isNaN(result3)) {
			Assert.assertEquals(result3, -0.1366771, TOL);
		} else {
			Assert.assertEquals(result3, Double.NaN, 0.0);
		}

		double result4 = lm_.getLogProb(Arrays.asList("another test".split(" ")));
		if (!Double.isNaN(result4)) {
			Assert.assertEquals(result4, (-0.60206 + -0.2218488), TOL);
		} else {
			Assert.assertEquals(result4, Double.NaN, 0.0);
		}
	}


	public static void testContextEncodedLogProb(final ContextEncodedNgramLanguageModel<String> lm_) {

		Assert.assertEquals(lm_.getLogProb(Arrays.asList("This another test is".split(" "))), -0.67443009, TOL);
		Assert.assertEquals(lm_.getLogProb(Arrays.asList("another test sentence.".split(" "))), -0.07443009, TOL);
		Assert.assertEquals(lm_.getLogProb(Arrays.asList("is another test".split(" "))), -0.1366771, TOL);
		Assert.assertEquals(lm_.getLogProb(Arrays.asList("another test".split(" "))), -0.60206 + -0.2218488, TOL);
	}
}
