package org.arc42.pdfutil;


import org.junit.Test;


import static junit.framework.Assert.assertNotNull;

public class PdfProcessingOptionsTest {

    PdfProcessingOptions processingOptions;

    @Test
    public void testFluentProcessingOptionInterface() {
        processingOptions = new PdfProcessingOptions()
                .withTargetFile("./test/targetfile.pdf")
                .withBlankPageText( "blankpagetext")
                .withEvenify( true );
                //.withSourceFiles( )

        assertNotNull("ProcessingOptions should not be null", processingOptions);

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


