package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Horse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseRepository extends JpaRepository<Horse, Integer>, HorseRepositoryBase {
    boolean existsByName(String name);
    Horse getByName(String name);
}
