package com.adn.inventory.util.paging;

import org.springframework.data.domain.Sort;

public class SortDirectionUtil {
    public static Sort.Direction getSortBy(String sortBy){
        if (sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        }else{
            return Sort.Direction.DESC;
        }
    }
}
