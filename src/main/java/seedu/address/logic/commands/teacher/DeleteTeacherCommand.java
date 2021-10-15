package seedu.address.logic.commands.teacher;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.teacher.Teacher;

public class DeleteTeacherCommand extends Command {

    public static final String COMMAND_WORD = "deleteTeacher";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public DeleteTeacherCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Teacher> lastShownList = model.getFilteredTeacherList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Teacher teacherToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTeacher(teacherToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, teacherToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTeacherCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTeacherCommand) other).targetIndex)); // state check
    }
}
