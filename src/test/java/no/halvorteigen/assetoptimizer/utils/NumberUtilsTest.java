package no.halvorteigen.assetoptimizer.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberUtilsTest {

    @Test
    void should_add_leading_zeros() {
        // Arrange
        int number = 123;
        int length = 5;

        // Act
        String result = NumberUtils.fillLeadingZeros(number, length);

        // Assert
        assertThat(result).isEqualTo("00123");
    }

    @Test
    void should_preserve_number_when_length_of_number_is_correct() {
        // Arrange
        int number = 12345;
        int length = 5;

        // Act
        String result = NumberUtils.fillLeadingZeros(number, length);

        // Assert
        assertThat(result).isEqualTo("12345");
    }

    @Test
    void should_throw_exception_when_number_is_longer_than_length() {
        // Arrange
        int number = 123456;
        int length = 5;

        // Act
        assertThatThrownBy(() -> NumberUtils.fillLeadingZeros(number, length))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Number is longer than length");
    }
}