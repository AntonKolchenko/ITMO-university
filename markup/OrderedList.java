package markup;

import java.util.List;

public class OrderedList extends BigText {
    public OrderedList(List<ListItem> list) {
        super(list);

        bFirst = "[list=1]";
        bSecond = "[/list]";
    }
}
