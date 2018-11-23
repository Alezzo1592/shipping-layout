import org.junit.Test;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by agonzal on 09/11/2018.
 */
public class FloorTest {


    @Test
    public  void testGenerateASimpleFloor() throws InvalidSubLevelException{
        Floor aSimpleFloor = new Floor(5, 5, 5);
        assertEquals(5,aSimpleFloor.getNumberOfStreet(),0);
        Item aNewItem = new Item(1);
        aSimpleFloor.addItem(aNewItem,1,1,1);
        double entropy = aSimpleFloor.calculateEntropy(1);
        assertTrue(entropy == 0);
    }

    @Test
    public  void testSimpleFloorAddItemInSamePlaceShouldThrowInvalidSubLevelException(){
        Floor aSimpleFloor = new Floor(2, 2, 2);
        Item aNewItem = new Item(1);
        boolean throwException = false;
        try {
            aSimpleFloor.addItem(aNewItem, 1, 1, 1);
            aSimpleFloor.addItem(aNewItem, 1, 1, 1);
            fail("Should Throw InvalidSubLevelException");
        } catch(InvalidSubLevelException e){
            throwException = true;
        }
        assertTrue (throwException);
    }

    @Test
    public void testFloorWith2ItemsWithDiferentTypeInSameStreetShouldGenerateEntropyNotEqualsTo0() throws InvalidSubLevelException{
        Floor aSimpleFloor = new Floor(2, 2, 2);
        Item aNewItem = new Item(1);

        try {
            aSimpleFloor.addItem(aNewItem, 1, 0, 1);
        } catch(InvalidSubLevelException e){
            fail("Should add Item in the floor");
        }

        double entropy = aSimpleFloor.calculateEntropy(1);
        assertTrue(entropy == 0);
        aNewItem = new Item(2);

        try {
            aSimpleFloor.addItem(aNewItem, 1, 1, 1);
        } catch(InvalidSubLevelException e){
            fail("Should add Item in the floor");
        }

        entropy = aSimpleFloor.calculateEntropy(1);
        assertTrue(entropy == 1);
    }

    @Test
    public void testFloorGetAvailablePositions() throws InvalidSubLevelException{
        Floor aSimpleFloor = new Floor(2, 2, 2);
        LinkedHashMap<Integer, LinkedHashMap<Integer,ArrayList<Integer>>>  availablePositions = aSimpleFloor.getAvailablePositions();
        LinkedHashMap<Integer,ArrayList<Integer>> availablePositionStreet1 = availablePositions.get(0);
        LinkedHashMap<Integer,ArrayList<Integer>> availablePositionStreet2 = availablePositions.get(1);
        ArrayList<Integer> availablePositionsLevel1_1 = availablePositionStreet1.get(0);
        ArrayList<Integer> availablePositionsLevel1_2 = availablePositionStreet1.get(1);
        ArrayList<Integer> availablePositionsLevel2_1 = availablePositionStreet2.get(0);
        ArrayList<Integer> availablePositionsLevel2_2 = availablePositionStreet2.get(1);

        assertTrue(2 == availablePositionsLevel1_1.size());
        assertTrue(2 == availablePositionsLevel1_2.size());
        assertTrue(2 == availablePositionsLevel2_1.size());
        assertTrue(2 == availablePositionsLevel2_2.size());

        Item aNewItem = new Item(1);
        try {
            aSimpleFloor.addItem(aNewItem, 1, 0, 1);
        } catch(InvalidSubLevelException e){
            fail("Should add Item in the floor");
        }
        availablePositions = aSimpleFloor.getAvailablePositions();
        availablePositionStreet1 = availablePositions.get(0);
        availablePositionStreet2 = availablePositions.get(1);
        availablePositionsLevel1_1 = availablePositionStreet1.get(0);
        availablePositionsLevel1_2 = availablePositionStreet1.get(1);
        availablePositionsLevel2_1 = availablePositionStreet2.get(0);
        availablePositionsLevel2_2 = availablePositionStreet2.get(1);

        assertTrue(2 == availablePositionsLevel1_1.size());
        assertTrue(2 == availablePositionsLevel1_2.size());
        assertTrue(1 == availablePositionsLevel2_1.size());
        assertTrue(2 == availablePositionsLevel2_2.size());

    }
}