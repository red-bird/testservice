package com.patrego.testservice.catalog.infrastructure.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookEntityRepository extends JpaRepository<BookEntity, UUID> {
}
