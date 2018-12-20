import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by agonzal on 07/11/2018.
 */
public class ItemTests {
    @Test
    public void testGenerateGenericItem(){
        Item genericItem = new Item(4);
        assertTrue (4   == genericItem.getType());
        assertTrue (1   == genericItem.getCount());
        assertTrue (-1  == genericItem.getCategory());
        assertTrue (1   == genericItem.getCountOfSubLevel());
        assertTrue (-1  == genericItem.getLevel());
        assertTrue( 0   == genericItem.getMinDistanceEqualsItem());
    }

    @Test
    public void testGenerateCustomItem(){
        Item genericItem = new Item(2,1,2,1,1,1);
        assertTrue (1 == genericItem.getType()  );
        assertTrue (2 == genericItem.getCount() );
        assertTrue (1 == genericItem.getCategory()  );
        assertTrue (2 == genericItem.getCountOfSubLevel()   );
        assertTrue (1 == genericItem.getLevel() );
        assertTrue (1 == genericItem.getMinDistanceEqualsItem());
    }

    @Test
    public void testItemToString(){
        Item genericItem = new Item(4);
        assertTrue(genericItem.toString().equals("<1,-1,1,-1,4,0,>"));
    }
}
