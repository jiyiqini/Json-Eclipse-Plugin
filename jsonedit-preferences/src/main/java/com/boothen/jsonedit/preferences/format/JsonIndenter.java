package com.boothen.jsonedit.preferences.format;

import java.util.Arrays;

import org.antlr.v4.runtime.Token;

import com.boothen.jsonedit.antlr.JSONLexer;

/**
 * Indents JSON code snippets.
 */
public class JsonIndenter {

    private final int tabWidth;
    private final boolean spacesForTabs;
    private int level;

    public JsonIndenter(int tabWidth, boolean spacesForTabs) {
        this.tabWidth = tabWidth;
        this.spacesForTabs = spacesForTabs;
    }

    public void updateIndentLevel(Token token) {
        if (increasesIndent(token)) {
            increaseIndent();
        }

        if (decreasesIndent(token)) {
            decreaseIndent();
        }
    }

    public void increaseIndent() {
        level++;
    }

    public void decreaseIndent() {
        level--;
    }

    public boolean increasesIndent(Token token) {
        switch (token.getType()) {
        case JSONLexer.BEGIN_OBJECT:
        case JSONLexer.BEGIN_ARRAY:
            return true;
        default:
            return false;
        }
    }

    public boolean decreasesIndent(Token token) {
        switch (token.getType()) {
        case JSONLexer.END_OBJECT:
        case JSONLexer.END_ARRAY:
            return true;
        default:
            return false;
        }
    }

    public void indent(StringBuffer buffer) {
        if (level <= 0) {
            return;
        }

        if (spacesForTabs) {
            char[] array = createArray(' ', level * tabWidth);
            buffer.append(array);
        } else {
            char[] array = createArray('\t', level);
            buffer.append(array);
        }
    }

    private static char[] createArray(char fillChar, int length) {
        char[] array = new char[length];
        Arrays.fill(array, fillChar);
        return array;
    }
}
