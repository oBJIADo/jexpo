package ru.tsystems.divider;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import ru.tsystems.divider.exceptions.ExcelFormatException;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.excel.ExcelReader;
import ru.tsystems.divider.service.api.functional.JiraToDBConverter;
import ru.tsystems.divider.service.impl.excel.XlsFileReader;
import ru.tsystems.divider.service.impl.excel.XlsxFileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class DivideRunner {
    private static final Logger logger = Logger.getLogger(DivideRunner.class);

    private static final String EXCEL_OLD_FORMAT = "xls";

    private static final String EXCEL_FORMAT = "xlsx";

    public static void main(String args[]) {

        String fileName;
        String sheetName;
        int startRowIndex;
        int convertingParam;

        try {
            convertingParam = Integer.valueOf(args[0]);
            fileName = args[1];
            sheetName = args[2];
            startRowIndex = Integer.valueOf(args[3]);

            // Start spring boot app and get Beam from context
            ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(DivideRunner.class);
            JiraToDBConverter transfer = configurableApplicationContext.getBean(JiraToDBConverter.class);

            if (convertingParam == 1)
                startTasksReader(fileName, sheetName, startRowIndex, transfer);
            else if (convertingParam == 2)
                startCommentReader(fileName, sheetName, startRowIndex, transfer);
            else
                throw new IllegalArgumentException("Converting param is wrong: " + convertingParam);

        } catch (IndexOutOfBoundsException indexexc) {
            System.out.println(indexexc.getMessage());
            System.out
                    .println("Need 4 arguments:\n  1) Read mode.\n  2) File name with path.\n  3) Sheet name in excel file.\n  4) Index of the first readable row.");
            logger.fatal("Arguments error: " + indexexc.getMessage());
        } catch (NoShetException nsexc) {
            System.out.println(nsexc.getMessage());
            System.out.println("Wrong sheet name.");
            logger.fatal("Wrong sheet name.");
        } catch (ExcelFormatException excelExc) {
            System.out.println(excelExc.getMessage());
            logger.fatal(excelExc.getMessage());
        } catch (FileNotFoundException fnfexc) {
            System.out.println(fnfexc.getMessage());
            System.out.println("Wrong file name");
            logger.fatal("Wrong file name");
        } catch (IOException ioexc) {
            System.out.println(ioexc.getMessage());
            System.out.println("Can't to read file.");
            logger.fatal("Can't to read file.");
        }
    }

    private static void startTasksReader(String fileName, String sheetName, int startRowIndex,
                                         JiraToDBConverter converter) throws ExcelFormatException, IOException, NoShetException { //todo: Simple this shit
        String fileFormat = getFormat(fileName);
        if (EXCEL_FORMAT.equalsIgnoreCase(fileFormat)) {
            try (ExcelReader excelReader = new XlsxFileReader(fileName, sheetName)) {
                converter.transferAll(excelReader, startRowIndex);
            } catch (IOException exc) {
                logger.error("IOexception." + exc.getMessage());
                throw exc;
            } catch (NoShetException nsexc) {
                logger.error("NoSheetException: " + nsexc.getMessage());
                throw nsexc;
            }
        } else if (EXCEL_OLD_FORMAT.equalsIgnoreCase(fileFormat)) {
            try (ExcelReader excelReader = new XlsFileReader(fileName, sheetName)) { //todo
                converter.transferAll(excelReader, startRowIndex);
            } catch (IOException exc) {
                logger.error("IOexception." + exc.getMessage());
                throw exc;
            } catch (NoShetException nsexc) {
                logger.error("NoSheetException: " + nsexc.getMessage());
                throw nsexc;
            }
        } else {
            throw new ExcelFormatException(fileFormat, fileName);
        }
    }


    private static void startCommentReader(String fileName, String sheetName, int startRowIndex,
                                           JiraToDBConverter converter) throws ExcelFormatException, IOException, NoShetException {
        String fileFormat = getFormat(fileName);
        if (EXCEL_FORMAT.equalsIgnoreCase(fileFormat)) {
            try (ExcelReader excelReader = new XlsxFileReader(fileName, sheetName)) {
                converter.transferAllComments(excelReader, startRowIndex);
            } catch (IOException exc) {
                logger.error("IOexception." + exc.getMessage());
                throw exc;
            } catch (NoShetException nsexc) {
                logger.error("NoSheetException: " + nsexc.getMessage());
                throw nsexc;
            }
        } else if (EXCEL_OLD_FORMAT.equalsIgnoreCase(fileFormat)) {
            try (ExcelReader excelReader = new XlsFileReader(fileName, sheetName)) {
                converter.transferAllComments(excelReader, startRowIndex);
            } catch (IOException exc) {
                logger.error("IOexception." + exc.getMessage());
                throw exc;
            } catch (NoShetException nsexc) {
                logger.error("NoSheetException: " + nsexc.getMessage());
                throw nsexc;
            }
        } else {
            throw new ExcelFormatException(fileFormat, fileName);
        }
    }

    private static String getFormat(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex + 1);
    }
}
