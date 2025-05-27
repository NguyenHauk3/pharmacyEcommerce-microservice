package com.microsevice.ePharmaMS.user.util;

import com.microsevice.ePharmaMS.user.modal.User;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class UserExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<User> listUsers;

    public UserExport(List<User> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Email", style);
        createCell(row, 2, "Tên", style);
        createCell(row, 3, "Mật khẩu", style);
        createCell(row, 4, "Số điện thoại", style);
        createCell(row, 5, "Giới tính", style);
        createCell(row, 6, "Địa chỉ", style);
        createCell(row, 7, "Quyen", style);


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getName(), style);
            createCell(row, columnCount++, user.getPassword(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getGender(), style);
            createCell(row, columnCount++, user.getAddress(), style);
            createCell(row, columnCount++, user.getRole(), style);
        }
    }

    public void export(HttpServletResponse response) throws Exception {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
