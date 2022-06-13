package org.metapod.ftracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoDto {
    private int flightId;

    private List<BaggageDto> baggage;

    private List<CargoUnitDto> cargo;
}
