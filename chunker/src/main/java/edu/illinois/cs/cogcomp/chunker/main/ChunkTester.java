/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computation Group University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package edu.illinois.cs.cogcomp.chunker.main;

import edu.illinois.cs.cogcomp.chunker.main.lbjava.ChunkLabel;
import edu.illinois.cs.cogcomp.chunker.main.lbjava.Chunker;
import edu.illinois.cs.cogcomp.chunker.utils.CoNLL2000Parser;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.lbjava.nlp.seg.BIOTester;
import edu.illinois.cs.cogcomp.lbjava.parse.ChildrenFromVectors;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;

import java.net.URL;

import static org.junit.Assert.assertNotNull;


/**
 * This class may be used to produce a detailed report of the <i>phrase by phrase</i> performance of
 * {@link Chunker} on given testing data. {@link Chunker} must exist before attempting to compile
 * this code.
 *
 * <h3>Usage</h3> <blockquote><code>
 *   java edu.illinois.cs.cogcomp.chunker.main.ChunkTester &lt;test data&gt;
 *                                                      [&lt;parser&gt;]
 * </code></blockquote>
 *
 * <h3>Input</h3>
 * <p>
 * The first command line parameter should be filled in with the name of a file containing labeled
 * testing data. The optional second parameter is the name of a <code>parse.Parser</code> whose
 * constructor takes the name of a file as a <code>String</code> as input and that produces
 * <code>parse.LinkedVector</code> objects representing sentences. When omitted, the default is
 * {@link CoNLL2000Parser}.
 *
 * <h3>Output</h3>
 * The output is generated by the <code>classify.TestDiscrete</code> class.
 *
 * @author Nick Rizzolo
 **/
public class ChunkTester {
    public static void chunkTester(String testFile){
        Parser parser;
        parser = new CoNLL2000Parser(testFile);
        BIOTester tester =
                new BIOTester(new Chunker(), new ChunkLabel(), new ChildrenFromVectors(parser));
        tester.test().printPerformance(System.out);
    }
    public static void main(String[] args){
        ResourceManager rm = new ChunkerConfigurator().getDefaultConfig();
        String testFileName = rm.getString("testGoldPOSData");
        String testNoPOSFileName = rm.getString("testNoPOSData");

        System.out.println("\nWith Gold POS");
        chunkTester(testFileName);

        System.out.println("\nWith NO POS");
        chunkTester(testNoPOSFileName);
    }
    /*public static void main(String[] args) {
        ResourceManager rm = new ChunkerConfigurator().getDefaultConfig();
        String testFileName = rm.getString("testGoldPOSData");
        String testNoPOSFileName = rm.getString("testNoPOSData");

        URL testFileURL = ChunkTester.class.getClassLoader().getResource(testFileName);
        assertNotNull("Test file missing", testFileURL);
        String testFile = testFileURL.getFile();

        URL testNoPOSFileURL = ChunkTester.class.getClassLoader().getResource(testNoPOSFileName);
        assertNotNull("Test file missing", testNoPOSFileURL);
        String testNoPOSFile = testNoPOSFileURL.getFile();
        Parser parser;

        System.out.println("\nWith Gold POS");

        parser = new CoNLL2000Parser(testFile);

        BIOTester tester =
                new BIOTester(new Chunker(), new ChunkLabel(), new ChildrenFromVectors(parser));
        tester.test().printPerformance(System.out);

        System.out.println("\nWith NO POS");

        parser = new CoNLL2000Parser(testNoPOSFile);

        tester = new BIOTester(new Chunker(), new ChunkLabel(), new ChildrenFromVectors(parser));
        tester.test().printPerformance(System.out);
    }*/
}
