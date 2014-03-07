## Todo (for Tiny Pdf Util)


### High Prio

* concat a number of Pdf files into single Pdf, filenames given as command line arguments

* dispatch after recognizing command line options

* secure Pdf file
    * add metadata (author, version etc.)
    * encrypt Pdf with password (disable pdf manipulation or content-copy, allow only printing)
     
* ensure pdfutil can be used from command line 



### Low Prio

* add check command to check for page dimensions of multiple files (in case all pages of the resulting document need to have the same pagesize)
* add support for non-A4 dimensions
* add support for pagecount, footer and header


### Completed

* Add (Apache style) open source license (partially completed)
* added command line parser (JOptSimple) and the following options:
     * -h(help)
     * -? (or ?)
     * -e(venify): add blank page at the end of files with odd pagecount
     * -t(arget): give name of target file
     * -v(ersion): print version number
     * -f(iles): comma separated list of source files
     * -b(lankpagetext): text to be printed on BLANK pages

    