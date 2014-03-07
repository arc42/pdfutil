package org.arc42.pdfutil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

public class PdfConcatenizerTest  {


    private static String TMP_OUT_FILE = "./test/out_test.pdf";

    private PdfConcatenizer myWorker;

    private List<String> srcFiles = new ArrayList();


    @Before
    public void setUp() throws Exception {

        File myFile = new File( TMP_OUT_FILE );
        if(myFile.exists())
            myFile.delete();


        srcFiles.add( 0, GivenPdfFiles.SRC_FILE_3_PAGES);
        srcFiles.add( 1, GivenPdfFiles.SRC_FILE_4_PAGES);
        srcFiles.add( 2, GivenPdfFiles.SRC_FILE_11_PAGES);

        myWorker = new PdfConcatenizer(srcFiles, TMP_OUT_FILE, Boolean.TRUE,
                "Page intentionally left blank");
        myWorker.initBlankPagePdf();
    }

    @After
    public void tearDown() {


    }



    @Test
    public void testConcatFilesInitialization() throws Exception {

        assertNotNull("myWorker instance not initialized", myWorker);

        assertEquals("expect three source files",
                myWorker.getSourceFiles().size(), 3);

        assertEquals("expect evenify == TRUE", myWorker.getEvenify(), true);
    }


    @Test
    public void testConcatThreeFiles() throws Exception {

        myWorker.setTargetFileName(TMP_OUT_FILE);

        myWorker.setEvenify(false);
        myWorker.concatFiles();

        // 3pgs + 4pgs + 11pgs shall have 18 pages
        assertEquals( "resulting pdf(3 files, no evenify) shall have 18 pages",
                18, PdfChecker.countPagesInPdf( TMP_OUT_FILE ));

        myWorker.setEvenify(true);
        myWorker.concatFiles();

        // with evenify: (3pgs + 1) + 4pgs + (11pgs +1) shall have 20 pages
        assertEquals( "resulting pdf(3 files, with evenify) shall have 20 pages",
                20, PdfChecker.countPagesInPdf( TMP_OUT_FILE ));

    }


    @Test
    public void testInitializeViaProcessingOptions() {

        PdfProcessingOptions processingOptions = new PdfProcessingOptions()
                .withTargetFile(TMP_OUT_FILE)
                .withSourceFiles( srcFiles  )
                .withBlankPageText("blankpagetext")
                .withEvenify(true);


        assertEquals( "processingOptions shall contain correct input file #0",
                processingOptions.getSourceFiles().get(0),
                srcFiles.get(0));

        PdfConcatenizer concatenizer = new PdfConcatenizer( processingOptions );

        assertNotNull( "concatenizer shall not be null", concatenizer );

        concatenizer.concatFiles();

        // with evenify: (3pgs + 1) + 4pgs + (11pgs +1) shall have 20 pages
        assertEquals( "resulting pdf(3 files, with evenify) shall have 20 pages",
                20, PdfChecker.countPagesInPdf( TMP_OUT_FILE ));

    }


}
