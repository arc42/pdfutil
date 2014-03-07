package org.arc42.pdfutil;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

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

public class ArgumentParser {


    private OptionSet options;
    private OptionParser parser;

    private static final Logger LOGGER = Logger.getLogger(ArgumentParser.class.getName());

    // need this for testing
    protected OptionSet getOptions() {
        return options;
    }

    // need this for testing
    protected OptionParser getParser() {
        return parser;
    }



    protected ArgumentParser() {
        super();


    }


    /**
     * setupAllowedOptions configures the JOptSimple parser
     */
    public final void setupAllowedOptions() {
       /* isn't that awesome: a fluent interface to describe the optional and required command line parameters?
        *  and it even generates the help...
        */

        parser = new OptionParser() {
            {
                /* required arguments                       */
                /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
                acceptsAll(asList("f", "files", "sourcefiles"),
                        "non-empty list of source/input files to be processed, separated by ':'")
                        .withRequiredArg()
                        .required()
                        .describedAs("path1:" + ',' + "path2:...")
                        .ofType(String.class)
                        .withValuesSeparatedBy(':');

                acceptsAll(asList("t", "target", "targetfile", "outputfile", "outfile"),
                        "The output pdf file created by concatenation, encryption or metadata-enrichment")
                        .withRequiredArg()
                        .ofType(String.class)
                        .defaultsTo(PdfProcessingOptions.DEFAULT_TARGET_FILE);


                /* optional arguments */
                /* ================== */
                acceptsAll(asList("e", "evenify"),
                        "Ensure that every input file has an even number of pages, so the following file starts on a RIGHT page")
                        .withOptionalArg()
                        .ofType(Boolean.class)
                        .defaultsTo(PdfProcessingOptions.DEFAULT_EVENIFY)
                        .describedAs("evenify true/false (-e without arg defaults to true)");


                acceptsAll(asList("blankpagetext", "b", "B"),
                        "Text to be printed on (empty)pages added by evenification")
                        .withRequiredArg()
                        .ofType(String.class)
                        .defaultsTo(PdfProcessingOptions.DEFAULT_BLANKPAGETEXT);

                /* help and info arguments */
                /* ----------------------- */
                acceptsAll(asList("h", "?"),
                        "show help")
                        .forHelp();

                acceptsAll(asList("v", "version", "chatty"),
                        "be verbose about version and other technical detail");

            }
        };
    }

    /**
     * evaluate the arguments given to options:
     * It analyses the options and dispatches required actions.
     */
    @SuppressWarnings("unchecked")
    private PdfProcessingOptions determineProcessingOptions() {

        PdfProcessingOptions processingOptions = new PdfProcessingOptions();

        if (options.has("targetfile")) {
            processingOptions.setTargetFile((String) options.valueOf("targetfile"));

            LOGGER.log(Level.INFO, "target file: " + processingOptions.getTargetFile());
        }

        if (options.has("blankpagetext")) {
            processingOptions.setBlankPageText((String) options.valueOf("blankpagetext"));
        }

        if (options.has("evenify")) {
            processingOptions.setEvenify((Boolean) options.valueOf("evenify"));
        }


        if (options.has("sourcefiles")) {
            processingOptions.setSourceFiles((List<String>) options.valuesOf("sourcefiles"));
            LOGGER.log(Level.INFO, "source files: " + processingOptions.getSourceFiles());

        }

        return processingOptions;
    }


    /**
     * print help - using JoptSimple library to generate help from descriptions of parser arguments.
     */
    protected void printHelp() {
        try {
            // make sure we exit processing
            parser.printHelpOn(System.out);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception: help requested but could not be given: ", e);
        }
    }


    /**
     * has user given the help command?
     *
     * @return true/false
     */
    protected boolean containHelpRequest() {
        return options.has("?");
    }

    /**
     * has user given the version command?
     *
     * @return true/false
     */
    protected boolean wasVersionInfoRequested() {
        return options.has("version");
    }


    /**
     * parse the given argument list, return an instance of PdfProcessingOptions
     *
     * @param args: the arguments given on the command line
     * @return
     */
    public PdfProcessingOptions transformArgsIntoOptions(String[] args) {
        // what arguments are allowed?
        setupAllowedOptions();

        // parse the arguments given
        options = parser.parse(args);

        // summarize the results in options instance
        return determineProcessingOptions();

    }
}
