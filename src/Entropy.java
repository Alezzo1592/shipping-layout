/**
 * Created by agonzal on 07/11/2018.
 */
public class Entropy {
    public double type1;
    public double type2;
    public double type3;
    public double type4;
    public double size;


    public Entropy() {
        this.type1 = 0;
        this.type2 = 0;
        this.type3 = 0;
        this.type4 = 0;
        this.size = 0;
    }
    public void addEntropy(Entropy anotherEntropy){
        this.type1 += anotherEntropy.type1;
        this.type2 += anotherEntropy.type2;
        this.type3 += anotherEntropy.type3;
        this.type4 += anotherEntropy.type4;
        this.size  += anotherEntropy.size;
    }

    @Override
    public boolean equals(Object anotherEntropy){

        if(anotherEntropy.getClass() != Entropy.class)
            return false;

        Entropy anotherEntropyCast = (Entropy)anotherEntropy;
        return (this.type1==anotherEntropyCast.type1) &&
                (this.type2==anotherEntropyCast.type2) &&
                (this.type3==anotherEntropyCast.type3) &&
                (this.type4==anotherEntropyCast.type4) &&
                (this.size==anotherEntropyCast.size);
    }

    public double getValue(){
        double probType1 = type1 / size;
        double probType2 = type2 / size;
        double probType3 = type3 / size;
        double probType4 = type4 / size;
        int numberOfPositives = ((probType1 > 0)?1:0)+((probType2 > 0)?1:0)+((probType3 > 0)?1:0)+((probType4 > 0)?1:0);
        if(numberOfPositives >1){
            double informationType1 = (probType1 > 0)? (- (probType1 *  (Math.log(probType1)/Math.log(2)))):0;
            double informationType2 = (probType2 > 0)? (- (probType2 *  (Math.log(probType2)/Math.log(2)))):0;
            double informationType3 = (probType3 > 0)? (- (probType3 *  (Math.log(probType3)/Math.log(2)))):0;
            double informationType4 = (probType4 > 0)? (- (probType4 *  (Math.log(probType4)/Math.log(2)))):0;
            return informationType1 + informationType2 + informationType3 +informationType4;
        }else {
            return 0;
        }
    }

    public void addValue(Integer type, Integer _size){
        switch (type){
            case 1:
                type1+=_size;
                break;
            case 2:
                type2+=_size;
                break;
            case 3:
                type3+=_size;
                break;
            case 4:
                type4+=_size;
                break;
        }
        size += _size;
    }
}
