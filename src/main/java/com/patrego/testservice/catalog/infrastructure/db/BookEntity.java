package com.patrego.testservice.catalog.infrastructure.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 127, message = "Title must be less than 127 characters")
    private String title;

    @Column(name = "isbn", nullable = false, unique = true)
    @NotBlank(message = "ISBN cannot be blank")
    @Size(min = 13, max = 31, message = "ISBN must be exactly 13-31 characters")
    private String isbn;

}
