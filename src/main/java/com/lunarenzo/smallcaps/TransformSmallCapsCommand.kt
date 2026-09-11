package com.lunarenzo.smallcaps

import com.rk.commands.ActionContext
import com.rk.commands.EditorActionContext
import com.rk.commands.EditorCommand
import com.rk.commands.EditorNonActionContext
import com.rk.icons.Icon

/**
 * An [EditorCommand] that converts highlighted selected text inside the active text editor
 * into Unicode Small Caps characters.
 */
class TransformSmallCapsCommand : EditorCommand() {

    override val id: String = "com.lunarenzo.smallcaps.transform"

    override val preferText: Boolean = true

    override fun getLabel(): String = "Convert to Small Caps"

    override fun getIcon(): Icon = Icon.TextIcon("ѕᴄ")

    /**
     * Compatibility entry point for host app calling execute(context).
     */
    override fun execute(context: EditorActionContext) {
        action(context)
    }

    /**
     * Compatibility entry point for host app calling execute(context).
     */
    override fun execute(context: ActionContext) {
        action(context)
    }

    /**
     * Primary action handler called by Xed-Editor runtime.
     * Replaces the currently active selection range with converted Small Caps text.
     */
    override fun action(context: EditorActionContext) {
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

    override fun isEnabled(context: EditorNonActionContext): Boolean {
        val editorTab = context.editorTab
        val isEditable = editorTab.editorState.editable
        val editor = editorTab.editorState.editor.get()
        return isEditable && (editor?.isTextSelected == true)
    }

    override fun isSupported(context: EditorNonActionContext): Boolean {
        return context.editorTab.editorState.editable
    }
}
