
/**
 * *********************************************************************
 * This is free software - without ANY guarantee!
 * <p/>
 * <p/>
 * Copyright 2013, Dr. Gernot Starke, arc42.org
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * **********************************************************************
 */

package org.arc42.pdfutil;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests whether the options and arguments given to ArgumentParser
 * (via command line) can be recognized.
 * <p>
 * Please note: as ArgumentParser requires at least one input file (as argument to --f option!),
 * we have to mirror this fact in all testcases!
 * </p>
 *
 * @see ArgumentParser#setupAllowedOptions()
 */


public class ArgumentParserTest {
    private ArgumentParser argumentParser;

    private String sampleBlankPageText = "42 - solid foundation of everything";
    private String sampleTargetFileName = "./sample_target_file.pdf";


    @Before
    public void setUp() throws Exception {

        argumentParser = new ArgumentParser();
        argumentParser.setupAllowedOptions();

    }


    @Test
    public void canRecognizeMultipleOptions() {


        argumentParser.transformArgsIntoOptions(new String[]{
                "-f", "./tmp/test.pdf",
                "-t", sampleTargetFileName,
                "-b", sampleBlankPageText,
                "-e", "false"});

        assertTrue("Option for targetfile not recognized",
                argumentParser.getOptions().has("targetfile"));

        assertTrue("Option for blank-page-text not recognized",
                argumentParser.getOptions().has("b"));

        assertEquals("Argument of blankpagetext-option not recognized",
                sampleBlankPageText,
                argumentParser.getOptions().valueOf("B"));


    }

    @Test
    public void recognizeFileOptionWithSingleArgument() {
        argumentParser.transformArgsIntoOptions(new String[]{
                "-f", GivenPdfFiles.SRC_FILE_3_PAGES}
        );

        // test for the option
        assertTrue("Option for files not recognized",
                argumentParser.getOptions().has("files"));

        assertTrue("Option for sourcefile file not recognized",
                argumentParser.getOptions().has("sourcefiles"));

        assertTrue("Option for file ('f') not recognized",
                argumentParser.getOptions().has("f"));

        // test the argument (filename)
        assertEquals("Argument of file-option not recognized",
                new String(org.arc42.pdfutil.GivenPdfFiles.SRC_FILE_3_PAGES),
                argumentParser.getOptions().valueOf("f"));

    }


    @SuppressWarnings("unchecked")
    @Test
    public void recognizeFileOptionWithMultipleArguments() {

        String pathSeparatorChar = ":";

        String fileargs =
                GivenPdfFiles.SRC_FILE_3_PAGES + pathSeparatorChar
                        + GivenPdfFiles.SRC_FILE_4_PAGES + pathSeparatorChar
                        + GivenPdfFiles.SRC_FILE_11_PAGES;

        argumentParser.transformArgsIntoOptions(new String[]{"--f", fileargs});

        // test for the option
        assertTrue("Option for files not recognized",
                argumentParser.getOptions().has("files"));

        // test whether arguments are recognized
        assertTrue("file option has not arguments",
                argumentParser.getOptions().hasArgument("f"));

        // test the three arguments
        // uncomment the following, if parser is configured with "File.class" instead of "String.class"
        //assertEquals( "Arguments of file-option not recognized",
        //        asList( new File( GivenPdfFiles.SRC_FILE_3_PAGES),
        //                new File( GivenPdfFiles.SRC_FILE_4_PAGES),
        //                new File( GivenPdfFiles.SRC_FILE_11_PAGES)),
        //        argumentParser.options.valuesOf("f") );

        // test the three arguments
        assertEquals("Arguments of file-option not recognized",
                asList(new String(GivenPdfFiles.SRC_FILE_3_PAGES),
                        new String(GivenPdfFiles.SRC_FILE_4_PAGES),
                        new String(GivenPdfFiles.SRC_FILE_11_PAGES)),
                argumentParser.getOptions().valuesOf("f"));

        // capture the arguments in a List
        List<String> someFiles;


        someFiles = (List<String>) argumentParser.getOptions().valuesOf("f");

        assertEquals("cannot capture first argument of file option",
                someFiles.get(0),
                GivenPdfFiles.SRC_FILE_3_PAGES);

        assertEquals("cannot capture second argument of file option",
                someFiles.get(1),
                GivenPdfFiles.SRC_FILE_4_PAGES);

        assertEquals("wrong length of file argument list",
                3,
                someFiles.size());
    }

