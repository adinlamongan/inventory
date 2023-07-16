package com.adn.inventory.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adn.inventory.dto.ProdukResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.dto.KartuStokResponseDTO;
import com.adn.inventory.excelGenerator.KartuStokExcelGenerator;
import com.adn.inventory.services.ProdukService;
import com.adn.inventory.services.KartuStokService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class KartuStokRestController {
    private final KartuStokService kartuStokService;

    private final ProdukService produkService;


    private static KartuStokRequestDTO copyDTO;

    @PostMapping("kartu_stok/list_data")
    public ResponseEntity<List<KartuStokResponseDTO>> listData(@RequestBody KartuStokRequestDTO dto) {
        return ResponseEntity.ok().body(kartuStokService.getData(dto));
    }

    @GetMapping("kartu_stok/list_produk")
    private ResponseEntity<List<ProdukResponseDTO>> getListBarangAutocomplete(
            @RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(produkService.getListBarangAutocomplete(keyword));
    }

    @GetMapping("kartu_stok/excel")
    private void getExcel(HttpServletResponse response) throws Exception {
        File licenseFile = new File ("storage/kartustok.xlsx");
        InputStream is = new FileInputStream(licenseFile);

        // set file as attached data and copy file data to response output stream
        response.setHeader("Content-Disposition", "attachment; filename=kartustok.xlsx");
        FileCopyUtils.copy(is, response.getOutputStream());

        // delete file on server file system
        licenseFile.delete();

        // close stream and return to view
        response.flushBuffer();
    }

    @PostMapping("kartu_stok/print")
    public String exportIntoPdfFile(@RequestBody KartuStokRequestDTO dto, HttpServletRequest request) {

        //applicationConfiguration.copyDTO(dto.getProdukId());
        copyDTO = dto;
        return  request.getScheme() + "://"
                + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath()
                + "/kartu_stok/print_data";
    }

    @PostMapping("kartu_stok/list_data_excel")
    public String exportIntoExcelFile(@RequestBody KartuStokRequestDTO dto, HttpServletRequest request) throws IOException {
        List<KartuStokResponseDTO> listOfStudents = kartuStokService.getData(dto);
        KartuStokExcelGenerator generator = new KartuStokExcelGenerator(listOfStudents, dto);
        generator.generateExcelFile();
        return  request.getScheme() + "://"
                + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath()
                + "/kartu_stok/excel";
    }






    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static KartuStokRequestDTO getCopyDTO() {
        return copyDTO;
    }
}
