package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.MenuSubResponseDTO;
import com.adn.inventory.repository.MenuSubQueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuSubQueryRepoImpl implements MenuSubQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MenuSubResponseDTO> getData(int roleId) {
        String sql = "SELECT r.*,menu.nama AS nama_menu,menu.icon FROM role_akses_detail r  "
                + "JOIN menu ON menu.id=r.menu_id  "
                + "WHERE role_id=? "
                + "ORDER BY menu_id,no_urut";
        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new MenuSubResponseDTO(
                                rs.getInt("id"),
                                rs.getInt("menu_id"),
                                rs.getString("nama_menu"),
                                rs.getString("icon"),
                                rs.getString("nama"),
                                rs.getInt("no_urut"),
                                rs.getString("url"),
                                rs.getBoolean("utama"),
                                rs.getBoolean("tambah"),
                                rs.getBoolean("detail"),
                                rs.getBoolean("edit"),
                                rs.getBoolean("hapus"),
                                rs.getBoolean("verifikasi"),
                                rs.getBoolean("batal_verifikasi"),
                                rs.getBoolean("print")
                        ),roleId);
    }

    @Override
    public void insertIntoRoleAksesDetail(int roleId) {
        String sql = "INSERT INTO role_akses_detail (role_id,menu_id,nama,no_urut,utama,detail,edit,hapus,verifikasi,batal_verifikasi,print,url) "
                + "SELECT ' "+ roleId +"', menu_id,nama,no_urut,utama,detail,edit,hapus,verifikasi,batal_verifikasi,print,url FROM menu_sub";
        jdbcTemplate.update(sql);
    }
}
