/**
 * Created by agonzal on 19/11/2018.
 */
public class ItemPlace {
    Item itemToPlace;
    FloorPlace itemFloorPlace;

    public Item getItemToPlace() {
        return itemToPlace;
    }

    public FloorPlace getItemFloorPlace() {
        return itemFloorPlace;
    }

    public ItemPlace(Item itemToPlace,FloorPlace itemFloorPlace) {
        this.itemToPlace = itemToPlace;
        this.itemFloorPlace=itemFloorPlace;
    }
}
