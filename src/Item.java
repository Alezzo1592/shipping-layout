/**
 * Created by agonzal on 07/11/2018.
 */
public class Item {
    private Integer countOfSubLevel = 1 ;
    private Integer category = -1;
    private Integer count = 1;
    private Integer level = -1;
    private Integer type;
    private Integer minDistanceEqualsItem = 0;

    public Integer getCountOfSubLevel() {
        return countOfSubLevel;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getLevel() {
        return level;
    }
    public Integer getType() {
        return type;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getMinDistanceEqualsItem() {
        return minDistanceEqualsItem;
    }

    public Item(Integer _type){
        this.countOfSubLevel = 1;
        this.category = -1;
        this.count = 1;
        this.level = -1;
        this.type = _type;
        this.minDistanceEqualsItem = 0;
    }

    public Item(Integer _countOfSubLevel, Integer _category, Integer _count, Integer _level, Integer _type,Integer minDistance) {
        this.countOfSubLevel = _countOfSubLevel;
        this.category = _category;
        this.count = _count;
        this.level = _level;
        this.type = _type;
        this.minDistanceEqualsItem = minDistance;
    }
    @Override
    public String toString(){
        return "<"
                +this.countOfSubLevel.toString()+","
                +this.category.toString()+","
                +this.count.toString()+","
                +this.level.toString()+","
                +this.type.toString()+","
                +this.minDistanceEqualsItem.toString()+","
                +">";
    }
}