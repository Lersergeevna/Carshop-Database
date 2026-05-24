package carshop.entity;

import jakarta.persistence.*;

/**
 * Базовый суперкласс для сущностей с идентификатором.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "global_sequence_generator"
    )
    @SequenceGenerator(
            name = "global_sequence_generator",
            sequenceName = "global_sequence",
            allocationSize = 1
    )
    private Long id;

    public Long getId() {
        return id;
    }
}
