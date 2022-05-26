package markup;
import java.util.List;

public class Emphasis extends AbstractText {
    public Emphasis(List<AddMarkup> array) {
        super(array);

        bFirst = "[i]";
        bSecond = "[/i]";

        mFirst = "*";
        mSecond = "*";
    }
}
