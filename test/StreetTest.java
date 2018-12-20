import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by agonzal on 07/11/2018.
 */
public class StreetTest {

    @Test
    public void testGenerateStreet(){
        Street newSimpleStreet = new Street(1,1);
        assertTrue(1==newSimpleStreet.getNumberOfLevels());
        assertEquals(0,newSimpleStreet.calculateEntropy(1),0);
    }

    @Test
    public void testStreetToString(){
        Street newSimpleStreet = new Street(2,2);
        assertTrue(newSimpleStreet.toString().equals("_ | _\n_ | _\n"));
    }

}