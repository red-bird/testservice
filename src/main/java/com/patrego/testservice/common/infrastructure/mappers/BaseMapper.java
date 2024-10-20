package com.patrego.testservice.common.infrastructure.mappers;

import com.patrego.testservice.common.domain.valueObjects.Id;

import java.util.UUID;

public interface BaseMapper<Dt,Dm, E> {
    E domainToEntity(Dm domain);
    Dm entityToDomain(E entity);
    Dm dtoToDomain(Dt dto);
    Dt domainToDto(Dm domain);

    default Id stringToId(String id) {
        return id != null ? new Id(UUID.fromString(id)) : null;
    }

    default String idToString(Id id) {
        return id != null ? id.id().toString() : null;
    }
}
