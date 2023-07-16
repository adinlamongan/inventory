package com.adn.inventory.util;

import com.adn.inventory.security.CustomUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAktif {

    public  int userIdInfo(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
        return  user.getUserId();
    }
}
