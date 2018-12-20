/**
 * Created by agonzal on 19/11/2018.
 */
public class FloorPlace {
    Integer streetId;
    StreetPlace streetPlaceId;

    public StreetPlace getStreetPlaceId() {
        return streetPlaceId;
    }
    public Integer getStreetId() {
        return streetId;
    }

    public Integer getLevelId(){
        return streetPlaceId.getLevelId();
    }

    public Integer getSubLevelId(){
        return streetPlaceId.getSubLevelId();
    }

    public FloorPlace(Integer streetId,StreetPlace streetPlaceId) {
        this.streetId = streetId;
        this.streetPlaceId = streetPlaceId;
    }

    @Override
    public boolean equals(Object anotherFloorPlace) {

        if (anotherFloorPlace.getClass() != FloorPlace.class)
            return false;
        return this.streetId == ((FloorPlace)anotherFloorPlace).getStreetId() && this.streetPlaceId.equals(((FloorPlace) anotherFloorPlace).getStreetPlaceId());
    }
}
