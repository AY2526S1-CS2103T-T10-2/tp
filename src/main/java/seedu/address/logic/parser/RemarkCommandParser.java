package seedu.address.logic.parser;

import com.sun.jdi.connect.Connector;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

public class RemarkCommandParser implements Parser<RemarkCommand> {
    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), pe);
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_REMARK);

        String remark = argMultiMap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, remark);
    }
}
