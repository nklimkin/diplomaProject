package model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity {

    public static final int START_SEQ = 100_000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    public BaseEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {return this.id == null;}
}
