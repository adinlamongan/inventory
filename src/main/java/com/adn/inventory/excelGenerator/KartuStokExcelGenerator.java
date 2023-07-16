package com.adn.inventory.excelGenerator;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.dto.KartuStokResponseDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KartuStokExcelGenerator {
    private List<KartuStokResponseDTO> listData;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private KartuStokRequestDTO kartuStokRequestDTO;

    public KartuStokExcelGenerator(List<KartuStokResponseDTO> listData, KartuStokRequestDTO kartuStokRequestDTO) {
        this.kartuStokRequestDTO = kartuStokRequestDTO;
        this.listData = listData;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("kartu_stok");
        Row row = sheet.createRow(0);
        CellStyle styleTitle = workbook.createCellStyle();
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeight(14);
        styleTitle.setFont(fontTitle);
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "Kartu Stok", styleTitle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        row = sheet.createRow(1);
        createCell(row, 0, "Tanggal", style);
        createCell(row, 1, new SimpleDateFormat("dd-MM-yyyy").format(kartuStokRequestDTO.getTanggalMulai()) + " S/D " + new SimpleDateFormat("dd-MM-yyyy").format(kartuStokRequestDTO.getTanggalSampai()), style);
        row = sheet.createRow(2);
        createCell(row, 0, "Barang", style);
        createCell(row, 1, kartuStokRequestDTO.getNamaProduk(), style);
        row = sheet.createRow(3);
        createCell(row, 0, "Gudang", style);
        createCell(row, 1, kartuStokRequestDTO.getGudangId() == 0 ? "Semua Gudang" : listData.get(0).getNamaGudang(), style);
        //createCell(row, 3, "TELPON.", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Date) {
            cell.setCellValue((Date) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        AtomicInteger rowCount = new AtomicInteger(5);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        CellStyle styleGudang = workbook.createCellStyle();
        XSSFFont fontGudang = workbook.createFont();
        fontGudang.setFontHeight(11);
        styleGudang.setFont(font);
        styleGudang.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        // and solid fill pattern produces solid grey cell fill
        styleGudang.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle styleColHeader = workbook.createCellStyle();
        XSSFFont fontColHeader = workbook.createFont();
        fontColHeader.setFontHeight(11);
        fontColHeader.setColor(IndexedColors.WHITE.index);
        styleColHeader.setFont(fontColHeader);
        styleColHeader.setFillForegroundColor(IndexedColors.BLACK.index);
        styleColHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        for (KartuStokResponseDTO record : listData) {
            Row rowGudang = sheet.createRow(rowCount.getAndIncrement());
            int columnCount = 0;
            style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
            style.setFillPattern(FillPatternType.NO_FILL);
            createCell(rowGudang, columnCount++, record.getNamaGudang(), styleGudang);
            sheet.addMergedRegion(new CellRangeAddress(rowCount.getPlain() - 1, rowCount.getPlain() - 1, 0, 8));
            Row rowHeaderColumn = sheet.createRow(rowCount.getAndIncrement());
            createCell(rowHeaderColumn, 0, "No", styleColHeader);
            createCell(rowHeaderColumn, 1, "Nomor", styleColHeader);
            createCell(rowHeaderColumn, 2, "Tanggal", styleColHeader);
            createCell(rowHeaderColumn, 3, "Keterangan", styleColHeader);
            createCell(rowHeaderColumn, 4, "Kode", styleColHeader);
            createCell(rowHeaderColumn, 5, "Nama", styleColHeader);
            createCell(rowHeaderColumn, 6, "Masuk", styleColHeader);
            createCell(rowHeaderColumn, 7, "Keluar", styleColHeader);
            createCell(rowHeaderColumn, 8, "Satuan", styleColHeader);

            Row rowQtyAwal = sheet.createRow(rowCount.getAndIncrement());
            createCell(rowQtyAwal, 1, "Qty Awal", style);
            createCell(rowQtyAwal, 6, record.getQtyAwal(), style);
            sheet.addMergedRegion(new CellRangeAddress(rowCount.getPlain() - 1, rowCount.getPlain() - 1, 1, 5));
            sheet.addMergedRegion(new CellRangeAddress(rowCount.getPlain() - 1, rowCount.getPlain() - 1, 6, 7));
            double stokAkhir = 0;
            if (record.getDetail() != null) {
                AtomicInteger i = new AtomicInteger(0);
                AtomicReference<Double> qtyAkhir = new AtomicReference<>(record.getQtyAwal());
                record.getDetail().forEach((e) -> {
                            Row rowD = sheet.createRow(rowCount.getAndIncrement());
                            i.getAndIncrement();
                            double masuk = e.getQty() > 0 ? e.getQty() : 0;
                            double keluar = e.getQty() < 0 ? e.getQty() * -1 : 0;
                            qtyAkhir.set(qtyAkhir.get() + masuk - keluar);
                            createCell(rowD, 0, i.getPlain(), style);
                            createCell(rowD, 1, e.getRef() + e.getNomor(), style);
                            createCell(rowD, 2, new SimpleDateFormat("dd-MM-yyyy").format(e.getTanggal()), style);
                            createCell(rowD, 3, e.getKeterangan(), style);
                            createCell(rowD, 4, e.getKode(), style);
                            createCell(rowD, 5, e.getNamaProduk(), style);
                            createCell(rowD, 6, masuk, style);
                            createCell(rowD, 7, keluar, style);
                            createCell(rowD, 8, e.getNamaSatuan(), style);
                        }
                );
                stokAkhir = qtyAkhir.getPlain();
            }
            Row rowQtyAkhir = sheet.createRow(rowCount.getAndIncrement());
            createCell(rowQtyAkhir, 1, "Qty Akhir", style);
            createCell(rowQtyAkhir, 6, stokAkhir, style);
            sheet.addMergedRegion(new CellRangeAddress(rowCount.getPlain() - 1, rowCount.getPlain() - 1, 1, 5));
            sheet.addMergedRegion(new CellRangeAddress(rowCount.getPlain() - 1, rowCount.getPlain() - 1, 6, 7));

        }
    }

    public void generateExcelFile() throws IOException {
        writeHeader();
        write();
        try (FileOutputStream outputStream = new FileOutputStream("storage/kartustok.xlsx")) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
