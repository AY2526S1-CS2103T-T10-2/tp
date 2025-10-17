package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkTodoCommand object
 */
public class UnmarkTodoCommandParser implements Parser<UnmarkTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkTodoCommand
     * and returns an UnmarkTodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkTodoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmarkTodoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTodoCommand.MESSAGE_USAGE), pe);
        }
    }
}
