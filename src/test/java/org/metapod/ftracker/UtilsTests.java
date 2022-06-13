package org.metapod.ftracker;

import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.metapod.ftracker.model.domain.Parcel;
import org.metapod.ftracker.model.domain.WeightUnit;
import org.metapod.ftracker.utils.Mass;
import org.metapod.ftracker.utils.Parcels;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTests {

    private static Stream<Arguments> kgToLbTestArgumentProvider() {
        return Stream.of(
                Arguments.of(0, 0d),
                Arguments.of(1, 2.20462d),
                Arguments.of(10_000, 22046.224d));
    }

    @ParameterizedTest
    @MethodSource("kgToLbTestArgumentProvider")
    public void shouldConvertKilogramsToPounds(int kg, double expectedLb) {
        assertThat(Mass.kilogramsToPounds(kg)).isCloseTo(expectedLb, Offset.strictOffset(1e-3));
    }

    private static Stream<Arguments> lbToKgTestArgumentProvider() {
        return Stream.of(
                Arguments.of(0, 0d),
                Arguments.of(1, 0.4535d),
                Arguments.of(10_000, 4535.924d));
    }

    @ParameterizedTest
    @MethodSource("lbToKgTestArgumentProvider")
    public void shouldConvertPoundsToKilograms(int lb, double expectedKg) {
        assertThat(Mass.poundsToKilograms(lb)).isCloseTo(expectedKg, Offset.strictOffset(1e-3));
    }


    private static List<Parcel> EMPTY = Collections.emptyList();
    private static List<Parcel> TEN_SMALL_KG = Collections.nCopies(10, createParcelStub(1, WeightUnit.kg));
    private static List<Parcel> TEN_SMALL_LB = Collections.nCopies(10, createParcelStub(1, WeightUnit.lb));
    private static List<Parcel> BIG_KG_AND_LB = Arrays.asList(
            createParcelStub(10_000, WeightUnit.kg),
            createParcelStub(10_000, WeightUnit.lb));

    private static Stream<Arguments> sumTestArgumentsProvider() {
        return Stream.of(
                Arguments.of(EMPTY, 0),
                Arguments.of(TEN_SMALL_KG, 10),
                Arguments.of(TEN_SMALL_LB, 5),
                Arguments.of(BIG_KG_AND_LB, 14536));
    }

    private static Parcel createParcelStub(int weight, WeightUnit weightUnit) {
        Parcel parcelStub = Mockito.mock(Parcel.class);
        Mockito.when(parcelStub.getWeight()).thenReturn(weight);
        Mockito.when(parcelStub.getWeightUnit()).thenReturn(weightUnit);
        return parcelStub;
    }

    @ParameterizedTest
    @MethodSource("sumTestArgumentsProvider")
    public void shouldCalculateTotalSumInKg(List<Parcel> parcels, int expectedSum) {
        assertThat(Parcels.totalWeightInKilograms(parcels.stream())).isEqualTo(expectedSum);
    }
}
