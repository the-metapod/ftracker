package org.metapod.ftracker.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.utils.Mass;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Weight {

    private final long kg;

    private final long lb;

    public static Weight fromKilograms(long kg) {
        return new Weight(kg, Mass.kilogramsToPoundsRounded(kg));
    }
}
