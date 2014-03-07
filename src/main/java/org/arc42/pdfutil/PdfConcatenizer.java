/************************************************************************
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
 *********************************************************************** */

package org.arc42.pdfutil;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PdfConcatenizer {

    private java.util.List<String> sourceFiles;

    private String targetFileName;

    /*********
       evenify: add blank pages to files with odd pagecount,
       so that chapters always start on odd pages
     ********/
    private Boolean evenify;


    /*********
       Stuff needed to add blank pages (e.g. evenify)
     ********/
    private String textToPrintOnBlankPage;
    private PdfReader blankReader;


    private static final Logger LOGGER = Logger.getLogger(PdfUtilRunner.class.getName());


    /**
     * constructor with PdfProcessingOptions instance
     * @param processingOptions
     */
    public PdfConcatenizer( PdfProcessingOptions processingOptions ) {
        super();

        sourceFiles = processingOptions.getSourceFiles();
        targetFileName = processingOptions.getTargetFile();
        evenify = processingOptions.isEvenify();
        textToPrintOnBlankPage = processingOptions.getBlankPageText();

        this.initBlankPagePdf();
    }



    public PdfConcatenizer(List<String> pSourceFiles,
                           String pTargetFileName,
                           Boolean pEvenify,
                           String pTextToPrintOnBlankPages) {

        sourceFiles = pSourceFiles;
        evenify = pEvenify;
        targetFileName = pTargetFileName;

        textToPrintOnBlankPage = pTextToPrintOnBlankPages;

        this.initBlankPagePdf();
    }


    /**
     * creates a Pdf file with just a single page
     * which will eventually be inserted when we need an EMPTY (blank) page!
     */

    public final void initBlankPagePdf() {
        Document blankDocument = new Document();

        int fontsize = 40;

        try {
            PdfWriter.getInstance(blankDocument, new FileOutputStream("./blankpage_tmp.pdf"));
            blankDocument.open();

            Paragraph paragraph = new Paragraph();
            addBlankLines(paragraph, 5);

            Font font = new Font(Font.FontFamily.HELVETICA,
                    fontsize,
                    Font.BOLDITALIC,
                    BaseColor.LIGHT_GRAY);

            // write the blankPageText in big letters
            paragraph.setAlignment(Element.ALIGN_CENTER);

            if (textToPrintOnBlankPage == null) {
                textToPrintOnBlankPage = "intentionally left blank" ;

            }
            paragraph.add(
                    new Pa    new Paragraph(textToPrintOnBlankPage,
                    font));ragraph(textToPrintOnBlankPage,
                            font));


            blankDocument.add(paragraph);
            blankDocument.close();

            blankReader = new PdfReader("./blankpage_tmp.pdf");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception: empty page pdf (needed to evenify) could not be generated: ", e);
        }
    }


    /**
     * concats all files given in sourceFile
     */
    public void concatFiles() {
        Document document = new Document();
        try {
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(targetFileName));
            document.open();
            PdfReader reader;
            int nrOfPagesInCurrentFile;
            for (int i = 0; i < sourceFiles.size(); i++) {
                reader = new PdfReader(sourceFiles.get(i));

                nrOfPagesInCurrentFile = reader.getNumberOfPages();
                for (int page = 0; page < nrOfPagesInCurrentFile; ) {
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }

                if (evenify && (nrOfPagesInCurrentFile % 2 == 1)) {
                    addBlankPage(copy);
                }

            }
            document.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception: wrong file you gave me, Yoda muttered...", e);
        } catch (BadPdfFormatException e) {
            LOGGER.log(Level.SEVERE, "Exception: Encountered bad pdf format", e);
        } catch (DocumentException e) {
            LOGGER.log(Level.SEVERE, "Exception: Something bad happened to the output document.", e);
        }

    }


    /**
     * addBlankPage adds an empty page (e.g. "deliberately left blank") to current PdfCopy instance
     * (usually at the end of odd-paged files to achieve "evenification" (even number of
     * pages in every processed file)
     *
     * @param copy where the blank page is added to
     */
    private void addBlankPage(PdfCopy copy) throws DocumentException, IOException {
        copy.addPage(copy.getImportedPage(blankReader, 1));

        // the following lines would add an "EMPTY" page, with NO text on it
        // copy.newPage();
        // copy.addPage(PageSize.A4, 0);


    }


    // thanx to Lars Vogel for the idea to the following method
    private static void addBlankLines(Paragraph paragraph, int nrOfLines) {
        for (int i = 0; i < nrOfLines; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /*
    boring getter and setter stuff
     */
    public Boolean getEvenify() {
        return evenify;
    }

    public void setEvenify(Boolean evenify) {
        this.evenify = evenify;
    }


    public List<String> getSourceFiles() {
        return sourceFiles;
    }

    public void setSourceFiles( List<String> sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }


}