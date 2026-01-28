package com.join.me.modules.profile.mapper;

import com.join.me.modules.profile.dto.response.AddressResponse;
import com.join.me.modules.profile.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toAddressDto(Address address);
}


