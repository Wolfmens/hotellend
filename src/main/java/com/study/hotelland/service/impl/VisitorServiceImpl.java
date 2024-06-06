package com.study.hotelland.service.impl;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.entity.Visitor;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.repository.VisitorRepository;
import com.study.hotelland.service.VisitorService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repository;

    @Override
    public List<Visitor> findAll() {
        return repository.findAll();
    }

    @Override
    public Visitor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () ->  new NotFoundEntityException(MessageFormat.format("Visitor with id {0} not found", id)));
    }

    @Override
    public Visitor create(Visitor visitor, RoleType type) {
        if (repository.existsByNameAndEmail(visitor.getName(), visitor.getEmail())) {
            throw new ValidationException("Visitor with the specified name and email already exists!");
        }
        visitor.setAuthority(type);

        return repository.save(visitor);
    }

    @Override
    public Visitor update(Visitor visitor) {
        Visitor visitorFromDb = findById(visitor.getId());

        if (StringUtils.hasText(visitor.getName())) {
            visitorFromDb.setName(visitor.getName());
        }
        if (StringUtils.hasText(visitor.getEmail())) {
            visitorFromDb.setEmail(visitor.getEmail());
        }
        if (StringUtils.hasText(visitor.getPassword())) {
            visitorFromDb.setPassword(visitor.getPassword());
        }
        if (StringUtils.hasText(visitor.getAuthority().name())) {
            visitorFromDb.setAuthority(visitor.getAuthority());
        }

        return repository.save(visitorFromDb);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Visitor findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() ->  new NotFoundEntityException(MessageFormat.format("Visitor with name {0} not found", name)));
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
