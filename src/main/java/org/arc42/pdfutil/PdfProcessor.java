package org.arc42.pdfutil;


public class PdfProcessor {


    /**
     * constructor
     */
    public PdfProcessor( ) {
        super();

    }

    /**
     * dispatches to the functions selected via command line options:
     * - concatenate, if more than one source file was given
     * - add pdf security, if this was requested
     * @param options
     */
    public void dispatch( PdfProcessingOptions options ) {

        if (options.concatRequested()) {
            PdfConcatenizer concatenizer = new PdfConcatenizer(options);
            concatenizer.concatFiles();
        }





    }
}

