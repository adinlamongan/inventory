package com.adn.inventory.security;

import com.adn.inventory.dto.GroupMenuResponseDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class CustomUser extends User {

    private int userId;


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public List<GroupMenuResponseDTO> groupMenuResponseDTOList;


    public List<GroupMenuResponseDTO> getGroupMenuResponseDTOList() {
        return groupMenuResponseDTOList;
    }

    public void setGroupMenuResponseDTOList(List<GroupMenuResponseDTO> groupMenuResponseDTOList) {
        this.groupMenuResponseDTOList = groupMenuResponseDTOList;
    }


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, int userId, List<GroupMenuResponseDTO> groupMenuResponseDTOList) {
        super(username, password, authorities);
        setUserId(userId);
        setGroupMenuResponseDTOList(groupMenuResponseDTOList);

    }
}
