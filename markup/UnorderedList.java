package markup;

import java.util.List;

public class UnorderedList extends BigText {
    public UnorderedList(List<ListItem> list) {
        super(list);

        bFirst = "[list]";
        bSecond = "[/list]";
    }
}
