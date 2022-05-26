package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {

    public static void main(String args[]) {
        StringBuilder answer = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8)
            );

            try {
                while (true) {
                    String string = reader.readLine();

                    if (string == null) {
                        break;
                    }

                    StringBuilder stringBuilder = new StringBuilder();

                    while (string != null && !string.isEmpty()) {
                        stringBuilder.append(string + '\n');
                        string = reader.readLine();
                    }

                    if (stringBuilder.length() > 0) {
                        String line = new Paragraph(
                                stringBuilder.toString().substring(0, stringBuilder.length() - 1)
                        ).parse();

                        answer.append(line + '\n');
                    }
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problems with input file: " + e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8)
            );
            try {
                writer.write(answer.toString());
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Output file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problems with output file: " + e.getMessage());
        }
    }
}
