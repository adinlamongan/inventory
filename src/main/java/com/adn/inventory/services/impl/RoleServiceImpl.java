package com.adn.inventory.services.impl;

import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.dto.MenuSubResponseDTO;
import com.adn.inventory.dto.RoleRequestDTO;
import com.adn.inventory.dto.RoleSettingRequestDTO;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.Role;
import com.adn.inventory.models.RoleAksesDetail;
import com.adn.inventory.repository.MenuSubQueryRepo;
import com.adn.inventory.repository.RoleAksesDetailRepo;
import com.adn.inventory.repository.RoleRepo;
import com.adn.inventory.services.RoleService;
import com.adn.inventory.util.PageDataTable;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepo roleRepo;

    private PageDataTable pageDataTable;

    private MenuSubQueryRepo menuSubQueryRepo;
    private ModelMapper modelMapper;

    private RoleAksesDetailRepo roleAksesDetailRepo;

    @Override
    public DatatabelOutput<Role> getRoles(PagingRequest pagingRequest) {
        Pageable pageable = pageDataTable.pagination(pagingRequest);

        Page<Role> roles = null;

        String keyword = pagingRequest.getSearch().getValue();
        if (keyword.isEmpty()) {
            roles = roleRepo.findAll(pageable);
        } else {
            roles = roleRepo.findByNameContainingAllIgnoreCase(keyword, pageable);
        }
        List<Role> roleList = roles.getContent();
        DatatabelOutput<Role> result = new DatatabelOutput<>();
        result.setData(roleList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(roleRepo.count());
        result.setRecordsFiltered(roles.getTotalElements());
        return result;
    }

    @Override
    public Role getRoleByid(int id) {
        return roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid role id"));
    }

    @Override
    public void editRole(RoleRequestDTO dto) {
        Role role = roleRepo.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("invalid role id"));
        role.setName(dto.getName());
        roleRepo.save(role);

    }

    @Override
    @Transactional
    public void addRole(RoleRequestDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        roleRepo.save(role);
        menuSubQueryRepo.insertIntoRoleAksesDetail(role.getId());

    }

    @Override
    public void deleteRole(RoleRequestDTO dto) {

    }

    @Override
    @Transactional
    public void settingSaveRole(RoleSettingRequestDTO dto) {
        roleAksesDetailRepo.deleteByRoleId(dto.getRoleId());
        int length = dto.getMenuId().size();
        for (int i = 0; i < length; i++) {
            RoleAksesDetail roleAksesDetail = new RoleAksesDetail();
            roleAksesDetail.setRoleId(dto.getRoleId());
            roleAksesDetail.setMenuId(dto.getMenuId().get(i));
            roleAksesDetail.setNama(dto.getNama().get(i));
            roleAksesDetail.setNoUrut(dto.getNoUrut().get(i));
            roleAksesDetail.setUrl(dto.getUrl().get(i));
            roleAksesDetail.setUtama(dto.getUtama().get(i));
            roleAksesDetail.setTambah(dto.getTambah().get(i));
            roleAksesDetail.setDetail(dto.getDetail().get(i));
            roleAksesDetail.setEdit(dto.getEdit().get(i));
            roleAksesDetail.setHapus(dto.getHapus().get(i));
            roleAksesDetail.setVerifikasi(dto.getVerifikasi().get(i));
            roleAksesDetail.setBatalVerifikasi(dto.getBatalVerifikasi().get(i));
            roleAksesDetail.setPrint(dto.getPrint().get(i));
            roleAksesDetailRepo.save(roleAksesDetail);
        }

    }

    @Override
    public List<GroupMenuResponseDTO> getMenuSub(int id) {
        Map<Integer, List<MenuSubResponseDTO>> data = menuSubQueryRepo.getData(id).stream().collect(Collectors.groupingBy(
                MenuSubResponseDTO::getMenu_id,
                Collectors.toList()
        ));
        List<GroupMenuResponseDTO> dtos = data.values().stream().map(e -> {
            GroupMenuResponseDTO dto = new GroupMenuResponseDTO();
            dto.setMenu(e.get(0).getNamaMenu());
            List<MenuSubResponseDTO> listD = e.stream().map(ee -> {
//                MenuSubResponseDTO d = new MenuSubResponseDTO();
////                d.setId(ee.getId());
////                d.setMenu_id(ee.getMenu_id());
////                d.setNama(ee.getNama());
////                d.setNama_menu(ee.getNama_menu());
////                d.setUtama(ee.getUtama());
////                d.setTambah(ee.getTambah());
////                d.setDetail(ee.getDetail());
////                d.setEdit(ee.getEdit());
////                d.setHapus(ee.getHapus());
////                d.
//                d = modelMapper.map(ee,MenuSubResponseDTO.class);
                return modelMapper.map(ee, MenuSubResponseDTO.class);
            }).collect(Collectors.toList());
            dto.setLisMenuSub(listD);
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }
}
