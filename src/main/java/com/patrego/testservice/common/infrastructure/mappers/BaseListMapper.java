package com.patrego.testservice.common.infrastructure.mappers;

import java.util.List;

public interface BaseListMapper<Dt,Dm, E> {
    List<E> domainToEntity(List<Dm> domain);
    List<Dm> entityToDomain(List<E> entity);
    List<Dm> dtoToDomain(List<Dt> dto);
    List<Dt> domainToDto(List<Dm> domain);
}
