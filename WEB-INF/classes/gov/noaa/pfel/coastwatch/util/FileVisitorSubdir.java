/* 
 * FileVisitorSubdir Copyright 2014, NOAA.
 * See the LICENSE.txt file in this file's directory.
 */
package gov.noaa.pfel.coastwatch.util;

import com.cohort.array.StringArray;
import com.cohort.util.File2;
import com.cohort.util.MustBe;
import com.cohort.util.String2;
import com.cohort.util.Test;

import gov.noaa.pfel.coastwatch.pointdata.Table;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class gathers a list of subdir names (including initial dir).
 *
 * @author Bob Simons (bob.simons@noaa.gov) 2014-12-29
 */
public class FileVisitorSubdir extends SimpleFileVisitor<Path> {

    /**
     * Set this to true (by calling verbose=true in your program, 
     * not by changing the code here)
     * if you want lots of diagnostic messages sent to String2.log.
     */
    public static boolean verbose = false; 
    public static boolean reallyVerbose = false; 

    /** things set by constructor */
    public String dir;  //with \\ or / separators. With trailing slash (to match).

    public ArrayList<Path> results = new ArrayList();


    /** 
     * The constructor.
     * Usage: see useIt().
     *
     * @param tDir The starting directory, with \\ or /, with or without trailing slash.  
     */
    public FileVisitorSubdir(String tDir) {
        super();
        dir = File2.addSlash(tDir);
    }

    /** Invoked before entering a directory. */
    public FileVisitResult preVisitDirectory(Path tPath, BasicFileAttributes attrs)
        throws IOException {
        
        results.add(tPath);
        return FileVisitResult.CONTINUE;    
    }


    /** Invoked for a file that could not be visited. */
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        //2015-03-10 I added this method to override the superclass
        //which apparently throws the exception and stops the parent
        //SimpleFileVisitor. This class just ignores the error.
        //A one time test that this change solves the problem: call
        //    new FileVisitorSubdir("/") 
        //  on my Windows computer with message below enabled.
        //Always show message here. It is useful information.     (message is just filename)
        String2.log("WARNING: FileVisitorSubdir.visitFileFailed: " + exc.getMessage());
        return FileVisitResult.CONTINUE;    
    }

    /** 
     * A convenience method for using this class. 
     *
     * @param tDir The starting directory, with \\ or /, with or without trailing slash.  
     * @return a StringArray with dir and subdir names 
     *   (with the OS's slashes -- \\ for Windows!).
     */
    public static StringArray oneStep(String tDir) 
        throws IOException {
        long time = System.currentTimeMillis();

        //Is it an S3 bucket with "files"?
        //http://docs.aws.amazon.com/AmazonS3/latest/API/RESTBucketGET.html
        if (File2.addSlash(tDir).matches(String2.AWS_S3_REGEX)) { //forcing trailing slash avoids problems
            Table table = FileVisitorDNLS.oneStep(tDir, "/", //regex that won't match any files 
                true, true); //tRecursive, tDirectoriesToo
            StringArray dirNames = (StringArray)table.getColumn(FileVisitorDNLS.DIRECTORY);
            return dirNames;
        }            

        //do local file system
        FileVisitorSubdir fv = new FileVisitorSubdir(tDir);
        Files.walkFileTree(FileSystems.getDefault().getPath(tDir), fv);
        int n = fv.results.size();
        StringArray dirNames = new StringArray(n, false);
        for (int i = 0; i < n; i++)
            dirNames.add(fv.results.get(i).toString());
        if (verbose) String2.log("FileVisitorSubdir.oneStep finished successfully. n=" + 
            n + " time=" + (System.currentTimeMillis() - time));
        return dirNames;
    }

    /** 
     * This tests a local file system. 
     */
    public static void testLocal() throws Throwable {
        String2.log("\n*** FileVisitorSubdir.testLocal");
        verbose = true;
        String contextDir = SSR.getContextDirectory(); //with / separator and / at the end
        StringArray alps;
        long time;

        alps = oneStep(contextDir + "WEB-INF/classes/com/cohort"); 
        String results = alps.toNewlineString();
        String expected = 
"C:\\programs\\tomcat\\webapps\\cwexperimental\\WEB-INF\\classes\\com\\cohort\n" +
"C:\\programs\\tomcat\\webapps\\cwexperimental\\WEB-INF\\classes\\com\\cohort\\array\n" +
"C:\\programs\\tomcat\\webapps\\cwexperimental\\WEB-INF\\classes\\com\\cohort\\ema\n" +
"C:\\programs\\tomcat\\webapps\\cwexperimental\\WEB-INF\\classes\\com\\cohort\\util\n";
        Test.ensureEqual(results, expected, "results=\\n" + results);

        alps = oneStep(String2.replaceAll(contextDir + "WEB-INF/classes/com/cohort/", '/', '\\')); 
        results = alps.toNewlineString();
        Test.ensureEqual(results, expected, "results=\n" + results);

        String2.log("\n*** FileVisitorSubdir.testLocal finished.");
    }

    /** 
     * This tests an Amazon AWS S3 file system.
     * Your S3 credentials must be in 
     * <br> ~/.aws/credentials on Linux, OS X, or Unix
     * <br> C:\Users\USERNAME\.aws\credentials on Windows
     * See http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-setup.html .
     */
    public static void testAWSS3() throws Throwable {
        String2.log("\n*** FileVisitorSubdir.testAWSS3");
        try {

        verbose = true;
        String contextDir = SSR.getContextDirectory(); //with / separator and / at the end
        StringArray alps;
        long time;

        alps = oneStep(
            "http://nasanex.s3.amazonaws.com/NEX-DCP30/BCSD/rcp26/mon/atmos/tasmin/r1i1p1/v1.0/"); 
        String results = alps.toNewlineString();
        String expected = 
"http://nasanex.s3.amazonaws.com/NEX-DCP30/BCSD/rcp26/mon/atmos/tasmin/r1i1p1/v1.0/\n" +
"http://nasanex.s3.amazonaws.com/NEX-DCP30/BCSD/rcp26/mon/atmos/tasmin/r1i1p1/v1.0/CONUS/\n";
        Test.ensureEqual(results, expected, "results=\n" + results);

        String2.log("\n*** FileVisitorSubdir.testAWSS3 finished.");

        } catch (Throwable t) {
            String2.pressEnterToContinue(MustBe.throwableToString(t) + 
                "\nUnexpected error.  (Did you create your AWS S3 credentials file?)"); 
        }
    }

    /**
     * This tests the methods in this class.
     *
     * @throws Throwable if trouble
     */
    public static void test() throws Throwable {
        String2.log("\n****************** FileVisitorSubdir.test() *****************\n");
/* */
        //always done        
        testLocal();
        testAWSS3();
    }


}