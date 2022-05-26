package markup;

import java.util.List;

public class Paragraph implements GiveText {

    protected List<AddMarkup> have;

    public Paragraph(List<AddMarkup> array) {
        have = array;
    }

    @Override
    public void toBBCode(StringBuilder string) {
        for (AddMarkup input : have) {
            input.toBBCode(string);
        }
    }

    public void toMarkdown(StringBuilder string) {
        for (AddMarkup input : have) {
            input.toMarkdown(string);
        }
    }
}
