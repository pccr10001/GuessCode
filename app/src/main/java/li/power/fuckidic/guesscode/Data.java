package li.power.fuckidic.guesscode;

/**
 * Created by PowerLi on 2017/4/11.
 */
public class Data {
    int index;
    boolean block;

    public Data(int index, boolean block) {
        this.index = index;
        this.block = block;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
