package org.metapod.ftracker.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cargos")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<Baggage> baggage = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<CargoUnit> cargo = new ArrayList<>();
}
