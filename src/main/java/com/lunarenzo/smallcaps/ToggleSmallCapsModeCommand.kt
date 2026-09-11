package com.lunarenzo.smallcaps

import com.rk.commands.ActionContext
import com.rk.commands.EditorActionContext
import com.rk.commands.EditorCommand
import com.rk.commands.EditorNonActionContext
import com.rk.commands.ToggleableCommand
import com.rk.icons.Icon

/**
 * An [EditorCommand] implementing [ToggleableCommand] that enables/disables real-time
 * Small Caps text transformation mode for text input.
 */
class ToggleSmallCapsModeCommand(
    private val onToggleChanged: (Boolean) -> Unit
) : EditorCommand(), ToggleableCommand {

    override val id: String = "com.lunarenzo.smallcaps.toggle_mode"

    @Volatile
    private var isEnabledMode: Boolean = false

    override fun getLabel(): String = "Small Caps Input Mode"

    override fun getIcon(): Icon = Icon.TextIcon("ᴀʙ")

    /**
     * Returns true if Small Caps Input Mode is currently toggled on.
     */
    override fun isOn(): Boolean = isEnabledMode

    override fun execute(context: EditorActionContext) {
        action(context)
    }

    override fun execute(context: ActionContext) {
        action(context)
    }

    /**
     * Toggles the active state and notifies listener callback.
     */
    override fun action(context: EditorActionContext) {
        isEnabledMode = !isEnabledMode
        onToggleChanged(isEnabledMode)
    }

    override fun isSupported(context: EditorNonActionContext): Boolean = true

    override fun isEnabled(context: EditorNonActionContext): Boolean {
        return context.editorTab.editorState.editable
    }
}