    @Test
    public void recognizeShortBlankPageTextOption() throws Exception {

        argumentParser.transformArgsIntoOptions(new String[]{
                "-B", sampleBlankPageText,
                "-f", "./tmp/test.pdf"});

        assertTrue("Option for blank-page-text not recognized",
                argumentParser.getOptions().has("blankpagetext"));
    }


    @Test
    public void recognizeBlankPageTextOption() throws Exception {

        assertNotNull("argumentParser instance not initialized", argumentParser);

        argumentParser.transformArgsIntoOptions( new String[] {
                "--B", sampleBlankPageText,
                "-f", "./tmp/test.pdf" });

        assertTrue("Option for blank-page-text not recognized",
                argumentParser.getOptions().has("blankpagetext"));


        assertEquals("Argument of blankpagetext-option not recognized",
                sampleBlankPageText,
                argumentParser.getOptions().valueOf("B"));
        assertEquals("Argument of blankpagetext-option not recognized",
                sampleBlankPageText,
                argumentParser.getOptions().valueOf("blankpagetext"));
    }


    /**
     * tests whether the target-file option is recognized
     */
    @Test
    public void recognizeTargetFileOption() throws Exception {

        argumentParser.transformArgsIntoOptions( new String[] {
                "--target", sampleTargetFileName,
                "-f", "./tmp/test.pdf" });

        assertTrue("Option for targetfile not recognized",
                argumentParser.getOptions().has("targetfile"));

        assertTrue("(alternative) option for outputfile not recognized",
                argumentParser.getOptions().has("outputfile"));

        assertTrue("(alternative) for outfile not recognized",
                argumentParser.getOptions().has("outfile"));

        assertTrue("Option ('t') for targetfile not recognized",
                argumentParser.getOptions().has("t"));

        assertFalse("option for evenification was not present!",
                argumentParser.getOptions().has("evenify"));

        assertEquals("Argument of targetfile not recognized",
                sampleTargetFileName,
                argumentParser.getOptions().valueOf("targetfile"));
    }

    @Test
    public void allAlternativesAreRecognized() {

        argumentParser.transformArgsIntoOptions( new String[] {
                "-t", sampleTargetFileName,
                "-f", "./tmp/test.pdf" });

        // assert the -t option is recognized by ALL its alternative names
        // from PdfUtilRunner.java:
        // acceptsAll(asList("t", "target", "targetfile", "outputfile", "outfile"),

        assertTrue("alternative names for option 'targetfile' not recognized",
                argumentParser.getOptions().has("targetfile")
                        && argumentParser.getOptions().has("target")
                        && argumentParser.getOptions().has("outputfile")
                        && argumentParser.getOptions().has("t")
                        && argumentParser.getOptions().has("outfile")
        );

        assertFalse("other names for output are NOT recognized",
                argumentParser.getOptions().has("a")
                        || argumentParser.getOptions().has("result"));

    }

    @Test
    public void recognizeMixedCommandLine() {

        argumentParser.transformArgsIntoOptions(new String[]{
                "-targetfile", "./out.pdf",
                "-sourcefiles", "./s1.pdf:./s2.pdf",
                "-e"});

        assertTrue("option 'targetfile' not recognized",
                argumentParser.getOptions().has("targetfile"));

        assertTrue("option 'sourcefiles' not recognized",
                argumentParser.getOptions().has("sourcefiles"));

        assertTrue("opeion 'evenify' not recognized",
                argumentParser.getOptions().has("evenify"));

    }

    @Test
    public void evenifyOptionTrueIsRecognized() {
        argumentParser.transformArgsIntoOptions(new String[]{
                "-e", "true", "-f", "./tmp/test.pdf"});

        assertTrue("evenify = true is not recognized",
                argumentParser.getOptions().has("evenify"));

        assertEquals("Argument of evenify-option not recognized",
                true,
                argumentParser.getOptions().valueOf("evenify"));

    }

    @Test
    public void evenifyOptionFalseIsRecognized() {
        argumentParser.transformArgsIntoOptions(new String[]
               {"-e", "false", "-f", "./tmp/test.pdf"});

        assertTrue("evenify = false is not recognized",
                argumentParser.getOptions().has("evenify"));

        assertEquals("Argument of evenify-option not recognized",
                false,
                argumentParser.getOptions().valueOf("evenify"));

    }

}
