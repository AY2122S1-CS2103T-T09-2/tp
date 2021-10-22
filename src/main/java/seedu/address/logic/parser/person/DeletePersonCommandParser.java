package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleCodesContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeletePersonCommandParser implements Parser<DeletePersonCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);
        List<String> moduleCodes = argMultimap.getAllValues(PREFIX_MODULE_CODE);
        if (moduleCodes.size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_DELETE_BY_MODULE_USAGE)
            );
        }
        if (moduleCodes.size() == 1) {
            try {
                return deleteByModuleCode(moduleCodes);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
            }
        }
        try {
            return deleteByIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

    private DeletePersonCommand deleteByIndex(String args) throws ParseException {
        if (args.contains("-")) {
            Index start = ParserUtil.parseIndex(args.substring(1, args.indexOf("-")));
            Index end = ParserUtil.parseIndex(args.substring(args.indexOf("-") + 1));
            return new DeletePersonCommand(start, end);
        } else {
            return new DeletePersonCommand(ParserUtil.parseIndex(args));
        }
    }

    private DeletePersonCommand deleteByModuleCode(List<String> moduleCodes) throws ParseException {
        List<String> stringListOfModuleCodes = ParserUtil.parseModuleCodes(moduleCodes).stream()
                .map(moduleCode -> moduleCode.toString())
                .collect(Collectors.toList());;
        ModuleCode moduleCode = ParserUtil.parseModuleCode(moduleCodes.get(0));
        if (moduleCode.getLessonCodes().size() > 1) {
            throw new ParseException("error message");
        }

        return new DeletePersonCommand(
                new ModuleCodesContainsKeywordsPredicate(stringListOfModuleCodes), moduleCode);
    }
}
