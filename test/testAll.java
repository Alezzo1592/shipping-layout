import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;

/**
 * Created by agonzal on 16/12/2018.
 */
public class testAll {
    static String TEST_4x2x2_0_ENTROPY="Floor4X2X2_with_3_items";
    static String DEFAULT="";
    static String TEST_4x2x2_HAS_ENTROPY = "Floor4X2X2_with_3_items_has_entropy";
    static String TEST_3x2x2_TEST_DUMMY_ALGORITHM = "Floor3X2X2_6_items";
    static String TEST100x7x60_WITH_10_PORCENT="random_10%_110x7x60.txt";
    static String TEST100x7x60_WITH_35_PORCENT = "random_35%_100x7x60.txt";
    static String TEST100x7x60_WITH_70_PORCENT = "random_70%_100x7x60.txt";
    static String ENTRY_OF_15_ITEMS = "random_entry_with_15_items.txt";
    static String ENTRY_OF_25_ITEMS = "random_entry_with_25_items.txt";
    static String ENTRY_OF_40_ITEMS = "random_entry_with_40_items.txt";
    static String ENTRY_OF_50_ITEMS = "random_entry_with_50_items.txt";
    static String ENTRY_OF_75_ITEMS = "random_entry_with_75_items.txt";
    static String ENTRY_OF_100_ITEMS = "random_entry_with_100_items.txt";

    private static Loader startLoader (String loadFile){
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

    private static String parseEntropy(double entropy){
        DecimalFormat df2 = new DecimalFormat(".####");

        return df2.format(entropy);
    }
    @Test
    public void testModifyNumberWithOutChangeToStop (){
        Loader loaderToTest = startLoader(TEST100x7x60_WITH_70_PORCENT);


        long startTime=System.currentTimeMillis();
        List<ItemPlace> newItemsToAdd = loaderToTest.generateHeuristicAssignment(ENTRY_OF_100_ITEMS);
        long endTime = System.currentTimeMillis();
        long milliHeurisitcEntry = endTime-startTime;

        List<ItemPlace> anotherItemsToAdd = new ArrayList<>(newItemsToAdd);
        System.out.println("heuristic");
        double heuristicEntropy = loaderToTest.calculateEntropy(anotherItemsToAdd);
        anotherItemsToAdd = new ArrayList<>(newItemsToAdd);
        loaderToTest.MAX_SIZE_TABOO_SEARCH =3;
        System.out.println("MAX_NUMBER_5");

        startTime=System.currentTimeMillis();
        List<ItemPlace> dummyPlacesEntry =  loaderToTest.getBetterList(anotherItemsToAdd);
        endTime = System.currentTimeMillis();
        long milliMaxNumber5 = endTime-startTime;

        double numberOfSteps5=loaderToTest.calculateEntropy(dummyPlacesEntry);
        anotherItemsToAdd = new ArrayList<>(newItemsToAdd);
        loaderToTest.MAX_SIZE_TABOO_SEARCH = 7;
        System.out.println("MAX_NUMBER_10");
        startTime=System.currentTimeMillis();
        double numberOfSteps10=loaderToTest.calculateEntropy(loaderToTest.getBetterList(anotherItemsToAdd));
        endTime = System.currentTimeMillis();
        long milliMaxNumber10 = endTime-startTime;

        anotherItemsToAdd = new ArrayList<>(newItemsToAdd);
        loaderToTest.MAX_SIZE_TABOO_SEARCH = 10;
        System.out.println("MAX_NUMBER_20");
        startTime=System.currentTimeMillis();
        double numberOfSteps20=loaderToTest.calculateEntropy(loaderToTest.getBetterList(anotherItemsToAdd));
        endTime = System.currentTimeMillis();
        long milliMaxNumber20 = endTime-startTime;

        anotherItemsToAdd = new ArrayList<>(newItemsToAdd);
        loaderToTest.MAX_SIZE_TABOO_SEARCH = 15;
        System.out.println("MAX_NUMBER_30");
        startTime=System.currentTimeMillis();
        double numberOfSteps30 = loaderToTest.calculateEntropy(loaderToTest.getBetterList(anotherItemsToAdd));
        endTime = System.currentTimeMillis();
        long milliMaxNumber30 = endTime-startTime;



        System.out.println("\nMAX_NUMBER_OF_STEP_WITHOUT_BE_BETTER: 3");
        System.out.println(numberOfSteps5);
        System.out.println("time:");
        System.out.println(milliMaxNumber5);

        System.out.println("\nMAX_NUMBER_OF_STEP_WITHOUT_BE_BETTER: 7");
        System.out.println(numberOfSteps10);
        System.out.println("time:");
        System.out.println(milliMaxNumber10);

        System.out.println("\nMAX_NUMBER_OF_STEP_WITHOUT_BE_BETTER: 10");
        System.out.println(numberOfSteps20);
        System.out.println("time:");
        System.out.println(milliMaxNumber20);


        System.out.println("\nMAX_NUMBER_OF_STEP_WITHOUT_BE_BETTER: 15");
        System.out.println(numberOfSteps30);
        System.out.println("time:");
        System.out.println(milliMaxNumber30);
    }
}
