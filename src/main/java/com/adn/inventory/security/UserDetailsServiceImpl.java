package com.adn.inventory.security;

import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.dto.MenuSubResponseDTO;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.UserInfo;
import com.adn.inventory.repository.UserInfoRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserInfoRepo userInfoRepo;

    private ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepo.getUserInfo(username).orElseThrow(()-> new ResourceNotFoundException("username not found"));

        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRoleName());


        Map<Integer, List<MenuSubResponseDTO>> data = userInfoRepo.getAksesRole(userInfo.getId()).stream().collect(Collectors.groupingBy(
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


//        UserDetails userDetails = (UserDetails) new User(userInfo.getUsername(),userInfo.getPassword(), List.of(authority));
        return new CustomUser(userInfo.getUsername(),userInfo.getPassword(), List.of(authority),userInfo.getId(), dtos);


    }
}
