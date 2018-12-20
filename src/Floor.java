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


    public FloorPlace findPlaceToSetItem(Item itemToAdd,List<FloorPlace> actualPlaces, List<StreetPlace> streetPlaces) {

        FloorPlace aNewFloor = null;
        for (int i = 0; i < numberOfStreet ; i++) {

            Double actualEntropy = streets[i].calculateEntropy(1);
            Pair<Double,StreetPlace> newEntropyAndPlace = streets[i].calculateEntropyWithNewItem(itemToAdd,streetPlaces);

            if(newEntropyAndPlace!=null && newEntropyAndPlace.getKey() > actualEntropy){
                aNewFloor = new FloorPlace(i,newEntropyAndPlace.getValue());

                if(!actualPlaces.contains(aNewFloor)){
                    return aNewFloor;
                }
            }
        }
        return aNewFloor;
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
                }
            }
        }
        return bestPlace;
    }

    public double calculateEntropy(List<ItemPlace> allItemPlaces) {
        double entropy= 0;
        for (int i = 0; i < numberOfStreet; i++) {
            List<ItemPlace> itemPlacesForStreet = new ArrayList<>();
            for ( ItemPlace itemP :allItemPlaces){
                if(itemP.getItemFloorPlace().getStreetId()== i){
                    itemPlacesForStreet.add(itemP);
                }
            }
            entropy += streets[i].calculateEntropy(itemPlacesForStreet);
        }
        return entropy;
    }

    private List<FloorPlace> getFloorPlaces(List<ItemPlace> allItemPlaces){
        List<FloorPlace> result = new ArrayList<>();
        for(ItemPlace ip:allItemPlaces){
            result.add(ip.getItemFloorPlace());
        }
        return result;
    }

    public Pair<ItemPlace ,ItemPlace> getBetterItemPlaceChange(ItemPlace actualItemPlace,List<ItemPlace> otherItemPlaces){
        ItemPlace betterItemPlace = actualItemPlace;
        FloorPlace betterFloorPlace = findBestPlaceToSetItem(actualItemPlace.getItemToPlace(),getFloorPlaces(otherItemPlaces),new ArrayList<StreetPlace>());
        ItemPlace newItemPlace = new ItemPlace(actualItemPlace.getItemToPlace(), betterFloorPlace);
        return new Pair<>(actualItemPlace,newItemPlace);
    }

    public Pair<ItemPlace,ItemPlace> getBetterItemPlaceChangeFor(ItemPlace actualIP, List<ItemPlace> actualItemPlaces) {
        ItemPlace betterChange =findBestReplaceItemPlace(actualIP,actualItemPlaces);
        return new Pair<>(actualIP,betterChange);
    }

    private ItemPlace findBestReplaceItemPlace(ItemPlace actualIP, List<ItemPlace> actualItemPlaces) {
        List<ItemPlace> allItemPlaces = new ArrayList<>(actualItemPlaces);
        allItemPlaces.add(actualIP);
        double betterEntropy = calculateEntropy(allItemPlaces);
        ItemPlace betterItemPlace = actualIP;

        for(ItemPlace itemP : actualItemPlaces){
            allItemPlaces = new ArrayList<>(actualItemPlaces);
            ItemPlace firstItemPlaceToChange = new ItemPlace(itemP);
            ItemPlace secondItemPlaceToChange = new ItemPlace(actualIP);
            firstItemPlaceToChange.itemToPlace = actualIP.getItemToPlace();
            secondItemPlaceToChange.itemToPlace = itemP.getItemToPlace();
            allItemPlaces.add(firstItemPlaceToChange);
            allItemPlaces.add(secondItemPlaceToChange);
            allItemPlaces.remove(itemP);
            double actualEntropy =calculateEntropy(allItemPlaces);
            if(betterEntropy < actualEntropy){
                actualEntropy = betterEntropy;
                betterItemPlace = itemP;
            }
        }
        return betterItemPlace;
    }

    private boolean isValidFloorPlace(List<ItemPlace> actualItemPlaces, FloorPlace actualFloorPlace){

        for (ItemPlace ip : actualItemPlaces){
            if(ip.getItemFloorPlace().equals(actualFloorPlace))
                return false;
        }

        return true;
    }

    @Override
    public String toString(){
        String floorString = "";
        for (int i = 0; i < numberOfStreet; i++) {
            if(!floorString.equals(""))
                floorString+="\n----------------\n";
            floorString+= streets[i].toString();
        }
        return  floorString;
    }

}
