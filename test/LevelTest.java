import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by agonzal on 07/11/2018.
 */
public class LevelTest {
    @Test
    public void testCreateLevel(){
        Level aNewLevel = new Level(1);
        assertFalse(1==aNewLevel.getSize());
        assertTrue(0==aNewLevel.getSize());
    }

    @Test
    public void testAddItemToLevel() throws InvalidSubLevelException{
        Level aNewLevel = new Level(1);
        Item newItem = new Item(1);

        aNewLevel.addItem(newItem,0);
        assertEquals ("Expected 0 available Positions", 0 , aNewLevel.availablePositions().size());
    }

    @Test
    public void testAddItemAndHasAvailablePositions() throws InvalidSubLevelException{
        Level aNewLevel = new Level(2);
        assertEquals ("Expected 2 available Positions", 2 , aNewLevel.availablePositions().size());

        Item newItem = new Item(1);
        aNewLevel.addItem(newItem,0);
        assertEquals ("Expected 1 available Positions", 1 , aNewLevel.availablePositions().size());
    }

    @Test
    public void testTryToAddItemWithOutEmptyPosition() throws InvalidSubLevelException{
        boolean hasInvalidSubLevelException = false;
        Level aNewLevel = new Level(1);
        Item newItem = new Item(1);

        aNewLevel.addItem(newItem,0);
        assertEquals ("Expected 0 available Positions", 0 , aNewLevel.availablePositions().size());
        boolean hasException = false;
        try{
            newItem = new Item(2);
            aNewLevel.addItem(newItem,0);
        }catch(InvalidSubLevelException e){
            hasInvalidSubLevelException = true;
        }
        assert hasInvalidSubLevelException;
    }

    @Test
    public void testListEntropy() throws InvalidSubLevelException{
        Level aNewLevel = new Level(5);
        Item newItemType1 = new Item(1);
        Item newItemType2 = new Item(2);

        Entropy levelEntropy = new Entropy();
        levelEntropy.addValue(1,1);
        aNewLevel.addItem(newItemType1,0);
        assertTrue(levelEntropy.equals(aNewLevel.calculateEntropy()));

        aNewLevel.addItem(newItemType2,1);
        assertFalse(levelEntropy.equals(aNewLevel.calculateEntropy()));

        levelEntropy.addValue(2,1);
        assertTrue(levelEntropy.equals(aNewLevel.calculateEntropy()));

        assertTrue(aNewLevel.toString().equals("I | I | _ | _ | _"));
    }
}