package markup;

import java.util.*;

public class Text implements AddMarkup {
    private String string;

    public Text(String str){
        string = str;
    }

    @Override
    public void toBBCode(StringBuilder string) {
        string.append(this.string);
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        string.append(this.string);
    }
}
