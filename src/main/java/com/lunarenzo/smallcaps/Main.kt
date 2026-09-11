package com.lunarenzo.smallcaps

import androidx.annotation.Keep
import com.rk.commands.CommandProvider
import com.rk.commands.ToolbarConfiguration
import com.rk.extension.ExtensionAPI
import com.rk.extension.ExtensionContext

/**
 * Main entry point for the Small Caps Text Converter Xed-Editor Extension.
 *
 * Annotated with [@Keep] to prevent R8/ProGuard obfuscation during dynamic reflect loading.
 */
@Keep
@Suppress("unused")
class Main(context: ExtensionContext) : ExtensionAPI(context) {

    private val transformCommand = TransformSmallCapsCommand()
    private val toggleCommand = ToggleSmallCapsModeCommand { isEnabled ->
        context.logInfo("Small Caps Input Mode toggled: $isEnabled")
    }

    /**
     * Called when the extension is loaded into memory by Xed-Editor host system.
     * Registers custom commands into [CommandProvider] and adds them to editor toolbar.
     */
    override fun onLoad() {
        context.logInfo("Initializing Small Caps Extension v1.0.0...")

        // Initialize real-time input manager and event subscriptions
        SmallCapsInputManager.initialize()

        // Register commands into global CommandProvider
        CommandProvider.registerCommand(transformCommand)
        CommandProvider.registerCommand(toggleCommand)

        // Expose action buttons on the editor action toolbar
        ToolbarConfiguration.addEditorToolbarCommand(transformCommand)
        ToolbarConfiguration.addEditorToolbarCommand(toggleCommand)

        context.logInfo("Small Caps Extension loaded successfully.")
    }

    /**
     * Called when the extension is unloaded or disposed.
     * Cleans up all toolbar items and command registrations.
     */
    override fun onDispose() {
        context.logInfo("Disposing Small Caps Extension...")

        // Dispose real-time input manager
        SmallCapsInputManager.dispose()

        // Remove commands from toolbar
        ToolbarConfiguration.removeEditorToolbarCommand(transformCommand)
        ToolbarConfiguration.removeEditorToolbarCommand(toggleCommand)

        // Unregister commands from global CommandProvider
        CommandProvider.unregisterCommand(transformCommand)
        CommandProvider.unregisterCommand(toggleCommand)

        context.logInfo("Small Caps Extension disposed cleanly.")
    }
}
