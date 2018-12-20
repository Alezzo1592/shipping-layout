import com.sun.tools.javac.util.Assert;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xerces.internal.dom.DOMNormalizer.abort;
import static java.lang.Integer.parseInt;

/**
 * Created by agonzal on 10/11/2018.
 */
public class Loader {
    private Floor privateFloor;
    private static Integer NUMBER_OF_STEPS= 10;
    private static Integer NUMBER_OF_STEPS_TO_WORK= 50;
    public Integer MAX_NUMBER_OF_STEP_WITHOUT_BE_BETTER = 5;
    private String fileNameDefault = "file";
    private String addItemEntryDefault = "fileEntryDefault";
    public Integer MAX_SIZE_TABOO_SEARCH = 10;

    public List< List<ItemPlace>> allMoves = new ArrayList<>();

    private List< TabooMove > TabooSearch = new ArrayList<>();

    private class TabooMove{
        Pair<ItemPlace, ItemPlace> move;
        boolean isSwapMove;
    }
    public Loader() {}

    public void loadData() throws Throwable {
        loadData(fileNameDefault);
    }

    public void loadData(String file) throws Throwable{
        File loadFile = new File(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] data = line.split(",");
            privateFloor = new Floor( parseInt(data[0]), parseInt(data[1]), parseInt(data[2]));
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                //_countOfSubLevel,_category,_count,_level,_type,minDistance,street,level,sublevel
                Integer _countOfSubLevel= Integer.parseInt(data[0]);
                Integer _category= Integer.parseInt(data[1]);
                Integer _count= Integer.parseInt(data[2]);
                Integer _level= Integer.parseInt(data[3]);
                Integer _type= Integer.parseInt(data[4]);
                Integer minDistance= Integer.parseInt(data[5]);
                Integer street= Integer.parseInt(data[6]);
                Integer level= Integer.parseInt(data[7]);
                Integer sublevel= Integer.parseInt(data[8]);
                Item itemToAdd = new Item(_countOfSubLevel,_category,_count,_level,_type,minDistance);
                try {
                    privateFloor.addItem(itemToAdd, street, level, sublevel);
                }catch (InvalidSubLevelException e){
                    throw new Throwable("error when try to add item in the base");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addItems(List<ItemPlace> placeItemsToAdd) throws InvalidSubLevelException{
        for (ItemPlace item_place : placeItemsToAdd){
                try {
                    addItem(item_place.getItemToPlace(), item_place.getItemFloorPlace());
                }catch(InvalidSubLevelException e){
                    throw e;
                }
        }
    }

    private void addItem(Item itemToPlace, FloorPlace floorPlace) throws InvalidSubLevelException {
        privateFloor.addItem(itemToPlace,floorPlace.getStreetId(),floorPlace.getLevelId(),floorPlace.getSubLevelId());
    }

    private boolean isValidTabooMove(Pair<ItemPlace, ItemPlace> actualSwap ){

        for (TabooMove old_move : TabooSearch){
            if(( old_move.move.getKey() == actualSwap.getKey() && actualSwap.getValue() == old_move.move.getValue()) ||
                    (old_move.move.getKey() == actualSwap.getValue() && actualSwap.getKey() == old_move.move.getValue()) )
                return false;
        }
        return true;
    }

    public List<ItemPlace> metaheuristicChangeTo(List<ItemPlace> allItemPlaces){
        for (int i = 0; i < NUMBER_OF_STEPS ; i++) {
            Pair<ItemPlace ,ItemPlace > replaceItemPlace= firstMetaheuristicOneStep(allItemPlaces);
            if(replaceItemPlace!=null) {
                allItemPlaces.remove(replaceItemPlace.getKey());
                allItemPlaces.add(replaceItemPlace.getValue());
            }
        }

        return allItemPlaces;
    }

    private Pair<ItemPlace ,ItemPlace > firstMetaheuristicOneStep(List<ItemPlace> allItemPlaces){
        List<ItemPlace> result = allItemPlaces;
        double bestEntropy = privateFloor.calculateEntropy(allItemPlaces);
        double secondBestEntropy = 0;
        Pair<ItemPlace ,ItemPlace > betterItemPlaceSwap = null;
        Pair<ItemPlace ,ItemPlace > secondBetterItemPlaceSwap = null;
        for (ItemPlace actual_IP_to_change : allItemPlaces){

            List<ItemPlace> ip_withOut_actual = new ArrayList<>( allItemPlaces);
            ip_withOut_actual.remove(actual_IP_to_change);
            Pair<ItemPlace ,ItemPlace > replaceItemPlace = privateFloor.getBetterItemPlaceChange(actual_IP_to_change,ip_withOut_actual);
            ip_withOut_actual.add(replaceItemPlace.getValue());

            double actualBetterEntropy =  privateFloor.calculateEntropy(ip_withOut_actual);
            if(isValidTabooMove(replaceItemPlace)){
                if(actualBetterEntropy >= bestEntropy ){
                    betterItemPlaceSwap = replaceItemPlace;
                }else{
                    if(secondBetterItemPlaceSwap == null ||secondBestEntropy < bestEntropy){
                        secondBestEntropy = bestEntropy;
                        secondBetterItemPlaceSwap = replaceItemPlace;
                    }
                }
            }
        }
        return betterItemPlaceSwap!=null?betterItemPlaceSwap:secondBetterItemPlaceSwap;
    }

    public List<ItemPlace> metaheuristicChageFor(List<ItemPlace> allItemPlaces){
        for (int i = 0; i < NUMBER_OF_STEPS ; i++) {
            Pair<ItemPlace ,ItemPlace > itemPlaceItemPlacePair= secondMetaheuristicOneStep(allItemPlaces);
            if(itemPlaceItemPlacePair!=null && !(itemPlaceItemPlacePair.getKey().equals(itemPlaceItemPlacePair.getValue()))){

                ItemPlace newFirstItemPlace = new ItemPlace(itemPlaceItemPlacePair.getKey().getItemToPlace(),itemPlaceItemPlacePair.getValue().getItemFloorPlace());
                ItemPlace newSecondItemPlace = new ItemPlace(itemPlaceItemPlacePair.getValue().getItemToPlace(),itemPlaceItemPlacePair.getKey().getItemFloorPlace());
                allItemPlaces.remove(itemPlaceItemPlacePair.getValue());
                allItemPlaces.remove(itemPlaceItemPlacePair.getKey());
                allItemPlaces.add(newFirstItemPlace);
                allItemPlaces.add(newSecondItemPlace);
            }
        }

        return allItemPlaces;
    }

    private Pair<ItemPlace ,ItemPlace > secondMetaheuristicOneStep(List<ItemPlace> allItemPlaces){
        double bestEntropy = privateFloor.calculateEntropy(allItemPlaces);
        double secondBestEntropy = 0;
        Pair<ItemPlace ,ItemPlace > bestItemPlaceSwap = null;
        Pair<ItemPlace ,ItemPlace > secondBetterItemPlaceSwap = null;

        for(ItemPlace actualItemPlace: allItemPlaces){
            List<ItemPlace> ip_withOut_actual = new ArrayList<>( allItemPlaces);
            Pair<ItemPlace ,ItemPlace > replaceItemPlace = privateFloor.getBetterItemPlaceChangeFor(actualItemPlace,ip_withOut_actual);

            ItemPlace firstItemPlaceToChange = new ItemPlace(replaceItemPlace.getKey());
            ItemPlace secondItemPlaceToChange = new ItemPlace(replaceItemPlace.getValue());
            firstItemPlaceToChange.itemToPlace = replaceItemPlace.getValue().getItemToPlace();
            secondItemPlaceToChange.itemToPlace = replaceItemPlace.getKey().getItemToPlace();
            ip_withOut_actual.add(firstItemPlaceToChange);
            ip_withOut_actual.add(secondItemPlaceToChange);

            double actualEntropy = privateFloor.calculateEntropy(ip_withOut_actual);
            if(isValidTabooMove(replaceItemPlace)){
                if(actualEntropy >= bestEntropy ){
                    bestItemPlaceSwap = replaceItemPlace;
                }
            }else{
                if(secondBetterItemPlaceSwap == null ||secondBestEntropy < bestEntropy){
                    secondBestEntropy = bestEntropy;
                    secondBetterItemPlaceSwap = replaceItemPlace;
                }
            }
        }
        return bestItemPlaceSwap!=null?bestItemPlaceSwap:secondBetterItemPlaceSwap;
    }




    public List<ItemPlace> getBetterList(List<ItemPlace> theItemsPlaces){
        TabooSearch = new ArrayList<>();
        List<ItemPlace> itemPlacesToGetBetter = theItemsPlaces;
        double bestEntropy = privateFloor.calculateEntropy(theItemsPlaces);
        Integer numberOfSwapWithoutBeBetter = 0;

        while(numberOfSwapWithoutBeBetter<MAX_NUMBER_OF_STEP_WITHOUT_BE_BETTER){
            List<ItemPlace> itemPlacesFirstMove = theItemsPlaces;
            List<ItemPlace> itemPlacesSecondMove = theItemsPlaces;


            Pair<ItemPlace,ItemPlace> firstSwap = firstMetaheuristicOneStep(theItemsPlaces);
            Pair<ItemPlace,ItemPlace> secondSwap = secondMetaheuristicOneStep(theItemsPlaces);

            itemPlacesFirstMove.remove(firstSwap.getKey());
            itemPlacesFirstMove.add(firstSwap.getValue());

            double firstSwapEntropy=privateFloor.calculateEntropy(itemPlacesFirstMove);


            ItemPlace newFirstItemPlace = new ItemPlace(secondSwap.getKey().getItemToPlace(),secondSwap.getValue().getItemFloorPlace());
            ItemPlace newSecondItemPlace = new ItemPlace(secondSwap.getValue().getItemToPlace(),secondSwap.getKey().getItemFloorPlace());
            if(!newFirstItemPlace.equals(newSecondItemPlace)) {
                itemPlacesSecondMove.remove(secondSwap.getValue());
                itemPlacesSecondMove.remove(secondSwap.getKey());
                itemPlacesSecondMove.add(newFirstItemPlace);
                itemPlacesSecondMove.add(newSecondItemPlace);
            }
            double secondSwapEntropy=privateFloor.calculateEntropy(itemPlacesSecondMove);

            boolean firstValidMove  = isValidTabooMove(firstSwap);
            boolean secondValidMove = isValidTabooMove(secondSwap);
            if(!(firstValidMove && secondValidMove)){
                break;
            }
            TabooMove newTaboomove = new TabooMove();
            if(((firstValidMove && firstSwapEntropy > secondSwapEntropy) || !secondValidMove)){
                newTaboomove.move = firstSwap;
                newTaboomove.isSwapMove=false;
                theItemsPlaces = itemPlacesFirstMove;
                if(firstSwapEntropy> bestEntropy){
                    bestEntropy = firstSwapEntropy;
                    itemPlacesToGetBetter = theItemsPlaces;
                    numberOfSwapWithoutBeBetter = 0;
                }else{
                    numberOfSwapWithoutBeBetter +=1;
                }
            }else{
                theItemsPlaces = itemPlacesSecondMove;
                newTaboomove.move = secondSwap;
                newTaboomove.isSwapMove=true;
                if(secondSwapEntropy > bestEntropy){
                    bestEntropy = secondSwapEntropy;
                    itemPlacesToGetBetter = theItemsPlaces;
                    numberOfSwapWithoutBeBetter = 0;
                }else{
                    numberOfSwapWithoutBeBetter +=1;
                }
            }
            TabooSearch.add(newTaboomove);
            if(TabooSearch.size() == MAX_SIZE_TABOO_SEARCH){
                TabooSearch.remove(0);
            }
        }
        return itemPlacesToGetBetter;
    }

    public Double getEntropy() {
        return privateFloor.calculateEntropy(1);
    }

    public double calculateEntropy(List<ItemPlace> ip){
        return privateFloor.calculateEntropy(ip);
    }

    public List<ItemPlace> dummyEntry(){return dummyEntry(addItemEntryDefault);}

    public List<ItemPlace> generateHeuristicAssignment(){return generateHeuristicAssignment(addItemEntryDefault);}

    public List<ItemPlace> generateHeuristicAssignment(String fileEntry){
        List<Item> itemsToAdd = loadItems(fileEntry);
        List<FloorPlace> floorPlaces = new ArrayList<>();
        List<StreetPlace> streetPlaces = new ArrayList<>();
        List<ItemPlace> itemsPlaces = new ArrayList<>();

        for (Item itemToAdd : itemsToAdd){
            FloorPlace aNewFloorPlace =privateFloor.findBestPlaceToSetItem(itemToAdd,floorPlaces,streetPlaces) ;
            if(aNewFloorPlace !=null) {

                streetPlaces.add(aNewFloorPlace.getStreetPlaceId());
                floorPlaces.add(aNewFloorPlace);
                itemsPlaces.add(new ItemPlace(itemToAdd,aNewFloorPlace));
            }
        }
        return itemsPlaces;
    }

    public List<ItemPlace> dummyEntry(String fileEntry){
        List<Item> itemsToAdd = loadItems(fileEntry);
        List<FloorPlace> floorPlaces = new ArrayList<>();
        List<StreetPlace> streetPlaces = new ArrayList<>();
        List<ItemPlace> itemsPlaces = new ArrayList<>();

        for (Item itemToAdd : itemsToAdd){
            FloorPlace aNewFloorPlace =privateFloor.findPlaceToSetItem(itemToAdd,floorPlaces,streetPlaces) ;
            if(aNewFloorPlace !=null) {

                streetPlaces.add(aNewFloorPlace.getStreetPlaceId());
                floorPlaces.add(aNewFloorPlace);
                itemsPlaces.add(new ItemPlace(itemToAdd,aNewFloorPlace));
            }
        }
        return itemsPlaces;
    }

    public List<Item> loadItems( String fileItems){
        List<Item> itemToAdd = new ArrayList<>();
        File loadFile = new File(fileItems);
        try (BufferedReader br = new BufferedReader(new FileReader(fileItems))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Integer _countOfSubLevel= Integer.parseInt(data[0]);
                Integer _category= Integer.parseInt(data[1]);
                Integer _count= Integer.parseInt(data[2]);
                Integer _level= Integer.parseInt(data[3]);
                Integer _type= Integer.parseInt(data[4]);
                Integer minDistance= Integer.parseInt(data[5]);
                itemToAdd.add( new Item(_countOfSubLevel,_category,_count,_level,_type,minDistance));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemToAdd;
    }

    @Override
    public String toString(){
        return privateFloor.toString();
    }
}
