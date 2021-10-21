package seedu.address.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtMultipleIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        //Deletes 1 person
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedMessage = String.format(DeletePersonCommand.MESSAGE_NUMBER_DELETED_PERSON, 1) + expectedMessage;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deletePersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validRangeUnfilteredList_success() {
        //Deletes 2 persons
        Person personToDelete1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDelete2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeletePersonCommand deletePersonCommand1 = new DeletePersonCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage1 = String.format(DeletePersonCommand.MESSAGE_NUMBER_DELETED_PERSON, 2)
                + String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete1)
                + String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete2);

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel1.deletePerson(personToDelete1);
        expectedModel1.deletePerson(personToDelete2);

        assertCommandSuccess(deletePersonCommand1, model, expectedMessage1, expectedModel1);
    }

    /*@Test
    public void execute_validModuleCodeUnfilteredList_Success() {
        ModuleCodesContainsKeywordsPredicate predicate =
                new ModuleCodesContainsKeywordsPredicate(Arrays.asList("[CS2040]"));

    }*/

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(outOfBoundIndex);

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRangeUnfilteredList_throwsCommandException() {
        Index invalidStartIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 2);
        Index invalidEndIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 4);
        DeletePersonCommand deletePersonCommand1 = new DeletePersonCommand(invalidStartIndex, invalidEndIndex);

        assertCommandFailure(deletePersonCommand1, model, Messages.MESSAGE_INVALID_RANGE);
    }

    /*@Test
    public void execute_invalidModuleCodeUnfilteredList_throwsCommandExceptio() {
    }*/

    @Test
    public void execute_validIndexFilteredList_success() {
        //Deletes 1 person in the filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
        expectedMessage = String.format(DeletePersonCommand.MESSAGE_NUMBER_DELETED_PERSON, 1) + expectedMessage;
        System.out.println(expectedMessage);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        //showNoPerson(expectedModel);

        assertCommandSuccess(deletePersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validRangeFilteredList_success() {
        //Deletes 2 person in the filtered list
        showPersonAtMultipleIndex(model, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        Person personToDelete1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDelete2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeletePersonCommand deletePersonCommand1 = new DeletePersonCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage1 = String.format(DeletePersonCommand.MESSAGE_NUMBER_DELETED_PERSON, 2)
                + String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete1)
                + String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete2);

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel1.deletePerson(personToDelete1);
        expectedModel1.deletePerson(personToDelete2);
        //showNoPerson(expectedModel1);

        assertCommandSuccess(deletePersonCommand1, model, expectedMessage1, expectedModel1);
    }

    /*@Test
    public void execute_validModuleCodeFilteredList_success() {
    }*/

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(outOfBoundIndex);

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRangeFilteredList_throwsCommandException() {
        showPersonAtMultipleIndex(model, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        Index invalidStartIndex = INDEX_SECOND_PERSON;
        Index invalidEndIndex = INDEX_THIRD_PERSON;
        //ensures that the invalidStartIndex and invalidEndIndex are still in bound of the address book list
        assertTrue(invalidStartIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        assertTrue(invalidEndIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(invalidStartIndex, invalidEndIndex);

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_RANGE);
    }

    /*@Test
    public void execute_invalidModuleCodeFilteredList_throwsCommandException() {
    }*/

    @Test
    public void equals() {
        DeletePersonCommand deleteFirstCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);
        DeletePersonCommand deleteSecondCommand = new DeletePersonCommand(INDEX_SECOND_PERSON);
        DeletePersonCommand deleteBatchCommand1 = new DeletePersonCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        DeletePersonCommand deleteBatchCommand2 = new DeletePersonCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteBatchCommand1.equals(deleteBatchCommand1));

        // same values -> returns true
        DeletePersonCommand deleteFirstCommandCopy = new DeletePersonCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        DeletePersonCommand deleteBatchCommandCopy = new DeletePersonCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(deleteBatchCommand1.equals(deleteBatchCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        assertFalse(deleteBatchCommand1.equals(deleteBatchCommand2));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
