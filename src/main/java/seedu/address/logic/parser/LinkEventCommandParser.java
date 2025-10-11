package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAlias;

/**
 * Parses input arguments and creates a new LinkEventCommand object
 */
public class LinkEventCommandParser implements Parser<LinkEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkEventCommand
     * and returns an LinkEventCommand object for execution
     * @throws ParseException if the user input does not confirm the expected format
     */
    @Override
    public LinkEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ALIAS);

        // Ensure alias is present and a person index frm the preamble is provided
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ALIAS)
            || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkEventCommand.MESSAGE_USAGE));
        }


        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        EventAlias eventAlias = ParserUtil.parseEventAlias(argMultimap.getValue(PREFIX_EVENT_ALIAS).get());

        return new LinkEventCommand(index, eventAlias);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
