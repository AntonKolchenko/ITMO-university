package markup;

import java.util.List;

public abstract class BigText implements GiveText {

    protected List<ListItem> have;
    protected String bFirst, bSecond;

    public BigText(List<ListItem> array) {
        have = array;
    }

    @Override
    public void toBBCode(StringBuilder string) {
        string.append(bFirst);
        for (ListItem in : have) {
            in.toBBCode(string);
        }
        string.append(bSecond);
    }
}
