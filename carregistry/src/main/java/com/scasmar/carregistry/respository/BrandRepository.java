package com.scasmar.carregistry.respository;

import com.scasmar.carregistry.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
    List<BrandEntity> findByCountry(String country);
}
