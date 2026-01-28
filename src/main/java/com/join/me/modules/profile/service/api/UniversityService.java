package com.join.me.modules.profile.service.api;

import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.modules.profile.dto.request.UniversityCreationRequest;
import com.join.me.modules.profile.dto.request.UniversityUpdateRequest;
import com.join.me.modules.profile.dto.response.UniversityResponse;

public interface UniversityService {

    UniversityResponse create(UniversityCreationRequest request);

    PagingResponse<UniversityResponse> getUniversities(PagingRequest request);

    UniversityResponse getById(String universityId);

    UniversityResponse update(String universityId, UniversityUpdateRequest request);

    void deleteById(String universityId);
}


