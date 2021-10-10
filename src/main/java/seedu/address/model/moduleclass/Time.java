package seedu.address.model.moduleclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Days should only contain numbers";
    public static final String VALIDATION_REGEX = "\\{Alum}+";
    private final LocalTime value;

    public Time(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidTime(dateTime), MESSAGE_CONSTRAINTS);
        this.value = LocalTime.parse(dateTime);
    }


    /**
     * Returns true if a string is valid DateTime.
     *
     * @param test the given String.
     * @return True if the string can be parsed and is a valid DateTime, false otherwise.
     * @throw DateTimeParseException if the test String cannot be parsed.
     */
    public static boolean isValidTime(String test) throws DateTimeParseException {
        try {
            LocalTime.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
