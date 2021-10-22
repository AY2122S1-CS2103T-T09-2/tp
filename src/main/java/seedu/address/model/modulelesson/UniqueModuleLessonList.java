package seedu.address.model.modulelesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.modulelesson.exceptions.DuplicateModuleLessonException;
import seedu.address.model.modulelesson.exceptions.ModuleLessonNotFoundException;

public class UniqueModuleLessonList implements Iterable<ModuleLesson> {

    private final ObservableList<ModuleLesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<ModuleLesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent module lesson as the given argument.
     */
    public boolean contains(ModuleLesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModuleLesson);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     */
    public void add(ModuleLesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleLessonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent lesson from the list.
     * The lesson must exist in the list.
     */
    public void remove(ModuleLesson toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleLessonNotFoundException();
        }
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the list.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the list.
     */
    public void setModuleLesson(ModuleLesson target, ModuleLesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleLessonNotFoundException();
        }

        if (!target.isSameModuleLesson(editedLesson) && contains(editedLesson)) {
            throw new DuplicateModuleLessonException();
        }

        internalList.set(index, editedLesson);
    }

    public void setModuleLessons(UniqueModuleLessonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code moduleLessons}.
     * {@code moduleLessons} must not contain duplicate module lessons.
     */
    public void setModuleLessons(List<ModuleLesson> moduleLessons) {
        requireAllNonNull(moduleLessons);
        if (!moduleLessonsAreUnique(moduleLessons)) {
            throw new DuplicateModuleLessonException();
        }

        internalList.setAll(moduleLessons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ModuleLesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ModuleLesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleLessonList // instanceof handles nulls
                && internalList.equals(((UniqueModuleLessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code moduleLessons} contains only unique lessons.
     */
    private boolean moduleLessonsAreUnique(List<ModuleLesson> moduleLessons) {
        for (int i = 0; i < moduleLessons.size() - 1; i++) {
            for (int j = i + 1; j < moduleLessons.size(); j++) {
                if (moduleLessons.get(i).isSameModuleLesson(moduleLessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}

