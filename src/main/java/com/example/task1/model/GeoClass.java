package com.example.task1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GeoClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String parentSection;

    // Constructors, getters, setters
    public GeoClass() {
    }

    public GeoClass(String name, String code, String parentSection) {
        this.name = name;
        this.code = code;
        this.parentSection = parentSection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentSection() {
        return parentSection;
    }

    public void setParentSection(String parentSection) {
        this.parentSection = parentSection;
    }
}
