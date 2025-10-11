package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.event.EventName;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_EVENT_NAME,
                        PREFIX_EVENT_ALIAS,
                        PREFIX_START,
                        PREFIX_END,
                        PREFIX_DESC
                );

        if (!arePrefixesPresent(argMultimap,
                PREFIX_EVENT_NAME,
                PREFIX_EVENT_ALIAS,
                PREFIX_START,
                PREFIX_END,
                PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_EVENT_NAME,
                PREFIX_EVENT_ALIAS,
                PREFIX_START,
                PREFIX_END,
                PREFIX_DESC
        );
        EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());
        EventAlias alias = ParserUtil.parseEventAlias(argMultimap.getValue(PREFIX_EVENT_ALIAS).get());
        LocalDateTime start = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START).get());
        LocalDateTime end = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END).get());

        Event event = new Event(name, alias, start, end, argMultimap.getValue(PREFIX_DESC).get());

        return new AddEventCommand(event);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
