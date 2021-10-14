package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.student.AddStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.TypicalModels.FIRST_PREDICATE;
import static seedu.address.testutil.TypicalPersons.ALI;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARP_NOT_IN_LIST;
import static seedu.address.testutil.TypicalPersons.FISH_NOT_IN_LIST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.model.TypicalModels.ADD_ALICE;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private UndoCommand undoCommand = new UndoCommand();


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void testEmptyHistory_failToUndo() {
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_ALREADY_AT_OLDEST_CHANGE, expectedModel);
    }

    @Test
    public void nonEmptyHistory_oneAddition_success() {
        // add in a student and undoing it should give back the same model
        model.addPerson(FISH_NOT_IN_LIST);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);
        assertTrue(model.hasEqualHistory(expectedModel));
    }

    @Test
    public void nonEmptyHistory_twoAdditions_success() {
        // add in a student and undoing it should give back the same model
        model.addPerson(FISH_NOT_IN_LIST);
        model.addPerson(CARP_NOT_IN_LIST);
        undoCommand.execute(model);
        assertNotEquals(model, expectedModel);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);
        assertTrue(model.hasEqualHistory(expectedModel));
    }

    @Test
    public void undoDelete_success() {
        // add in a student and undoing it should give back the same model
        model.addPerson(FISH_NOT_IN_LIST);
        model.deletePerson(FISH_NOT_IN_LIST);
        expectedModel.addPerson(FISH_NOT_IN_LIST);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);
        assertTrue(model.hasEqualHistory(expectedModel));
    }

    @Test
    public void undoDelete_multiple_success() {
        model.addPerson(FISH_NOT_IN_LIST);
        model.deletePerson(FISH_NOT_IN_LIST);
        model.deletePerson(ALICE);
        model.addPerson(ALICE);
        undoCommand.execute(model);
        undoCommand.execute(model);
        undoCommand.execute(model);
        // after 4 undoes, you should get back the same model
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS,
                expectedModel);
        // and they have equal histories
        assertTrue(model.hasEqualHistory(expectedModel));
    }

    @Test
    public void unequalHistory() {
        model.addPerson(FISH_NOT_IN_LIST);
        model.deletePerson(FISH_NOT_IN_LIST);
        assertFalse(model.hasEqualHistory(expectedModel));
    }

    @Test
    public void filter_doesNotAffectHistory() {
        FilterCommand filterCommand = new FilterCommand(FIRST_PREDICATE);
        filterCommand.execute(model);
        assertTrue(model.hasEqualHistory(expectedModel));
        filterCommand.execute(expectedModel);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_ALREADY_AT_OLDEST_CHANGE, expectedModel);
    }
}
