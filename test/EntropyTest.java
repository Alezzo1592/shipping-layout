import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Created by agonzal on 09/11/2018.
 */
public class EntropyTest {
    @Test
    public void testEntropyEqualsToHim(){
        Entropy aNewEntropy = new Entropy();
        aNewEntropy.addValue(3,1);
        aNewEntropy.addValue(4,2);
        assertTrue( aNewEntropy.equals(aNewEntropy));
    }

    @Test
    public void testEntropyWithAnotherObjectShouldFail(){
        Entropy aNewEntropy = new Entropy();
        assertFalse( aNewEntropy.equals(1));
    }

    @Test
    public void testAddValuesFromAnotherEntropy(){
        Entropy aNewEntropy = new Entropy();
        aNewEntropy.addValue(3,1);

        Entropy aNewEntropyToAdd = new Entropy();
        aNewEntropyToAdd.addValue(3,2);


        Entropy aNewEntropyPlus = new Entropy();
        aNewEntropyPlus.addValue(3,3);

        aNewEntropy.addEntropy(aNewEntropyToAdd);

        assertTrue(aNewEntropy.equals(aNewEntropyPlus));
    }

    @Test
    public void testCalculateEntropyValue(){
        Entropy entropyToCalculate = new Entropy();
        entropyToCalculate.addValue(1,1);
        entropyToCalculate.addValue(2,3);
        entropyToCalculate.addValue(3,3);
        DecimalFormat df2 = new DecimalFormat(".####");

        assertTrue(df2.format(entropyToCalculate.getValue()).equals("1.4488"));
    }
}