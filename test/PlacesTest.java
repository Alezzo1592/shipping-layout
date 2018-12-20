import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by agonzal on 01/12/2018.
 */
public class PlacesTest {
    @Test
    public void testStreetPlaces(){
        StreetPlace aNewStreetPlace = new StreetPlace(1,1);
        StreetPlace anotherStreetPLace = new StreetPlace(1,2);
        assertTrue(aNewStreetPlace.equals(aNewStreetPlace));
        assertFalse(aNewStreetPlace.equals(1));
        assertFalse(aNewStreetPlace.equals(anotherStreetPLace));
    }

    @Test
    public void testFloorPlaces(){
        StreetPlace aSimpleStreetPLaces = new StreetPlace(1,1);

        FloorPlace aNewFloorPlaces = new FloorPlace(1,aSimpleStreetPLaces);
        FloorPlace anotherFloorPlaces = new FloorPlace(2,aSimpleStreetPLaces);

        assertFalse(aNewFloorPlaces.equals(anotherFloorPlaces));
        assertTrue(aNewFloorPlaces.equals(aNewFloorPlaces));
        assertFalse(anotherFloorPlaces.equals(1));
    }
}
