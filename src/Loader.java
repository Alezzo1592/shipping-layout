import com.sun.tools.javac.util.Assert;

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

    private String fileNameDefault = "file";
    private String addItemEntryDefault = "fileEntryDefault";
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
                addItem(item_place.getItemToPlace(),item_place.getItemFloorPlace());
        }
    }

    private void addItem(Item itemToPlace, FloorPlace floorPlace) throws InvalidSubLevelException {
        privateFloor.addItem(itemToPlace,floorPlace.getStreetId(),floorPlace.getLevelId(),floorPlace.getSubLevelId());
    }

    public List<ItemPlace> getBest1ChangeStreet(List<ItemPlace> actualItemsPlaces){

        return actualItemsPlaces;
    }

    private ItemPlace getBestStreetChange(ItemPlace actualPlace){

        return actualPlace;
    }

    public Double getEntropy() {
        return privateFloor.calculateEntropy(1);
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
}
