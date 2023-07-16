package com.adn.inventory.advice;

import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.exceptions.ResourceNotFoundExceptionForHtml;
import com.adn.inventory.exceptions.ResourceUnauthorizedException;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.AppUserService;

import org.apache.pdfbox.contentstream.operator.text.ShowText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private List<GroupMenuResponseDTO> akses;

    @Autowired
    private AppUserService appUserService;

    @ModelAttribute
    public void globalAttributes(Model model, HttpServletRequest request, Principal principal) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(principal != null){
            model.addAttribute("msg", "Message for every view");
            model.addAttribute("username", securityContext.getAuthentication().getName());


            if (request.getMethod().equals("GET")) {
                
                CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
                akses = user.getGroupMenuResponseDTOList();
                //List<GroupMenuResponseDTO> akses = appUserService.getUserInfoRole(2);
                //akses = appUserService.getUserInfoRole(2);
                model.addAttribute("akses", akses);
                String str = request.getRequestURI().substring(1);
                String url;
                if (str.indexOf("/") > 0){
                    url = str.substring(0, str.indexOf("/"));
                }else {
                    url = str;
                }

                akses.forEach(e -> e.getLisMenuSub().forEach((ee)->{
                    if(ee.getUrl().equals(url)){
                        boolean akses_menu ;
                        if (str.contains("detail")){
                            akses_menu = ee.getDetail();
                        } else if (str.contains("add")) {
                            akses_menu = ee.getTambah();
                        } else if (str.contains("edit")) {
                            akses_menu = ee.getEdit();
                        }else {
                            akses_menu = ee.getUtama();
                        }
                        model.addAttribute("akses_detail", ee);
                        model.addAttribute("akses_menu",akses_menu);
                        if (!akses_menu) {
                            throw new ResourceUnauthorizedException("Unauthorized");
                        }
                    }
                }));
            }
        }

    }

    @ExceptionHandler(value = ResourceUnauthorizedException.class)
    public String handleUnautorized(Model model) {
        model.addAttribute("akses", akses);
        return "403";
    }

    @ExceptionHandler(value = ResourceNotFoundExceptionForHtml.class)
    public String handleNotFound(Model model) {
        model.addAttribute("akses", akses);
        return "404";
    }




}
