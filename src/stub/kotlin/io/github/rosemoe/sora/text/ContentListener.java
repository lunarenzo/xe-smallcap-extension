package io.github.rosemoe.sora.text;

public interface ContentListener {
    void beforeReplace(Content content, int startLine, int startColumn, int endLine, int endColumn, CharSequence replacement);
    void afterInsert(Content content, int startLine, int startColumn, int endLine, int endColumn, CharSequence insertedText);
    void afterDelete(Content content, int startLine, int startColumn, int endLine, int endColumn, CharSequence deletedText);
}
