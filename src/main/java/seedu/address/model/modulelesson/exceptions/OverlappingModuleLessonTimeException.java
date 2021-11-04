package seedu.address.model.modulelesson.exceptions;

public class OverlappingModuleLessonTimeException extends RuntimeException {
    /**
     * Signals that the operation cannot be performed due to clashing timings of the lessons.
     */
    public OverlappingModuleLessonTimeException() {
        super("The timings of this class will clash with another existing class!");
    }
}
