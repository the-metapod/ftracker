package org.metapod.ftracker.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AirportTrafficSummaryRequest {
    @Size(min = 3, max = 3)
    @Pattern(regexp = "[A-Z]+", message = "Airport code should contain only uppercase letters")
    private String iataAirportCode;

    private OffsetDateTime date;
}
