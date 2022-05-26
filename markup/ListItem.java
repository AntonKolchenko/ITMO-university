package markup;

import java.util.List;

public class ListItem {

    private List<GiveText> have;

    public ListItem(List<GiveText> list) {
        have = list;
    }

    public void toBBCode(StringBuilder string) {
        string.append("[*]");
        for (GiveText in : have) {
            in.toBBCode(string);
        }
    }
}
