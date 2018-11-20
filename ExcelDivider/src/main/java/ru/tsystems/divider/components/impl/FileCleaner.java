package ru.tsystems.divider.components.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.ExcelFileReader;
import ru.tsystems.divider.service.api.ExcelFileWriter;
import ru.tsystems.divider.service.impl.XlsxReaderImpl;
import ru.tsystems.divider.service.impl.XlsxWriterImpl;

/*
    todo: Component should remove tags from source only. It shouldn't read file, parsinx xlsx, etc... When class will
    todo: changing, rename this class to new name. Mb should to add properties for that!
 */

public class FileCleaner {

    private final static Map<String, String> REPLACES_TAGS;

    private final static Map<String, String> REPLACES_ONE_WORD_TAGS;

    private final static Map<String, String> REPLACES;

    static {
        REPLACES_TAGS = new HashMap<>();

        REPLACES_TAGS.put("div", "\n");
        REPLACES_TAGS.put("br", "\n");

        REPLACES_TAGS.put("font", "");
        REPLACES_TAGS.put("strong", "");

        REPLACES_TAGS.put("a href", "");

        /*
         * This map needed because in comments I were found xml tags like <AH...>, And maybe comments contains xml tags,
         * which can to start with same letters. It's why i searching <a href...> and only <a>,</a>.
         */
        REPLACES_ONE_WORD_TAGS = new HashMap<>();

        REPLACES_ONE_WORD_TAGS.put("</a>", "");
        REPLACES_ONE_WORD_TAGS.put("<a>", "");
        REPLACES_ONE_WORD_TAGS.put("<p> ", "\n");
        REPLACES_ONE_WORD_TAGS.put("</p>", "");
        REPLACES_ONE_WORD_TAGS.put("<b>", "");
        REPLACES_ONE_WORD_TAGS.put("</b>", "");

        REPLACES = new HashMap<>();

        REPLACES.put("&gt;", ">");
        REPLACES.put("&#228;", "ä");
        REPLACES.put("&#132;", "\"");
        REPLACES.put("&nbsp;", " ");
        REPLACES.put("&lt;", "<");
        REPLACES.put("&quot;", "\"");
        REPLACES.put("&amp;", "&");
        REPLACES.put("\t", " ");
    }

    public void clearFile(String readFileName, String resultFileName, String sheetName)
            throws IOException, NoShetException {
        try (ExcelFileReader reader = new XlsxReaderImpl(readFileName, sheetName);
                ExcelFileWriter writer = new XlsxWriterImpl(resultFileName, sheetName)) {
            Cell cell;
            int cellIndex = 1;
            int cellIndexToWrite = 1;
            String value;
            while ((cell = reader.getCell(0, cellIndex)) != null) {
                value = cell.getStringCellValue();
                System.out.println("Read cell: " + value);

                value = formatLine(value);
                System.out.println("Write cell: " + value);
                if (isFirstNum(value, ';')) {
                    writer.setTextCellValue(value, 0, cellIndexToWrite);
                } else {
                    cellIndexToWrite--;
                    writer.appendTextCellValue(value, 0, cellIndexToWrite);
                }
                cellIndex++;
                cellIndexToWrite++;
            }
            System.out.println("Cell reading's file index: " + cellIndex + "\nCell writing's file index: "
                               + cellIndexToWrite);
        }
    }

    private static boolean isFirstNum(String str, char divideSymbol) {
        int index = 0;
        index = str.indexOf(divideSymbol) - 1;
        if (index < 0)
            return false;
        while (index >= 0) {
            if (!Character.isDigit(str.charAt(index)))
                return false;
            index--;
        }
        return true;
    }

    private static String formatLine(String line) {
        String result = line;
        result = removeTags(result);
        for (String del : REPLACES_ONE_WORD_TAGS.keySet())
            result = result.replaceAll(del, REPLACES_ONE_WORD_TAGS.get(del));
        for (String del : REPLACES.keySet())
            result = result.replaceAll(del, REPLACES.get(del));

        return result;
    }

    public static String removeTags(String line) {
        String result = line;
        int indexStart;
        int indexEnd;

        for (String tag : REPLACES_TAGS.keySet()) {
            while ((indexStart = searchTags(result, tag)) != -1) {
                indexEnd = result.indexOf(">", indexStart);
                if (indexEnd == result.length() - 1)
                    result = result.substring(0, indexStart) + REPLACES_TAGS.get(tag);
                else
                    result = result.substring(0, indexStart) + REPLACES_TAGS.get(tag) + result.substring(indexEnd + 1);
            }
        }
        return result;
    }

    private static int searchTags(String str, String tag) {
        int resultIndex;
        String tagOpenSymbol = "<";
        String tagCloseSymbol = "</";
        resultIndex = str.indexOf(tagOpenSymbol + tag);
        if (resultIndex == -1) {
            resultIndex = str.indexOf(tagCloseSymbol + tag);
        }

        return resultIndex;
    }
}
