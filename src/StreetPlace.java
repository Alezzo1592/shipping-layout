/**
 * Created by agonzal on 19/11/2018.
 */
public class StreetPlace {
    Integer levelId;
    Integer subLevelId;

    public StreetPlace(Integer levelId,Integer subLevelId) {
        this.levelId = levelId;
        this.subLevelId = subLevelId;
    }
    public Integer getSubLevelId() {
        return subLevelId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    @Override
    public boolean equals(Object anotherStreetPlace) {

        if (anotherStreetPlace.getClass() != StreetPlace.class)
            return false;

        return      this.levelId == ((StreetPlace)anotherStreetPlace).getLevelId()
                &&  this.subLevelId == ((StreetPlace)anotherStreetPlace).getSubLevelId();
    }
}
