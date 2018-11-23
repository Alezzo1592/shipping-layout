import javafx.util.Pair;

import java.util.*;

/**
 * Created by agonzal on 07/11/2018.
 */
public class Floor {
    private Integer numberOfStreet;
    private Street[] streets;


    public Integer getNumberOfStreet() {
        return numberOfStreet;
    }

    public void addItem(Item item, Integer street, Integer level, Integer sublevel) throws InvalidSubLevelException{
        streets[street].addItem(item,level,sublevel);
    }

    public Floor(Integer _numberOfStreet, Integer _numberOfLevels, Integer _sizeOfLevels) {
        this.numberOfStreet = _numberOfStreet;
        this.streets = new Street[_numberOfStreet];
        for (int i = 0; i < _numberOfStreet; i++) {
            this.streets[i] = new Street(_numberOfLevels,_sizeOfLevels);
        }
    }

    public LinkedHashMap<Integer, LinkedHashMap<Integer,ArrayList<Integer>> > getAvailablePositions(){
        LinkedHashMap<Integer, LinkedHashMap<Integer,ArrayList<Integer>> > dicOfPositions = new LinkedHashMap<>();
        for (int i = 0; i < numberOfStreet; i++) {
            dicOfPositions.put(i,streets[i].getAvailablePositions());
        }
        return dicOfPositions;
    }

    public double calculateEntropy(Integer mode){
        double entropy= 0;
        for(Street s : streets){
            entropy += s.calculateEntropy(mode);
        }
        return entropy;
    }
    public FloorPlace getBestStreetChange(ItemPlace actualPlace){
        FloorPlace actualFloorPlace = actualPlace.getItemFloorPlace();

        for (int i = 0; i < numberOfStreet; i++) {
            
        }

        return actualFloorPlace;
    }

    public FloorPlace findPlaceToSetItem(Item itemToAdd,List<FloorPlace> actualPlaces, List<StreetPlace> streetPlaces) {

        for (int i = 0; i < numberOfStreet ; i++) {

            Double actualEntropy = streets[i].calculateEntropy(1);
            Pair<Double,StreetPlace> newEntropyAndPlace = streets[i].calculateEntropyWithNewItem(itemToAdd,streetPlaces);

            if(newEntropyAndPlace!=null && newEntropyAndPlace.getKey() > actualEntropy){
                FloorPlace aNewFloor = new FloorPlace(i,newEntropyAndPlace.getValue());

                if(!actualPlaces.contains(aNewFloor)){
                    return aNewFloor;
                }
            }
        }
        return null;
    }

    public FloorPlace findBestPlaceToSetItem(Item itemToAdd, List<FloorPlace> floorPlaces, List<StreetPlace> streetPlaces) {
        Street firstStreet = streets[0];
        Pair<Double,StreetPlace> newEntropyAndPlace = firstStreet.calculateEntropyWithNewItem(itemToAdd,streetPlaces);
        FloorPlace bestPlace = new FloorPlace(0,newEntropyAndPlace.getValue());

        for (int i = 0; i < numberOfStreet ; i++) {

            Double actualEntropy = streets[i].calculateEntropy(1);
            newEntropyAndPlace = streets[i].calculateEntropyWithNewItem(itemToAdd,streetPlaces);
            Double newEntropy = newEntropyAndPlace.getKey();
            if(newEntropyAndPlace!=null && newEntropy > actualEntropy){
                FloorPlace aNewFloor = new FloorPlace(i,newEntropyAndPlace.getValue());

                if(!floorPlaces.contains(aNewFloor)){
                    bestPlace = aNewFloor;
                    actualEntropy = newEntropy;
                }
            }
        }
        return bestPlace;
    }
}
