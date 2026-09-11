package io.github.rosemoe.sora.text

interface ContentListener {
    fun beforeReplace(content: Content) {}
    fun afterInsert(content: Content, startLine: Int, startColumn: Int, endLine: Int, endColumn: Int, insertedText: CharSequence) {}
    fun afterDelete(content: Content, startLine: Int, startColumn: Int, endLine: Int, endColumn: CharSequence) {}
}

class Content {
    fun addContentListener(listener: ContentListener) {}
    fun removeContentListener(listener: ContentListener) {}
    fun substring(start: Int, end: Int): String = ""
    fun replace(start: Int, end: Int, replacement: CharSequence) {}
    fun replace(startLine: Int, startColumn: Int, endLine: Int, endColumn: Int, replacement: CharSequence) {}
}
