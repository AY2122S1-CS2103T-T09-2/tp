package seedu.address.model.modulelesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.Remark;

/**
 * Represents a ModuleLesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleLesson {

    // Identity fields
    private final ModuleCode moduleCode;
    private final LessonDay lessonDay;
    private final LessonTime lessonTime;
    private final Remark remark;


    /**
     * Every field must be present and not null.
     */
    public ModuleLesson(ModuleCode moduleCode, LessonDay lessonDay, LessonTime lessonTime, Remark remark) {
        requireAllNonNull(moduleCode, lessonDay, lessonTime, remark);
        // ModuleCode in ModuleLesson should contain exactly 1 lesson code
        assert moduleCode.getLessonCodes().size() == 1;
        this.moduleCode = moduleCode;
        this.lessonDay = lessonDay;
        this.lessonTime = lessonTime;
        this.remark = remark;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public LessonDay getDay() {
        return lessonDay;
    }

    public LessonTime getTime() {
        return lessonTime;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both lessons have the same module code, day and lessonTime.
     * This defines a weaker notion of equality between two lessons.
     */
    public boolean isSameModuleLesson(ModuleLesson otherModuleLesson) {
        if (otherModuleLesson == this) {
            return true;
        }

        return otherModuleLesson != null
                && otherModuleLesson.getModuleCode().equals(getModuleCode())
                && otherModuleLesson.getDay().equals(getDay())
                && otherModuleLesson.getTime().equals(getTime());
    }

    /**
     * Returns true if both lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two moduleLessons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleLesson)) {
            return false;
        }

        ModuleLesson otherModuleLesson = (ModuleLesson) other;
        return ((ModuleLesson) other).getModuleCode().equals(getModuleCode())
                && otherModuleLesson.getDay().equals(getDay())
                && otherModuleLesson.getTime().equals(getTime())
                && otherModuleLesson.getRemark().equals(getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, lessonDay, lessonTime, remark);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Module: ")
                .append(getModuleCode().toString())
                .append("; Day: ")
                .append(getDay().toString())
                .append("; Time: ")
                .append(getTime().toString());
        if (!remark.toString().trim().isEmpty()) {
            builder.append("; Remark: ");
            builder.append(getRemark());
        }
        return builder.toString();
    }

}