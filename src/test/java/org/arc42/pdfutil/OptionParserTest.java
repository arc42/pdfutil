package org.arc42.pdfutil;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.junit.Test;

import java.io.File;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class OptionParserTest {

    @Test
    public void recognizeOptionWithMultipleArguments() {
        String pathSeparatorChar = ":";

        OptionParser parser = new OptionParser();
        OptionSpec<File> path = parser.accepts("path").withRequiredArg().ofType(File.class)
                .withValuesSeparatedBy(pathSeparatorChar);

        OptionSet options = parser.parse("--path", "/tmp:/var:/opt");

        assertTrue(options.has(path));
        assertTrue(options.hasArgument(path));
        assertEquals("path option does not recognize its arguments",
                asList(new File("/tmp"), new File("/var"), new File("/opt")),
                options.valuesOf(path));
    }


    @Test
    public void supportsShortOptions() {
        OptionParser parser = new OptionParser("aB?*.");

        OptionSet options = parser.parse("-a", "-B", "-?");

        assertTrue(options.has("a"));
        assertTrue(options.has("B"));
        assertTrue(options.has("?"));
        assertFalse(options.has("."));
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
