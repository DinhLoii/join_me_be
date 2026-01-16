package com.join.me.common.utils;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.request.SortRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class PagingUtil {

    public static Sort createSort(PagingRequest request) {
        if (request == null) {
            return Sort.unsorted();
        }

        SortRequest sortRequest = request.getSortRequest();
        if (sortRequest == null) {
            return Sort.unsorted();
        }

        if (!StringUtils.hasText(sortRequest.getField())) {
            return Sort.unsorted();
        }

        Sort.Direction direction = Sort.Direction.ASC; // default
        String dirValue = sortRequest.getDirection();
        if (StringUtils.hasText(dirValue)) {
            try {
                direction = Sort.Direction.fromString(dirValue);
            } catch (IllegalArgumentException ignored) {
                // keep default ASC if invalid
            }
        }

        return Sort.by(direction, sortRequest.getField());
    }
}

