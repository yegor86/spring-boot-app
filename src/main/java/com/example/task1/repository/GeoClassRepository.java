package com.example.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.example.task1.model.GeoClass;

public interface GeoClassRepository extends JpaRepository<GeoClass, Long> {
    
    @Query("SELECT j FROM GeoClass j, JobGeoClass k WHERE j.id = k.id.geoClassId AND k.id.jobId = :jobId")
    List<GeoClass> findGeoClassesByJobId(@Param("jobId") Long jobId);

    @Query("SELECT g FROM GeoClass g WHERE "
         + "(:name IS NULL OR g.name = :name) AND "
         + "(:code IS NULL OR g.code = :code)")
    List<GeoClass> findByNameAndCode(@Param("name") String name, @Param("code") String code);
}
