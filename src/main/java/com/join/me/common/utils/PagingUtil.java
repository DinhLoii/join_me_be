package com.join.me.common.utils;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PagingUtil {
    public static Sort createSort(PagingRequest request) {
        if(request == null || request.getSortRequest().getField() == null) {
            return Sort.unsorted();
        }

        SortRequest sortRequest = request.getSortRequest();
        if(!StringUtils.hasText(sortRequest.getField())) {
            return Sort.unsorted();
        }

        Sort.Direction direction = Sort.Direction.fromString(sortRequest.getDirection());
        return Sort.by(direction, sortRequest.getField());
    }
}
