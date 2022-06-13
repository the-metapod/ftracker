package org.metapod.ftracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {
    private int flightId;

    @Range(min = 1000, max = 9999)
    private int flightNumber;

    @Size(min = 3, max = 3)
    @Pattern(regexp = "[A-Z]+", message = "Airport code should contain only uppercase letters")
    private String departureAirportIATACode;

    @Size(min = 3, max = 3)
    @Pattern(regexp = "[A-Z]+", message = "Airport code should contain only uppercase letters")
    private String arrivalAirportIATACode;

    private OffsetDateTime departureDate;
}
