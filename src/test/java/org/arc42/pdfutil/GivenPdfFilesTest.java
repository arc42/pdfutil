package org.arc42.pdfutil;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GivenPdfFilesTest {


    @Test
    public void testIfProvidedPdfFilesHaveCorrectPageCount() {
        String PAGECOUNTMESSAGE = "provided pdf file sample-A4-portrait-";

        assertEquals( PAGECOUNTMESSAGE + "3pgs.pdf shall have 3 pages",
                3, PdfChecker.countPagesInPdf( GivenPdfFiles.SRC_FILE_3_PAGES ));

        assertEquals( PAGECOUNTMESSAGE + "4-pgs.pdf shall have 4 pages",
                4, PdfChecker.countPagesInPdf( GivenPdfFiles.SRC_FILE_4_PAGES ));

        assertEquals( PAGECOUNTMESSAGE + "11-pgs.pdf shall have 11 pages",
                11, PdfChecker.countPagesInPdf( GivenPdfFiles.SRC_FILE_11_PAGES ));
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