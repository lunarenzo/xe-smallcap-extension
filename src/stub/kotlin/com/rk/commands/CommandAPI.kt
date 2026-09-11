package com.rk.commands

import android.app.Activity
import com.rk.editor.Editor
import com.rk.icons.Icon
import com.rk.tabs.editor.EditorTab

open class ActionContext(open val currentActivity: Activity? = null)

open class EditorActionContext(
    currentActivity: Activity? = null,
    open val editorTab: EditorTab,
    open val editor: Editor
) : ActionContext(currentActivity)

open class EditorNonActionContext(open val editorTab: EditorTab)

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
