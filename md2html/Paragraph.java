package md2html;

import java.util.HashMap;

public class Paragraph {

    private String string;
    private StringBuilder result = new StringBuilder();
    private int index = 0;


    private String[] markupTags = {"```", "**", "*", "__", "_", "--", "`"};

    private HashMap<Character, String> specificalSymbol = new HashMap<>();
    private HashMap<String, String> fIndex = new HashMap<>(), sIndex = new HashMap<>();
    private HashMap<String, Integer> map = new HashMap<>();

    public Paragraph(String string) {
        this.string = string;

        fIndex.put("**", "<strong>");
        sIndex.put("**", "</strong>");

        fIndex.put("*", "<em>");
        sIndex.put("*", "</em>");

        fIndex.put("__", "<strong>");
        sIndex.put("__", "</strong>");

        fIndex.put("_", "<em>");
        sIndex.put("_", "</em>");

        fIndex.put("--", "<s>");
        sIndex.put("--", "</s>");

        fIndex.put("`", "<code>");
        sIndex.put("`", "</code>");

        fIndex.put("```", "<pre>");
        sIndex.put("```", "</pre>");

        specificalSymbol.put('<', "&lt;");
        specificalSymbol.put('>', "&gt;");
        specificalSymbol.put('&', "&amp;");
    }

    private String getTags() {
        for (String in : markupTags) {
            if (index + in.length() <= string.length() && string.substring(index, index + in.length()).equals(in)) {
                return in;
            }
        }
        return null;
    }

    private void count() {
        while (index < string.length() && string.charAt(index) == '#') {
            index++;
        }

        if (Character.isWhitespace(string.charAt(index))) {
            index++;
        }
    }

    private boolean canBadCase() {
        return (string.charAt(index) == '*' || string.charAt(index) == '_')
                && (index + 1 == string.length() || Character.isWhitespace(string.charAt(index + 1)))
                && (result.length() == 0 || Character.isWhitespace(result.charAt(result.length() - 1)));
    }

    private void addTag(String tag) {
        if (map.containsKey(tag)) {
            result.insert(map.get(tag), fIndex.get(tag));
            result.append(sIndex.get(tag));
            map.remove(tag);
        } else {
            map.put(tag, result.length());
        }
    }

    private void parseText() {
        while (index < string.length()) {
            if (string.charAt(index) == '\\') {
                if (index + 1 < string.length()) {
                    result.append(string.charAt(++index));
                }
            } else {
                if (canBadCase()) {
                    result.append(string.charAt(index));
                } else {
                    String str = getTags();
                    if (str == null) {
                        if (specificalSymbol.containsKey(string.charAt(index))) {
                            result.append(specificalSymbol.get(string.charAt(index)));
                        } else {
                            result.append(string.charAt(index));
                        }
                    } else {
                        if (map.containsKey("```")) {
                            if (str.equals("```")) {
                                addTag(str);
                                index += str.length() - 1;
                            } else {
                                result.append(string.charAt(index));
                            }
                        } else {
                            addTag(str);
                            index += str.length() - 1;
                        }
                    }
                }
            }
            index++;
        }
    }

    public String parse() {
        count();

        if (index <= 1) {
            index = 0;
            result.append("<p>");
            parseText();
            result.append("</p>");
        } else {
            String divider = Integer.toString(index - 1);
            result.append("<h" + divider + ">");
            parseText();
            result.append("</h" + divider + ">");
        }

        return result.toString();
    }
}
