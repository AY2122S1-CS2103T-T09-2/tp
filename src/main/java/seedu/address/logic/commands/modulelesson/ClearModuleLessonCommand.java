package seedu.address.logic.commands.modulelesson;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ClearModuleLessonCommand extends Command {

    public static final String MESSAGE_SUCCESS = "The lessons in contHACKS has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearLessons();
        assert model.getAddressBook().getModuleLessonList().isEmpty();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
