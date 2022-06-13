package org.metapod.ftracker.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    private int id;

    @Range(min = 1000, max = 9999)
    private int flightNumber;

    @Size(min = 3, max = 3)
    private String departureAirportIATACode;

    @Size(min = 3, max = 3)
    private String arrivalAirportIATACode;

    private OffsetDateTime departureDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flight")
    private List<Cargo> cargos = new ArrayList<>();
}
