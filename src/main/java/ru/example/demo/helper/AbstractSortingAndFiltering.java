package ru.example.demo.helper;

import ru.example.demo.helper.paging.Column;
import ru.example.demo.helper.paging.Order;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;
import ru.example.demo.helper.paging.Direction;

public abstract class AbstractSortingAndFiltering<T> {
    protected Page<T> getPage(List<T> ts, PagingRequest pagingRequest) {
        List<T> filtered = ts.stream()
                                           .sorted(sortTs(pagingRequest))
                                           .filter(filterTs(pagingRequest))
                                           .skip(pagingRequest.getStart())
                                           .limit(pagingRequest.getLength())
                                           .collect(Collectors.toList());

        long count = ts.stream()
                             .filter(filterTs(pagingRequest))
                             .count();

        Page<T> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());
        
        return page;
    }

    protected Predicate<T> filterTs(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || ObjectUtils.isEmpty(pagingRequest.getSearch()
                                                                                  .getValue())) {
            return employee -> true;
        }
        
     
        String value = pagingRequest.getSearch()
                                    .getValue().toLowerCase();
        
        Predicate<T> tPredicate = formTPredicate(value);
        
        return tPredicate;
    }

    protected Comparator<T> sortTs(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return getTEmptyComparator();
        }

        try {
            Order order = pagingRequest.getOrder()
                                       .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                                         .get(columnIndex);
            

            Comparator<T> comparator = getTComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return getTEmptyComparator();
            }

            return comparator;

        } catch (Exception e) {
          
        }

        return getTEmptyComparator();
    }
    
    protected abstract Predicate<T> formTPredicate(String value);
    
    protected abstract Comparator<T> getTComparator(String name, Direction dir);
    
    protected abstract Comparator<T> getTEmptyComparator();
    
    
}
