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

    public ItemPlace(ItemPlace itemPlace){
        this.itemToPlace = itemPlace.getItemToPlace();
        this.itemFloorPlace = itemPlace.getItemFloorPlace();
    }

    @Override
    public boolean equals(Object anotherItemPlace){
        if (anotherItemPlace.getClass() != ItemPlace.class)
            return false;

        return      this.itemToPlace.equals(((ItemPlace )anotherItemPlace).getItemToPlace())
                &&  this.itemFloorPlace.equals(((ItemPlace)anotherItemPlace).getItemFloorPlace());
    }
}
