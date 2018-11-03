package ru.tsystems.divider.service.impl;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.tsystems.divider.service.api.ExcelFileWriter;

public class XlsxWriterImpl implements ExcelFileWriter {

    private final static int MAX_COUNT = 16383;

    private final String fileName;

    private final XSSFWorkbook xlsxWorkbook;

    private XSSFSheet xlsxShet;

    public XlsxWriterImpl(String fileName, String sheetName) {
        this.fileName = fileName;
        xlsxWorkbook = new XSSFWorkbook();
        xlsxShet = xlsxWorkbook.createSheet(sheetName);
    }

    @Override
    public void setTextCellValue(String value, int columnIndex, int rowIndex) {

        if(columnIndex>MAX_COUNT) {
            rowIndex += columnIndex / MAX_COUNT;
            columnIndex = columnIndex % MAX_COUNT;
        }

        Cell cell = getCell(columnIndex, rowIndex);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(value);

    }

    @Override
    public void appendTextCellValue(String value, int columnIndex, int rowIndex) {
        Cell cell = getCell(columnIndex, rowIndex);
        cell.setCellValue(cell.getStringCellValue() + value);
    }

    @Override
    public String getCellTextValue(int columnIndex, int rowIndex) {
        return getCell(columnIndex, rowIndex).getStringCellValue();
    }

    private Cell getCell(int columnIndex, int rowIndex) {
        Row row = xlsxShet.getRow(rowIndex);
        if (row == null)
            row = xlsxShet.createRow(rowIndex);

        Cell cell = row.getCell(columnIndex);
        if (cell == null)
            cell = row.createCell(columnIndex);

        return cell;
    }

    @Override
    public void setSheetName(String sheetName) {
        this.xlsxShet = xlsxWorkbook.createSheet(sheetName);
    }

    @Override
    public void getSheetName(String sheetName) {
        this.xlsxShet.getSheetName();
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
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        xlsxWorkbook.write(fileOutputStream);
        xlsxWorkbook.getPackage().close();
        fileOutputStream.close();
    }
}
