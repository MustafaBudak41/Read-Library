package com.RL.helper;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.RL.domain.Author;


public class ExcelReportHelper {


    static String SHEET_AUTHOR = "Author";
    static String[] AUTHOR_HEADERS = { "id", "Name", "Builtin" };



    public static ByteArrayInputStream getAuthorsExcelReport(List<Author> authors) throws IOException {
        Workbook workBook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workBook.createSheet(SHEET_AUTHOR);

        Row headerRow = sheet.createRow(0);

        for (int column = 0; column < AUTHOR_HEADERS.length; column++) {
            Cell cell = headerRow.createCell(column);
            cell.setCellValue(AUTHOR_HEADERS[column]);
        }

        int rowId = 1;

        for (Author author : authors) {
            Row row = sheet.createRow(rowId++);

            row.createCell(0).setCellValue(author.getId());
            row.createCell(1).setCellValue(author.getName());
            row.createCell(2).setCellValue(author.getBuiltIn()? ("True"):("False"));

        }

        workBook.write(out);

        workBook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }


}