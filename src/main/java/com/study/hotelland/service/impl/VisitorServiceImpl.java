package com.study.hotelland.service.impl;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.entity.Visitor;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.repository.VisitorRepository;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.statistic.event.RegistrationVisitorEvent;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final PasswordEncoder passwordEncoder;

    @Value("${app.kafka.property.registrationVisitorTopic}")
    private String registrationVisitorTopic;

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
        visitor.setPassword(passwordEncoder.encode(visitor.getPassword()));
        Visitor createdVisitor = repository.save(visitor);

        RegistrationVisitorEvent event = new RegistrationVisitorEvent();
        event.setVisitorId(createdVisitor.getId());
        kafkaTemplate.send(registrationVisitorTopic, event);

        return createdVisitor;
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
            visitorFromDb.setPassword(passwordEncoder.encode(visitor.getPassword()));
        }
        if (visitor.getAuthority() != null && StringUtils.hasText(visitor.getAuthority().name())) {
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
