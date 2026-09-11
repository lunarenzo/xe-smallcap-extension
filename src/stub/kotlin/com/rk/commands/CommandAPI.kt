package com.rk.commands

import android.app.Activity
import com.rk.icons.Icon

open class ActionContext(open val currentActivity: Activity? = null)

open class EditorActionContext(
    currentActivity: Activity? = null,
    open val editorTab: EditorTab,
    open val editor: Editor
) : ActionContext(currentActivity)

open class EditorNonActionContext(open val editorTab: EditorTab)

interface CursorRange {
    val startIndex: Int
    val endIndex: Int
}

interface EditableText {
    fun substring(start: Int, end: Int): String
    fun replace(start: Int, end: Int, replacement: String)
}

interface Editor {
    val isTextSelected: Boolean
    val cursorRange: CursorRange
    val text: EditableText
}

interface EditorState {
    val editable: Boolean
    val editor: WeakRefEditor
}

interface WeakRefEditor {
    fun get(): Editor?
}

interface EditorTab {
    val isEditable: Boolean
    val editorState: EditorState
}

abstract class Command {
    abstract val id: String
    open val prefix: String? = null
    abstract fun getLabel(): String
    abstract fun getIcon(): Icon
    abstract fun execute(context: ActionContext)
    open fun isEnabled(): Boolean = true
    open fun isSupported(): Boolean = true
    open val preferText: Boolean = false
}

interface ToggleableCommand {
    fun isOn(): Boolean
}

abstract class EditorCommand : Command() {
    final override fun execute(context: ActionContext) {}
    abstract fun execute(context: EditorActionContext)
    open fun isSupported(context: EditorNonActionContext): Boolean = true
    open fun isEnabled(context: EditorNonActionContext): Boolean = true
}

object CommandProvider {
    fun registerCommand(command: Command) {}
    fun unregisterCommand(command: Command) {}
}

object ToolbarConfiguration {
    fun addEditorToolbarCommand(command: Command, index: Int? = null) {}
    fun removeEditorToolbarCommand(command: Command) {}
}
