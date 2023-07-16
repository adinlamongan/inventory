package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.MenuSubResponseDTO;
import com.adn.inventory.models.UserInfo;
import com.adn.inventory.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserInfoRepoImpl implements UserInfoRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<UserInfo> getUserInfo(String username) {
        String sql = "select master_user.id, master_user.name AS username, master_user.password, role.name AS role_name from master_user "
                + "  join role ON role.id=master_user.role_id WHERE master_user.name=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                (rs, rowNum) ->
                        new UserInfo(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("role_name")
                        ), username));
    }

    @Override
    public List<MenuSubResponseDTO> getAksesRole(int userId) {
        String sql = "SELECT r.*,m.nama AS nama_menu, m.icon FROM master_user mu "
                + "JOIN role_akses_detail r ON mu.role_id=r.role_id "
                + "JOIN menu m ON m.id=r.menu_id "
                + "WHERE mu.id = ? "
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
                        ),userId);
    }


}
