import javafx.util.Pair;

import java.util.*;

/**
 * Created by agonzal on 07/11/2018.
 */
public class Street {
    private Integer numberOfLevels;
    private Level[] levels;

    public Integer getNumberOfLevels() {
        return numberOfLevels;
    }

    public Street(Integer _numberOfLevels, Integer _sizeOfLevel) {
        this.numberOfLevels = _numberOfLevels;
        this.levels = new Level[numberOfLevels];
        for (int i = 0; i < numberOfLevels; i++) {
            this.levels[i] = new Level(_sizeOfLevel);
        }
    }

    public void addItem(Item item, Integer level, Integer sublevel) throws InvalidSubLevelException{
        levels[level].addItem(item,sublevel);
    }

    public LinkedHashMap<Integer,ArrayList<Integer> > getAvailablePositions(){
        LinkedHashMap<Integer,ArrayList<Integer> > dicPositions = new LinkedHashMap<>();
        for (int i = 0; i < numberOfLevels; i++) {
            dicPositions.put(i,levels[i].availablePositions());
        }
        return dicPositions;
    }

    public double calculateEntropy(Integer mode){
        return calculateEntropy(mode,new Entropy());
    }

    public double calculateEntropy(Integer mode, Entropy entropyToAdd){

        return getOwnEntropy(mode,entropyToAdd).getValue();
    }

    private Entropy getOwnEntropy(Integer mode, Entropy entropyToAdd){
        Entropy streetEntropy = new Entropy();
        for (Level lvl : levels){
            streetEntropy.addEntropy(lvl.calculateEntropy());
        }
        streetEntropy.addEntropy(entropyToAdd);
        return streetEntropy;
    }

    public Pair<Double,StreetPlace> calculateEntropyWithNewItem(Item itemToAdd, List<StreetPlace> streetPlaces) {

        LinkedHashMap<Integer,ArrayList<Integer>> dicc = getAvailablePositions();
        Entropy newEntropy = new Entropy();
        newEntropy.addValue(itemToAdd.getType(),itemToAdd.getCount());

        double newEntropyValue = calculateEntropy(1, newEntropy);

        for(final Integer key: dicc.keySet()){
            for (Integer subLevel : dicc.get(key)) {

                StreetPlace newStreetPlace = new StreetPlace(key, subLevel);
                if(!streetPlaces.contains(newStreetPlace))
                    return (new Pair<>(newEntropyValue, newStreetPlace));
            }
        }

        return null;
    }

    public double calculateEntropy(List<ItemPlace> itemPlacesForStreet) {
        Entropy newEntropy = new Entropy();
        for (ItemPlace item_place: itemPlacesForStreet){
            newEntropy.addValue(item_place.getItemToPlace().getType(),item_place.getItemToPlace().getCount());
        }
        return  getOwnEntropy(1,newEntropy).getValue();
    }
    @Override
    public String toString(){
        String streetString = "";
        for (int i = 0; i < numberOfLevels ; i++) {
            streetString+= levels[i].toString()+"\n";
        }
        return streetString;
    }
}
