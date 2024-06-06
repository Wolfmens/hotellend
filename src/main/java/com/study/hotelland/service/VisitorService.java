package com.study.hotelland.service;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.entity.Visitor;

import java.util.List;

public interface VisitorService {

    List<Visitor> findAll();

    Visitor findById(Long id);

    Visitor create(Visitor visitor, RoleType type);

    Visitor update(Visitor visitor);

    void delete(Long id);

    Visitor findByName(String name);

    Boolean existsByName(String name);
}
