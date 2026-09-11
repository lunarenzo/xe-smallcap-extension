package io.github.rosemoe.sora.text

class Content {
    fun addContentListener(listener: ContentListener) {}
    fun removeContentListener(listener: ContentListener) {}
    fun substring(start: Int, end: Int): String = ""
    fun replace(start: Int, end: Int, replacement: CharSequence) {}
    fun replace(startLine: Int, startColumn: Int, endLine: Int, endColumn: Int, replacement: CharSequence) {}
}
