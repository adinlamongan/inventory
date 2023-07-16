package com.adn.inventory.util;

import com.adn.inventory.repository.NomorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class NomorOtomatis {

    @Autowired
    private NomorRepo nomorRepo;

    public String getNomor(Date tanggal, String namaTabel) {
        //Date dateParam = parseDate(tanggal);
        StringBuilder sb = new StringBuilder();
        String leadingNomor = new SimpleDateFormat("yyMM").format(tanggal);
        sb.append(leadingNomor);
        String nomor = nomorRepo.getNomor(namaTabel,leadingNomor);
        if (nomor == null) {
            sb.append("00001");
            return sb.toString();
        }

        switch (nomor.length()) {
            case 1:
                sb.append("0000");
                break;
            case 2:
                sb.append("000");
                break;
            case 3:
                sb.append("00");
                break;
            case 4:
                sb.append("0");
                break;
        }
        sb.append(nomor);
        return sb.toString();
    }

//    public String getNomor1(String tanggal, String namaTabel ){
//        Date dateParam = parseDate(tanggal);
//        StringBuilder sb = new StringBuilder();
//        sb.append(new SimpleDateFormat("yyMM").format(dateParam));
//        String nomor = nomorDao.getNomor(namaTabel);
//        if (nomor == null){
//            sb.append("00001");
//            return sb.toString();
//        }
//
//        switch (nomor.length()) {
//            case 1:
//                sb.append("0000");
//                break;
//            case 2:
//                sb.append("000");
//                break;
//            case 3:
//                sb.append("00");
//                break;
//            case 4:
//                sb.append("0");
//                break;
//        }
//        sb.append(nomor);
//
//        return sb.toString();
//    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
