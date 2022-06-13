package org.metapod.ftracker.utils;

import org.metapod.ftracker.model.domain.Parcel;
import org.metapod.ftracker.model.domain.WeightUnit;

import java.util.stream.Stream;

public class Parcels {
    public static long totalWeightInKilograms(Stream<? extends Parcel> parcels) {
        double totalWeight = parcels.mapToDouble(Parcels::weightInKilograms).sum();
        return Math.round(totalWeight);
    }

    public static double weightInKilograms(Parcel parcel) {
        return parcel.getWeightUnit() == WeightUnit.kg ?
                parcel.getWeight() :
                Mass.poundsToKilograms(parcel.getWeight());
    }

    public static int totalPieces(Stream<? extends Parcel> parcels) {
        return parcels.mapToInt(Parcel::getPieces).sum();
    }
}
