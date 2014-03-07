/* ***********************************************************************
 * This is free software - without ANY guarantee!
 *
 *
 * Copyright 2013, Dr. Gernot Starke, arc42.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ********************************************************************** */

/**
 * Main class for PdfUtils, concatenation and encryption of pdf files.
 *
 * Parses the command line arguments:
 *
 * -f(iles)
 * -f
 *
 * @author Gernot Starke
 * Created with IntelliJ IDEA.
 */

package org.arc42.pdfutil;


import java.util.logging.Level;
import java.util.logging.Logger;

public class PdfUtilRunner {

    private static final String PDF_UTIL_NO_GUARANTEE = "PdfUtilRunner is free software and comes with ABSOLUTELY NO guarantee, use on your own risk.";
    private static String versionId = "0.3.2";

    private static final Logger LOGGER = Logger.getLogger(PdfUtilRunner.class.getName());


    /**
     * constructor does nothing helpful
     */
    private PdfUtilRunner() {
        super();

    }


    // we're able to supply version info
    private static void printVersionInfo() {
        LOGGER.log(Level.INFO, PDF_UTIL_NO_GUARANTEE);
        LOGGER.log(Level.INFO, "Version: " + versionId);
    }



    public static void main(String[] args) {

        PdfProcessingOptions processingOptions;

        ArgumentParser parser = new ArgumentParser();

        // parse options from command line
        // set the options to pdfProcessingOptions instance
        processingOptions = parser.transformArgsIntoOptions(args);


        // did the user requested help?
        if (processingOptions.isHelpRequested()) {
            parser.printHelp();
        }

        // did the user ask for version info?
        if (processingOptions.isVersionRequested()) {
            printVersionInfo();
        }




        // dispatch further work (concatenation, security)
        new PdfProcessor().dispatch(processingOptions);

            // help or version info has been requested OR
            // source file info missing
            LOGGER.log(Level.INFO, "aborting pdfutil...");


    }


}

