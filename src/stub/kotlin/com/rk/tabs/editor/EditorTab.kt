package com.rk.tabs.editor

import com.rk.editor.Editor
import io.github.rosemoe.sora.text.Content
import java.lang.ref.WeakReference

data class CodeEditorState(val initialContent: Content? = null) {
    var editor: WeakReference<Editor?> = WeakReference(null)
    var editable: Boolean = true
}

open class EditorTab {
    val editorState: CodeEditorState = CodeEditorState()
    val isEditable: Boolean = true
}
