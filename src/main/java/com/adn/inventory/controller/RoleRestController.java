package com.adn.inventory.controller;

import com.adn.inventory.dto.RoleRequestDTO;
import com.adn.inventory.dto.RoleSettingRequestDTO;
import com.adn.inventory.dto.SupplierRequestDTO;
import com.adn.inventory.models.Role;
import com.adn.inventory.services.RoleService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoleRestController {

    private RoleService roleService;
    private ResponseSuccessDTO successDTO;


    @PostMapping("/roledata")
    public DatatabelOutput<Role> listData(@RequestBody PagingRequest pagingRequest) {
        return roleService.getRoles(pagingRequest);
    }

    @PostMapping("/role_add")
    public ResponseEntity<Object> add(@RequestBody RoleRequestDTO dto) {
        roleService.addRole(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PostMapping("/role_edit")
    public ResponseEntity<Object> edit(@RequestBody RoleRequestDTO dto) {
        roleService.editRole(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PostMapping("/role_delete")
    public ResponseEntity<Object> delete(@RequestBody RoleRequestDTO dto) {
        roleService.deleteRole(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }


    @PostMapping("/role_setting")
    public ResponseEntity<Object> saveSetting(@RequestBody RoleSettingRequestDTO dto) {
        roleService.settingSaveRole(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }


}
