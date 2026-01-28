package com.join.me.modules.profile.repository;

import com.join.me.modules.profile.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByProfile_Id(String profileId);
}


