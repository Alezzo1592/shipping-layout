import org.junit.Test;

import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by agonzal on 19/11/2018.
 */
public class LoaderTest {
    static String TEST_4x2x2_0_ENTROPY="Floor4X2X2_with_3_items";
    static String DEFAULT="";
    static String TEST_4x2x2_HAS_ENTROPY = "Floor4X2X2_with_3_items_has_entropy";
    static String TEST_3x2x2_TEST_DUMMY_ALGORITHM = "Floor3X2X2_6_items";

    @Test
    public void testLoader(){
        Loader loaderToTest = startLoader(DEFAULT);
        assertTrue(0 == loaderToTest.getEntropy());
    }

    private Loader startLoader (String loadFile){
        Loader loaderToTest = new Loader();
        try {
            if(loadFile.isEmpty())
                loaderToTest.loadData();
            else
                loaderToTest.loadData(loadFile);;
        }catch (Throwable t ){
            System.out.println(t.getMessage());
            System.out.println(t.getStackTrace());
            fail("Error To load default file");
        }
        return loaderToTest;
    }

    @Test
    public void testLoaderWithOnlyTypeInEveryStreetCalculateEntropyShouldBe0(){
        Loader loaderToTest = startLoader(TEST_4x2x2_0_ENTROPY);
        assertTrue(0 == loaderToTest.getEntropy());
    }

    @Test
    public void testLoaderWithNotOnlyTypeInEveryStreetCalculateEntropyShouldBeMoreThan0(){
        Loader loaderToTest = startLoader(TEST_4x2x2_HAS_ENTROPY);
        double entropy =loaderToTest.getEntropy();
        assertEquals(1,entropy,0);
    }

    private String parseEntropy(double entropy){
        DecimalFormat df2 = new DecimalFormat(".####");

        return df2.format(entropy);
    }

    @Test
    public void testLoaderTestingDummyAlgorithm(){
        Loader loaderToTest = startLoader(TEST_3x2x2_TEST_DUMMY_ALGORITHM);

        String entropy =parseEntropy (loaderToTest.getEntropy());
        assertTrue("1.9183".equals(entropy));

        List<ItemPlace> placeItemsToAdd =  loaderToTest.dummyEntry();

        try {
            loaderToTest.addItems(placeItemsToAdd);
        }catch (InvalidSubLevelException e){
            fail("Error to add item in loader Floor");
        }
        entropy =parseEntropy (loaderToTest.getEntropy());
        assertFalse("1.9183".equals(entropy));
        Double parseEntropy = Double.parseDouble(entropy);
        assertTrue(1.9183 < parseEntropy);
        assertTrue("3.585".equals(entropy));

    }

    @Test
    public void testLoaderTestingFirstHeuristic(){
        Loader loaderToTest = startLoader(TEST_3x2x2_TEST_DUMMY_ALGORITHM);

        String entropy =parseEntropy (loaderToTest.getEntropy());
        assertTrue("1.9183".equals(entropy));

        List<ItemPlace> placeItemsToAdd =  loaderToTest.generateHeuristicAssignment();

        try {
            loaderToTest.addItems(placeItemsToAdd);
        }catch (InvalidSubLevelException e){
            fail("Error to add item in loader Floor");
        }
        entropy =parseEntropy(loaderToTest.getEntropy());
        assertFalse("1.9183".equals(entropy));
        Double parseEntropy = Double.parseDouble(entropy);
        assertTrue(1.9183 < parseEntropy);
        assertTrue("4.0882".equals(entropy));
    }

}