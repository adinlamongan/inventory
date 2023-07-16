package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.models.KartuStokQuery;
import com.adn.inventory.models.KartuStokQuerySaldoAwal;
import com.adn.inventory.repository.KartuStokRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KartuStokRepoImpl implements KartuStokRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<KartuStokQuery> getData(KartuStokRequestDTO dto) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tanggalMulai", dto.getTanggalMulai());
        parameters.put("tanggalSampai", dto.getTanggalSampai());
        parameters.put("produkId", dto.getProdukId());
        String sqlGudang = "";
        if (dto.getGudangId() > 0) {
            parameters.put("gudangId", dto.getGudangId());
            sqlGudang = "AND mg.id=:gudangId ";
        }

        String sql = "SELECT "
                + "'LPB' AS ref, "
                + "h.id, h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM lpbd d "
                + "JOIN lpbh h ON h.id=d.lpb_id "
                + "JOIN stok_gudang s ON s.gudang_id=d.gudang_id AND s.produk_id=d.produk_id  "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=d.gudang_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal >=:tanggalMulai AND h.tanggal <=:tanggalSampai "
                + sqlGudang
                + "UNION ALL "
                + "SELECT  "
                + "'PND' AS ref, "
                + "h.id,h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM pindah_barangd d "
                + "JOIN pindah_barangh h ON h.id=d.pindah_barang_id "
                + "JOIN stok_gudang s ON s.gudang_id=h.gudang_tujuan_id AND s.produk_id=d.produk_id  "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=h.gudang_tujuan_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal >=:tanggalMulai AND h.tanggal <=:tanggalSampai "
                + sqlGudang
                + "UNION ALL "
                + "SELECT  "
                + "'PND' AS ref, "
                + "h.id,h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty * -1 as qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM pindah_barangd d "
                + "JOIN pindah_barangh h ON h.id=d.pindah_barang_id "
                + "JOIN stok_gudang s ON s.id=d.stok_id "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=h.gudang_asal_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal >=:tanggalMulai AND h.tanggal <=:tanggalSampai "
                + sqlGudang
                + "UNION ALL "
                + "SELECT  "
                + "'SJ' AS ref, "
                + "h.id,h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty * -1 as qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM surat_jaland d "
                + "JOIN surat_jalanh h ON h.id=d.surat_jalan_id "
                + "JOIN stok_gudang s ON s.id=d.stok_id "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=d.gudang_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal >=:tanggalMulai AND h.tanggal <=:tanggalSampai "
                + sqlGudang
                +"ORDER BY tanggal,id";
        try {
            //return jdbcTemplate.query(sql,
            return namedParameterJdbcTemplate.query(sql, parameters,
                    (rs, rowNum) ->
                            new KartuStokQuery(
                                    rs.getInt("id"),
                                    rs.getString("nomor"),
                                    rs.getDate("tanggal"),
                                    rs.getString("keterangan"),
                                    rs.getString("kode"),
                                    rs.getString("nama_produk"),
                                    rs.getDouble("qty"),
                                    rs.getString("nama_satuan"),
                                    rs.getInt("gudang_id"),
                                    rs.getString("nama_gudang"),
                                    rs.getInt("stok_id"),
//                                    rs.getString("table_name"),
//                                    rs.getInt("reference_id"),
//                                    rs.getString("table_name_detail"),
//                                    rs.getInt("reference_detail_id"),
                                    rs.getString("ref")

                            ));

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<KartuStokQuerySaldoAwal> getQtyAwal(KartuStokRequestDTO dto) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tanggalMulai", dto.getTanggalMulai());
        parameters.put("tanggalSampai", dto.getTanggalSampai());
        parameters.put("produkId", dto.getProdukId());
        String sqlGudang = "";
        String sqlGudangv = "";
        if (dto.getGudangId() > 0) {
            parameters.put("gudangId", dto.getGudangId());
            sqlGudang = "AND mg.id=:gudangId ";
            sqlGudangv = "WHERE vv.gudang_id=:gudangId ";
        }
        String sql = "SELECT * FROM ( SELECT mg.id as gudang_id,mg.nama as nama_gudang, COALESCE( SUM(v.qty),0) AS qty_awal  "
                + "FROM master_gudang mg "
                + " LEFT JOIN "
                + "( SELECT "
                + "'LPB' AS ref, "
                + "h.id, h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM lpbd d "
                + "JOIN lpbh h ON h.id=d.lpb_id "
                + "JOIN stok_gudang s ON s.gudang_id=d.gudang_id AND s.produk_id=d.produk_id  "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=d.gudang_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal <:tanggalMulai "
                + sqlGudang
                + "UNION ALL "
                + "SELECT  "
                + "'PND' AS ref, "
                + "h.id,h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM pindah_barangd d "
                + "JOIN pindah_barangh h ON h.id=d.pindah_barang_id "
                + "JOIN stok_gudang s ON s.gudang_id=h.gudang_tujuan_id AND s.produk_id=d.produk_id  "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=h.gudang_tujuan_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal <:tanggalMulai "
                + sqlGudang
                + "UNION ALL "
                + "SELECT  "
                + "'PND' AS ref, "
                + "h.id,h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty * -1 as qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM pindah_barangd d "
                + "JOIN pindah_barangh h ON h.id=d.pindah_barang_id "
                + "JOIN stok_gudang s ON s.id=d.stok_id "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=h.gudang_asal_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal <:tanggalMulai "
                + sqlGudang
                + "UNION ALL "
                + "SELECT  "
                + "'PND' AS ref, "
                + "h.id,h.nomor,h.tanggal,h.keterangan, "
                + "mp.kode,mp.nama AS nama_produk, "
                + "d.qty * -1 as qty, "
                + "ms.nama AS nama_satuan, "
                + "mg.id AS gudang_id, "
                + "mg.nama AS nama_gudang, "
                + "s.id AS stok_id "
                + "FROM surat_jaland d "
                + "JOIN surat_jalanh h ON h.id=d.surat_jalan_id "
                + "JOIN stok_gudang s ON s.id=d.stok_id "
                + "JOIN master_produk mp ON mp.id=d.produk_id "
                + "JOIN master_gudang mg ON mg.id=d.gudang_id "
                + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
                + "WHERE mp.id =:produkId "
                + "AND h.verifikasi = true "
                + "AND h.tanggal <:tanggalMulai "
                + sqlGudang
                +" ) as v  ON mg.id=v.gudang_id "
                + "GROUP BY mg.id,mg.nama ) as vv " + sqlGudangv;
        try {
            //return jdbcTemplate.query(sql,
            return namedParameterJdbcTemplate.query(sql, parameters,
                    (rs, rowNum) ->
                            new KartuStokQuerySaldoAwal(
                                    rs.getInt("gudang_id"),
                                    rs.getString("nama_gudang"),
                                    rs.getDouble("qty_awal")

                            ));

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
