package seedu.address.logic.parser.modulelesson;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.modulelesson.RemarkModuleLessonCommand;
import seedu.address.model.person.Remark;

public class RemarkModuleLessonCommandParserTest {

    private RemarkModuleLessonCommandParser parser = new RemarkModuleLessonCommandParser();
    private final String nonEmptyRemark = "Some remark";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark;
        RemarkModuleLessonCommand expectedCommand = new RemarkModuleLessonCommand(INDEX_FIRST_LESSON,
                new Remark(nonEmptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new RemarkModuleLessonCommand(INDEX_FIRST_LESSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkModuleLessonCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "remark", expectedMessage);

        // no index
        assertParseFailure(parser, "remark" + " " + nonEmptyRemark, expectedMessage);
    }
}
