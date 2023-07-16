package com.adn.inventory.util;


import com.adn.inventory.util.paging.Order;
import com.adn.inventory.util.paging.PagingRequest;
import com.adn.inventory.util.paging.SortDirectionUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageDataTable {



    public Pageable pagination(PagingRequest pagingRequest){
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;


        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (sortBy != null) {
            Sort sort = Sort.by(new Sort.Order(SortDirectionUtil.getSortBy(direction), sortBy));
            pageable = PageRequest.of(pageNumber, pageSize, sort);
        }
        return pageable;
    }
}
