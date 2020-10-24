package main.web.service_web;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import main.entity.Calls;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelFileExporter {
    static DateTimeFormatter formatterDate = DateTimeFormatter.ISO_LOCAL_DATE;
    static DateTimeFormatter formatterTime = DateTimeFormatter.ISO_LOCAL_TIME;

    public static ByteArrayInputStream callsListToExcelFile(List<Calls> calls) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Зведений звіт по всім дзвінкам");

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("Хто дзвонить");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Куди дзвОнили");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Дата");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Коли дзвонили, о котрій");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Довжина дзвінка, сек");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows for each customer
            for(int i = 0; i < calls.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(calls.get(i).getCalling_num());
                dataRow.createCell(1).setCellValue(calls.get(i).getDialed_num());
                dataRow.createCell(2).setCellValue(calls.get(i).getLocalDateTime().format(formatterDate));
                dataRow.createCell(3).setCellValue(calls.get(i).getLocalDateTime().format(formatterTime));
                dataRow.createCell(4).setCellValue(calls.get(i).getSec_dur());
            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}