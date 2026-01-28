package com.join.me.modules.profile.service.impl;

import com.join.me.common.constants.GlobalVariableConstant;
import com.join.me.common.dtos.request.PagingRequest;
import com.join.me.common.dtos.response.PagingResponse;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.common.utils.PagingUtil;
import com.join.me.modules.profile.dto.request.UniversityCreationRequest;
import com.join.me.modules.profile.dto.request.UniversityUpdateRequest;
import com.join.me.modules.profile.dto.response.UniversityResponse;
import com.join.me.modules.profile.entity.University;
import com.join.me.modules.profile.mapper.UniversityMapper;
import com.join.me.modules.profile.repository.UniversityRepository;
import com.join.me.modules.profile.service.api.UniversityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UniversityServiceImpl implements UniversityService {

    UniversityRepository universityRepository;
    UniversityMapper universityMapper;

    @Override
    @Transactional
    public UniversityResponse create(UniversityCreationRequest request) {
        University university = University.builder()
                .vietnameseName(request.getVietnameseName())
                .englishName(request.getEnglishName())
                .abbreviatedName(request.getAbbreviatedName())
                .build();
        return universityMapper.toUniversityDto(universityRepository.save(university));
    }

    @Override
    public PagingResponse<UniversityResponse> getUniversities(PagingRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<University> page = universityRepository.findAll(pageable);

        return PagingResponse.<UniversityResponse>builder()
                .currentPage(request.getPage())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .data(page.getContent().stream()
                        .map(universityMapper::toUniversityDto)
                        .toList())
                .build();
    }

    @Override
    public UniversityResponse getById(String universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));
        return universityMapper.toUniversityDto(university);
    }

    @Override
    @Transactional
    public UniversityResponse update(String universityId, UniversityUpdateRequest request) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));

        if (request.getVietnameseName() != null) {
            university.setVietnameseName(request.getVietnameseName());
        }
        if (request.getEnglishName() != null) {
            university.setEnglishName(request.getEnglishName());
        }
        if (request.getAbbreviatedName() != null) {
            university.setAbbreviatedName(request.getAbbreviatedName());
        }

        return universityMapper.toUniversityDto(universityRepository.save(university));
    }

    @Override
    @Transactional
    public void deleteById(String universityId) {
        universityRepository.deleteById(universityId);
    }
}


