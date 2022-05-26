package markup;

import java.util.List;

public class Strikeout extends AbstractText {
    public Strikeout(List<AddMarkup> array) {
        super(array);

        bFirst = "[s]";
        bSecond = "[/s]";

        mFirst = "~";
        mSecond = "~";
    }
}
