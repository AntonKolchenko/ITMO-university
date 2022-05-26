package markup;

import java.util.*;

public abstract class AbstractText implements AddMarkup {
    protected List<AddMarkup> have;
    protected String bFirst = "", bSecond = "";
    protected String mFirst = "", mSecond = "";

    public AbstractText(List<AddMarkup> array) {
        have = array;
    }

    @Override
    public void toBBCode(StringBuilder string) {
        string.append(bFirst);
        for (AddMarkup input : have) {
            input.toBBCode(string);
        }
        string.append(bSecond);
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        string.append(mFirst);
        for (AddMarkup input : have) {
            input.toMarkdown(string);
        }
        string.append(mSecond);
    }
}
