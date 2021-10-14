package seedu.address.logic.commands.teacher;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Teacher;
import seedu.address.testutil.TeacherBuilder;

public class AddTeacherCommandTest {

    @Test
    public void constructor_nullTeacher_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTeacherCommand(null));
    }

    @Test
    public void execute_teacherAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Teacher validTeacher = new TeacherBuilder().build();

        CommandResult commandResult = new AddTeacherCommand(validTeacher).execute(modelStub);

        assertEquals(String.format(AddTeacherCommand.MESSAGE_SUCCESS, validTeacher), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTeacher), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicateTeacher_throwsCommandException() {
        Teacher validTeacher = new TeacherBuilder().build();
        AddTeacherCommand addTeacherCommand = new AddTeacherCommand(validTeacher);
        ModelStub modelStub = new ModelStubWithPerson(validTeacher);

        assertThrows(CommandException.class,
            AddTeacherCommand.MESSAGE_DUPLICATE_TEACHER, () -> addTeacherCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Teacher cho = new TeacherBuilder().withName("Cho").build();
        Teacher dee = new TeacherBuilder().withName("Dee").build();
        AddTeacherCommand addChoCommand = new AddTeacherCommand(cho);
        AddTeacherCommand addDeeCommand = new AddTeacherCommand(dee);

        // same object -> returns true
        assertTrue(addChoCommand.equals(addChoCommand));

        // same values -> returns true
        AddTeacherCommand addChoCommandCopy = new AddTeacherCommand(cho);
        assertTrue(addChoCommand.equals(addChoCommandCopy));

        // different types -> returns false
        assertFalse(addChoCommand.equals(1));

        // null -> returns false
        assertFalse(addChoCommand.equals(null));

        // different teacher -> returns false
        assertFalse(addChoCommand.equals(addDeeCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEqualHistory(Model other) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
