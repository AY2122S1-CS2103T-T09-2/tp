package seedu.address.logic.commands.modulelesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modulelesson.ModuleLesson;

/**
 * Adds a module lesson to contHACKS.
 */
public class AddModuleLessonCommand extends Command {

    public static final String MESSAGE_USAGE = "addc: Adds a lesson to contHACKS. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE LESSON_CODE "
            + PREFIX_LESSON_DAY + "LESSON_DAY "
            + PREFIX_LESSON_TIME + "LESSON_START_TIME LESSON_END_TIME "
            + "[" + PREFIX_REMARK + "REMARK]\n "
            + "Example: addc "
            + PREFIX_MODULE_CODE + "CS2030S T12 "
            + PREFIX_LESSON_DAY + "2 "
            + PREFIX_LESSON_TIME + "10:00 11:00 "
            + PREFIX_REMARK + "Midterms on 1 Oct\n";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in contHACKS";

    private final ModuleLesson toAdd;

    /**
     * Creates an AddModuleLessonCommand to add the specified {@code ModuleLesson}.
     */
    public AddModuleLessonCommand(ModuleLesson moduleLesson) {
        requireNonNull(moduleLesson);
        toAdd = moduleLesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModuleLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleLessonCommand) other).toAdd));
    }
}
