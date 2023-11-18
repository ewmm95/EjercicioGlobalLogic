package com.autenticadorgl.repository;

import com.autenticadorgl.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<PhoneEntity,Long> {

    List<PhoneEntity> findByUserId(Long userId);

}
