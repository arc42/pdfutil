package org.arc42.pdfutil;

import java.util.List;

/**
 * Holds all the options required for processing pdf files:
 * <p>
 * <ul>
 * <li>source file set</li>
 * <li>target file set</li>
 * <li>evenify / add blank page option</li>
 * <li>blank page text</li>
 * <li>secure file agains printing, opening or modification</li>
 * </ul>
 * </p>
 */
public class PdfProcessingOptions {

    protected static String DEFAULT_TARGET_FILE = "./_pdfutil_out.pdf";
    protected static Boolean DEFAULT_EVENIFY = Boolean.TRUE;
    protected static String DEFAULT_BLANKPAGETEXT = "Page intentionally left blank";


    // attributes related to Pdf processing
    private String targetFile;

    private List<String> sourceFiles;
    private boolean evenify;
    private String blankPageText;


    // has user given help or version command?
    private boolean helpRequested;
    private boolean versionRequested;


    // the fluent interface
    // ===================================
    public PdfProcessingOptions() {
        targetFile = DEFAULT_TARGET_FILE;
        evenify = DEFAULT_EVENIFY;
        blankPageText = DEFAULT_BLANKPAGETEXT;

        helpRequested = false;
        versionRequested = false;
    }


    public PdfProcessingOptions withTargetFile(String tf) {
        this.targetFile = tf;
        return this;
    }

    public PdfProcessingOptions withEvenify(Boolean evenify) {
        this.evenify = evenify;
        return this;
    }

    public PdfProcessingOptions withSourceFiles(List<String> someFiles) {
        this.sourceFiles = someFiles;
        return this;
    }


    public PdfProcessingOptions withBlankPageText(String blankPageText) {
        this.blankPageText = blankPageText;
        return this;
    }


    // the simple stuff
    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public List<String> getSourceFiles() {
        return sourceFiles;
    }

    public void setSourceFiles(List<String> sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    public boolean isEvenify() {
        return evenify;
    }

    public void setEvenify(boolean evenify) {
        this.evenify = evenify;
    }

    public String getBlankPageText() {
        return blankPageText;
    }

    public void setBlankPageText(String blankPageText) {
        this.blankPageText = blankPageText;
    }


    /**
     * user included "?" or "help" option in argument list
     */
    public boolean isHelpRequested() {
        return helpRequested;
    }

    /**
     * user included "-version" option in argument list
     */
    public boolean isVersionRequested() {
        return versionRequested;
    }


    /**
     * concat required if more than one source file given
     * @return true/false
     */
    public boolean concatRequested() {
        return (sourceFiles.size() >1) ;
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


