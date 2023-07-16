package com.adn.inventory.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        List<String> menus = new ArrayList<>();
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((k, v) -> {
                           Boolean a = k.getMethodsCondition().toString().equals("[GET]");
                            if (a) {
//                                System.out.println(k.getPatternValues().toArray()[0]);
//                                System.out.println("zz");
                                String zz = printSubsInDelimiters(k.getPatternValues().toArray()[0].toString());
                                if (zz.length() > 0){
                                    menus.add(zz);
//

                                }
                            }
                        }

                );

        List<String> newlist = menus.stream().distinct().collect(Collectors.toList());
        for (String str : newlist){
            //System.out.println(str);
        }
    }

    public static String printSubsInDelimiters(String str)
    {

        // Regex to extract the string
        // between two delimiters
        String regex = "\\/(.*?)\\/";

        // Compile the Regex.
        Pattern p = Pattern.compile(regex);

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);

        // Get the subsequence
        // using find() method
        StringBuilder res = new StringBuilder();
        while (m.find())
        {
            res.append(m.group(1));
            //System.out.println(m.group(1));
        }
        return res.toString();
    }
}
