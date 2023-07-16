package com.adn.inventory.services.impl;

import com.adn.inventory.dto.AppUserRequestDTO;
import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.dto.MenuSubResponseDTO;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.AppUser;
import com.adn.inventory.models.AppUserQuery;
import com.adn.inventory.repository.AppUserQueryRepo;
import com.adn.inventory.repository.AppUserRepo;
import com.adn.inventory.repository.UserInfoRepo;
import com.adn.inventory.services.AppUserService;
import com.adn.inventory.util.UserAktif;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.Order;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private AppUserQueryRepo appUserQueryRepo;

    private AppUserRepo appUserRepo;

    private PasswordEncoder passwordEncoder;

    private UserInfoRepo userInfoRepo;

    private ModelMapper modelMapper;

    private UserAktif userAktif;


    @Override
    public DatatabelOutput<AppUserQuery> getAppUser(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<AppUserQuery> appUser = appUserQueryRepo.findAppUser(keyword, sortBy, direction,
                pageable);

        List<AppUserQuery> appUserQueryList = appUser.getContent();
        DatatabelOutput<AppUserQuery> result = new DatatabelOutput<>();
        result.setData(appUserQueryList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(appUserQueryRepo.getTotalRecord());
        result.setRecordsFiltered(appUser.getTotalElements());
        return result;
    }

    @Override
    public AppUserQuery getAppUserByid(int userId) {
        return appUserQueryRepo.findAppUserByid(userId);
    }

    @Override
    public void addAppUser(AppUserRequestDTO dto) {
        AppUser appUser = new AppUser();
        appUser.setUsername(dto.getName());
        appUser.setRoleId(dto.getRoleId());
        appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        appUser.setEnabled(dto.getEnabled());
        appUserRepo.save(appUser);
    }

    @Override
    public void editAppUser(AppUserRequestDTO dto) {
        AppUser appUser = appUserRepo.findById(dto.getId()).orElseThrow(()-> new ResourceNotFoundException("invalid id"));
        appUser.setUsername(dto.getName());
        appUser.setRoleId(dto.getRoleId());
        //appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        appUser.setEnabled(dto.getEnabled());
        appUserRepo.save(appUser);
    }

    @Override
    public void deleteAppUser(AppUserRequestDTO dto) {
        appUserRepo.deleteById(dto.getId());
    }

    @Override
    public List<GroupMenuResponseDTO> getUserInfoRole(int userId) {
        //int userId = userAktif.userIdInfo();
        Map<Integer, List<MenuSubResponseDTO>> data = userInfoRepo.getAksesRole(userId).stream().collect(Collectors.groupingBy(
                MenuSubResponseDTO::getMenu_id,
                Collectors.toList()
        ));
        AtomicInteger i = new AtomicInteger();
        List<GroupMenuResponseDTO> dtos = data.values().stream().map(e -> {
            GroupMenuResponseDTO dto = new GroupMenuResponseDTO();
            dto.setMenu(e.get(0).getNamaMenu());
            dto.setIcon(e.get(0).getIcon());
            i.set(0);
            List<MenuSubResponseDTO> listD = e.stream().map(ee -> {
                if(ee.getUtama()){
                    i.getAndIncrement();
                }
                return modelMapper.map(ee, MenuSubResponseDTO.class);
            }).collect(Collectors.toList());
            if(i.get() > 0){
                dto.setAktif(Boolean.TRUE);
            }
            dto.setLisMenuSub(listD);
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }
}
