package com.adn.inventory.controller;

import com.adn.inventory.dto.AppUserRequestDTO;
import com.adn.inventory.dto.SupplierRequestDTO;
import com.adn.inventory.services.AppUserService;
import com.adn.inventory.services.SupplierService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AppUserRestController {

    private AppUserService appUserService;
    private ResponseSuccessDTO successDTO;

    @PostMapping("/userdata")
    public DatatabelOutput<?> listData(@RequestBody PagingRequest pagingRequest) {
        return appUserService.getAppUser(pagingRequest);
    }

    @PostMapping("/user_add")
    public ResponseEntity<Object> add(@RequestBody AppUserRequestDTO dto) {
        appUserService.addAppUser(dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PostMapping("/user_edit")
    public ResponseEntity<Object> edit(@RequestBody AppUserRequestDTO dto) {
        appUserService.editAppUser(dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PostMapping("/user_delete")
    public ResponseEntity<Object> delete(@RequestBody AppUserRequestDTO dto) {
        appUserService.deleteAppUser(dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    
}
