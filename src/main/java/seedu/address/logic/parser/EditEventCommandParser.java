package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAlias;

/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_EVENT_NAME,
                        PREFIX_EVENT_ALIAS,
                        PREFIX_START,
                        PREFIX_END,
                        PREFIX_DESC
                );

        if (!argMultimap.getValue(PREFIX_EVENT_ALIAS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        EventAlias eventAlias = ParserUtil.parseEventAlias(argMultimap.getValue(PREFIX_EVENT_ALIAS).get());

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_EVENT_NAME,
                PREFIX_EVENT_ALIAS,
                PREFIX_START,
                PREFIX_END,
                PREFIX_DESC
        );

        EditEventCommand.EditEventDescriptor editEventDescriptor = new EditEventCommand.EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            editEventDescriptor.setEventName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_START).isPresent()) {
            editEventDescriptor.setStart(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START).get()));
        }
        if (argMultimap.getValue(PREFIX_END).isPresent()) {
            editEventDescriptor.setEnd(ParserUtil.parseDate(argMultimap.getValue(PREFIX_END).get()));
        }
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editEventDescriptor.setDescription(argMultimap.getValue(PREFIX_DESC).get());
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(eventAlias, editEventDescriptor);
    }
}
