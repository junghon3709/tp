package seedu.address.model;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Collections;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.student.AddStudentCommand;
import seedu.address.model.person.InvolvementContainsKeywordsPredicate;


public class TypicalModels {

    public static final InvolvementContainsKeywordsPredicate FIRST_PREDICATE =
            new InvolvementContainsKeywordsPredicate(Collections.singletonList("first"));

    public static final ModelManager ADD_ALICE =
            new ModelManagerBuilder().with(new AddStudentCommand(ALICE)).build();

    public static final ModelManager ADD_MULTIPLE_PEOPLE =
            new ModelManagerBuilder().with(new AddStudentCommand(ALICE)).with(new AddStudentCommand(BOB)).build();

    public static final ModelManager DELETING_ALICE =
            new ModelManagerBuilder()
                    .with(new AddStudentCommand(ALICE))
                    .with(new AddStudentCommand(BOB))
                    .with(new DeleteCommand(INDEX_FIRST_STUDENT))
                    .build();

    public static final ModelManager FILTER_BY_TAG =
            new ModelManagerBuilder()
                    .with(new AddStudentCommand(ALICE))
                    .with(new AddStudentCommand(BOB))
                    .with(new DeleteCommand(INDEX_FIRST_STUDENT))
                    .with(new FilterCommand(FIRST_PREDICATE))
                    .build();


}
