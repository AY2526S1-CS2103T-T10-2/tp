package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkTodoCommand object
 */
public class MarkTodoCommandParser implements Parser<MarkTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTodoCommand
     * and returns a MarkTodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkTodoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkTodoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE), pe);
        }
    }
}
