package org.metapod.ftracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.metapod.ftracker.model.domain.WeightUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoUnitDto {
    private int id;

    @Range(min = 1, max = 9999)
    private int weight;

    private WeightUnit weightUnit;

    @Range(min = 1, max = 9999)
    private int pieces;
}
