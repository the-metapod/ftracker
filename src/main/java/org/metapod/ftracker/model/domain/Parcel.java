package org.metapod.ftracker.model.domain;

public interface Parcel {
    int getWeight();

    WeightUnit getWeightUnit();

    int getPieces();
}
