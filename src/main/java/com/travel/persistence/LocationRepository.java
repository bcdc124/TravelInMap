package com.travel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
