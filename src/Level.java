import java.util.ArrayList;

/**
 * Created by agonzal on 07/11/2018.
 */
public class Level {
    private Integer size;
    private Item[] subLevels;

    public Integer getSize() {
        return size;
    }

    public void addItem(Item newItem, Integer subLevel) throws InvalidSubLevelException{
        if(subLevels[subLevel] == null){
            subLevels[subLevel]= newItem;
            size += newItem.getCount();
        }else{
            throw new InvalidSubLevelException();
        }
    }

    public ArrayList<Integer> availablePositions(){
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < subLevels.length; i++) {
            if(subLevels[i] == null){
                positions.add(i);
            }
        }
        return positions;
    }
    public Level(Integer _size) {
        this.size = 0;
        this.subLevels = new Item[_size];
    }
    public Entropy calculateEntropy(){
        Entropy levelEntropy = new Entropy();

        for(Item _item : subLevels){

            if(_item!=null) {
                levelEntropy.addValue(_item.getType(),_item.getCount());
            }
        }

        return levelEntropy;
    }
}
