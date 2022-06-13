package org.metapod.ftracker.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class CargoElementId implements Serializable {
    private Cargo cargo;
    private int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoElementId that = (CargoElementId) o;
        return id == that.id && cargo.equals(that.cargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargo, id);
    }
}
