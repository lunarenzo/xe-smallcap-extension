package io.github.rosemoe.sora.widget

import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.TextRange

open class CodeEditor {
    val isTextSelected: Boolean = false
    val cursorRange: TextRange = TextRange()
    val text: Content = Content()
}
