package com.lunarenzo.smallcaps

import com.rk.commands.EditorActionContext
import com.rk.commands.EditorCommand
import com.rk.commands.EditorNonActionContext
import com.rk.icons.Icon

/**
 * An [EditorCommand] that converts highlighted selected text inside the active text editor
 * into Unicode Small Caps characters (e.g. "Sword" / "sword" -> "ѕᴡᴏʀᴅ").
 */
class TransformSmallCapsCommand : EditorCommand() {

    override val id: String = "com.lunarenzo.smallcaps.transform"

    // Prefer label text representation when rendered on action toolbars
    override val preferText: Boolean = true

    override fun getLabel(): String = "ѕᴡᴏʀᴅ (Small Caps)"

    override fun getIcon(): Icon = Icon.TextIcon("ѕᴡ")

    /**
     * Replaces the currently active selection range with converted Small Caps text.
     */
    override fun execute(context: EditorActionContext) {
        val editor = context.editor
        if (editor.isTextSelected) {
            val selectionStart = editor.cursorRange.startIndex
            val selectionEnd = editor.cursorRange.endIndex

            if (selectionStart < selectionEnd) {
                val selectedText = editor.text.substring(selectionStart, selectionEnd)
                val smallCapsText = SmallCapsConverter.toSmallCaps(selectedText)
                editor.text.replace(selectionStart, selectionEnd, smallCapsText)
            }
        }
    }

    /**
     * Enabled only when the active editor is editable and has a non-empty text selection.
     */
    override fun isEnabled(context: EditorNonActionContext): Boolean {
        val editorTab = context.editorTab
        val isEditable = editorTab.editorState.editable
        val editor = editorTab.editorState.editor.get()
        return isEditable && (editor?.isTextSelected == true)
    }

    /**
     * Supported whenever the active editor tab is in editable state.
     */
    override fun isSupported(context: EditorNonActionContext): Boolean {
        return context.editorTab.editorState.editable
    }
}
