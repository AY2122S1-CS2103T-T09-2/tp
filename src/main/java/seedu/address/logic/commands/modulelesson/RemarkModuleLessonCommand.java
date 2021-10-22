package seedu.address.logic.commands.modulelesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modulelesson.ModuleLesson;
import seedu.address.model.person.Remark;

public class RemarkModuleLessonCommand extends Command {
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Lesson: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Lesson: %1$s";
    public static final String MESSAGE_USAGE = "remark"
            + ": Edits the remark of the class identified "
            + "by the index number used in the last class listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: remark 1 "
            + "r/ COM1-113";

    private final Index index;
    private Remark remark;

    /**
     * @param index index of the person in the filtered lesson list to edit the remark
     * @param remark remark of the class to be updated to
     */
    public RemarkModuleLessonCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<ModuleLesson> lastShownList = model.getFilteredModuleLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ModuleLesson lessonToEdit = lastShownList.get(index.getZeroBased());
        ModuleLesson editedLesson = new ModuleLesson(lessonToEdit.getModuleCodes(), lessonToEdit.getDay(),
                lessonToEdit.getTime(), remark);

        model.setModuleLesson(lessonToEdit, editedLesson);
        model.updateFilteredModuleLessonList(PREDICATE_SHOW_ALL_LESSONS);

        return new CommandResult(generateSuccessMessage(editedLesson));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code lessonToEdit}.
     */
    private String generateSuccessMessage(ModuleLesson lessonToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, lessonToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkModuleLessonCommand)) {
            return false;
        }

        // state check
        RemarkModuleLessonCommand e = (RemarkModuleLessonCommand) other;
        return index.equals(e.index) && remark.equals(e.remark);
    }
}
