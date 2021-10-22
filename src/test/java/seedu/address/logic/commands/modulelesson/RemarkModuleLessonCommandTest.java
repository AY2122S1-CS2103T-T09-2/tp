package seedu.address.logic.commands.modulelesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_REMARK_COM1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_REMARK_COM2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalModuleLessons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.person.ClearPersonCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.modulelesson.ModuleLesson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.ModuleLessonBuilder;

public class RemarkModuleLessonCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        ModuleLesson firstModuleLesson = model.getFilteredModuleLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        ModuleLesson editedModuleLesson = new ModuleLessonBuilder(firstModuleLesson).withRemark(REMARK_STUB).build();

        RemarkModuleLessonCommand remarkModuleLessonCommand =
                new RemarkModuleLessonCommand(INDEX_FIRST_LESSON, new Remark(editedModuleLesson.getRemark().value));

        String expectedMessage = String.format(RemarkModuleLessonCommand.MESSAGE_ADD_REMARK_SUCCESS,
                editedModuleLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setModuleLesson(firstModuleLesson, editedModuleLesson);

        assertCommandSuccess(remarkModuleLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        ModuleLesson firstLesson = model.getFilteredModuleLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        ModuleLesson editedLesson = new ModuleLessonBuilder(firstLesson).withRemark("").build();

        RemarkModuleLessonCommand remarkModuleLessonCommand = new RemarkModuleLessonCommand(INDEX_FIRST_LESSON,
                new Remark(editedLesson.getRemark().toString()));

        String expectedMessage = String.format(RemarkModuleLessonCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedLesson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setModuleLesson(firstLesson, editedLesson);

        assertCommandSuccess(remarkModuleLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final RemarkModuleLessonCommand standardCommand = new RemarkModuleLessonCommand(INDEX_FIRST_LESSON,
                new Remark(VALID_LESSON_REMARK_COM1));

        // same values -> returns true
        RemarkModuleLessonCommand commandWithSameValues = new RemarkModuleLessonCommand(INDEX_FIRST_LESSON,
                new Remark(VALID_LESSON_REMARK_COM1));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearPersonCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkModuleLessonCommand(INDEX_SECOND_LESSON,
                new Remark(VALID_LESSON_REMARK_COM1))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkModuleLessonCommand(INDEX_FIRST_LESSON,
                new Remark(VALID_LESSON_REMARK_COM2))));
    }

}

