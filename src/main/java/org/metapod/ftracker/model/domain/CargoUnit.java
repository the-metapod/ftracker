package org.metapod.ftracker.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo_units")
@IdClass(CargoElementId.class)
public class CargoUnit implements Parcel {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Id
    private int id;

    @Range(min = 1, max = 9999)
    private int weight;

    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    @Range(min = 1, max = 9999)
    private int pieces;
}
