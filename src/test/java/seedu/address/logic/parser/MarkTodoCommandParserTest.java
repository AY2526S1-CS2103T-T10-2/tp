package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkTodoCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MarkTodoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MarkTodoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MarkTodoCommandParserTest {

    private MarkTodoCommandParser parser = new MarkTodoCommandParser();

    @Test
    public void parse_validArgs_returnsMarkTodoCommand() {
        assertParseSuccess(parser, "1", new MarkTodoCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArgs_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntegerArgs_throwsParseException() {
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_mixedValidInvalidArgs_throwsParseException() {
        assertParseFailure(parser,
                "1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTodoCommand.MESSAGE_USAGE));
    }
}

