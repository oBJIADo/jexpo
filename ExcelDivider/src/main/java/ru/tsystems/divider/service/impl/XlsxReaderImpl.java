package ru.tsystems.divider.service.impl;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.ExcelFileReader;

public class XlsxReaderImpl implements ExcelFileReader {

    private final FileInputStream fileInputStream;

    private final XSSFWorkbook xlsxWorkbook;

    private XSSFSheet xlsxShet;

    public XlsxReaderImpl(String xlsxFilePath, String xlsxSheetName) throws IOException {
        this(xlsxFilePath);
        this.xlsxShet = xlsxWorkbook.getSheet(xlsxSheetName);
    }

    public XlsxReaderImpl(String xlsxFilePath) throws IOException {
        this.fileInputStream = new FileInputStream(xlsxFilePath);
        this.xlsxWorkbook = new XSSFWorkbook(fileInputStream);
    }

    /**
     * Select current xlsx sheet.
     *
     * @param sheetName
     *            Sheet name.
     */
    @Override
    public void setSheet(String sheetName) throws NoShetException {
        try {
            this.xlsxShet = xlsxWorkbook.getSheet(sheetName);
        }catch (IllegalArgumentException iaexc){
            throw new NoShetException("Sheet with current name doesn't exist");
        }
    }

    public XSSFSheet getShet() {
        return xlsxShet;
    }

    /**
     * Read row from xlsx file and return it via list.
     *
     * @param rowIndex
     *            index of a row which should be readed.
     * @return List with rows values.
     */
    @Override
    public Row getRow(int rowIndex) throws NoShetException{
        if(xlsxShet == null)
            throw new NoShetException("Sheet not selected");

        return xlsxShet.getRow(rowIndex);
    }

    /**
     * l Read cell from xlsx file and return it.
     *
     * @param columnIndex
     *            Horizontal index of the cell.
     * @param rowIndex
     *            Vertical index of the cell.
     * @return Cell's value.
     */
    @Override
    public Cell getCell(int columnIndex, int rowIndex) throws NoShetException{
        if(xlsxShet == null)
            throw new NoShetException("Sheet not selected");

        Row row = xlsxShet.getRow(rowIndex);
        if (row == null)
            return null;

        return row.getCell(columnIndex);
    }

    /**
     * Closes this resource, relinquishing any underlying resources. This method is invoked automatically on objects
     * managed by the {@code try}-with-resources statement.
     *
     * <p>
     * While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to declare concrete implementations of the
     * {@code close} method to throw more specific exceptions, or to throw no exception at all if the close operation
     * cannot fail.
     *
     * <p>
     * Cases where the close operation may fail require careful attention by implementers. It is strongly advised to
     * relinquish the underlying resources and to internally <em>mark</em> the resource as closed, prior to throwing the
     * exception. The {@code
     * close} method is unlikely to be invoked more than once and so this ensures that the resources are released in a
     * timely manner. Furthermore it reduces problems that could arise when the resource wraps, or is wrapped, by
     * another resource.
     *
     * <p>
     * <em>Implementers of this interface are also strongly advised to not have the {@code close} method throw
     * {@link InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status, and runtime misbehavior is likely to occur if an
     * {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed suppressed}.
     * <p>
     * More generally, if it would cause problems for an exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>
     * Note that unlike the {@link Closeable#close close} method of {@link Closeable}, this {@code close} method is
     * <em>not</em> required to be idempotent. In other words, calling this {@code close} method more than once may have
     * some visible side effect, unlike {@code Closeable.close} which is required to have no effect if called more than
     * once.
     * <p>
     * However, implementers of this interface are strongly encouraged to make their {@code close} methods idempotent.
     *
     * @throws Exception
     *             if this resource cannot be closed
     */
    @Override
    public void close() throws IOException {
        /**
         * before close file stream, should to close "package". This actions described into XSSFWorkbook's javadoc in
         * it's class
         */
        xlsxWorkbook.getPackage().close();
        fileInputStream.close();
    }
}
