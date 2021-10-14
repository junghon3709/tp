package seedu.address.model;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.student.AddStudentCommand;
import seedu.address.model.person.InvolvementContainsKeywordsPredicate;

import java.util.Collections;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

public class TypicalModels {

    public static InvolvementContainsKeywordsPredicate FIRST_PREDICATE =
            new InvolvementContainsKeywordsPredicate(Collections.singletonList("first"));

    public static ModelManager ADD_ALICE =
            new ModelManagerBuilder().with(new AddStudentCommand(ALICE)).build();

    public static ModelManager ADD_MULTIPLE_PEOPLE  =
            new ModelManagerBuilder().with(new AddStudentCommand(ALICE)).with(new AddStudentCommand(BOB)).build();

    public static ModelManager DELETING_ALICE =
            new ModelManagerBuilder()
                    .with(new AddStudentCommand(ALICE))
                    .with(new AddStudentCommand(BOB))
                    .with(new DeleteCommand(INDEX_FIRST_STUDENT))
                    .build();

    public static ModelManager FITLER_BY_TAG =
            new ModelManagerBuilder()
                    .with(new AddStudentCommand(ALICE))
                    .with(new AddStudentCommand(BOB))
                    .with(new DeleteCommand(INDEX_FIRST_STUDENT))
                    .with(new FilterCommand(FIRST_PREDICATE))
                    .build();


}
