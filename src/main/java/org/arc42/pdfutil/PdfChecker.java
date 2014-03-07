package org.arc42.pdfutil;

import com.itextpdf.text.pdf.PdfReader;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to perform various checks on pdf files, e.g. count pages, determine pagesize etc.
 * TODO: enhance to work on intermediate pdf representations (instead of files) too.
 */
public class PdfChecker {

    private static final Logger LOGGER = Logger.getLogger(PdfChecker.class.getName());


    /**
     * private constructor to hide the implicit public one...
     */
    private PdfChecker() {
        // Utility classes, which are a collection of static members, are not meant to be instantiated.
        // They should therefore not have public constructors.
    }


    static int countPagesInPdf(String candidate) {

        // LOGGER.log(Level.INFO, System.getProperty("user.dir"));

        PdfReader reader = null;
        try {
            reader = new PdfReader(candidate);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception: file not found - therefore I cannot count pages", e);
        }

        return reader.getNumberOfPages();
    }


}

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


