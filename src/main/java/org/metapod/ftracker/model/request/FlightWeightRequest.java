package org.metapod.ftracker.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FlightWeightRequest {
    @Range(min = 1000, max = 9999)
    int flightNumber;

    OffsetDateTime date;
}
