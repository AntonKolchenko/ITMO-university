package markup;

import java.util.List;

public class Strong extends AbstractText {
    public Strong(List<AddMarkup> array) {
        super(array);

        bFirst = "[b]";
        bSecond = "[/b]";

        mFirst = "__";
        mSecond = "__";
    }
}
