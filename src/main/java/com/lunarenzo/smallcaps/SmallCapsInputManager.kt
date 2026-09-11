package com.lunarenzo.smallcaps

import com.rk.editor.Editor
import com.rk.events.EditorEvent
import com.rk.events.EventSubscription
import com.rk.events.Events
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import java.util.Collections
import java.util.WeakHashMap

/**
 * Manager handling real-time Small Caps text conversion during active editor typing.
 */
object SmallCapsInputManager {

    @Volatile
    var isModeEnabled: Boolean = false

    private val attachedEditors = Collections.newSetFromMap(WeakHashMap<Editor, Boolean>())
    private var eventSubscription: EventSubscription? = null

    /**
     * Internal listener implementation that transforms newly inserted ASCII text
     * into Unicode Small Caps in real-time.
     */
    private class SmallCapsContentListener : ContentListener {
        @Volatile
        private var isModifying = false

        override fun afterInsert(
            content: Content,
            startLine: Int,
            startColumn: Int,
            endLine: Int,
            endColumn: Int,
            insertedText: CharSequence
        ) {
            if (isModifying || !isModeEnabled) return
            if (insertedText.isEmpty()) return

            val convertedText = SmallCapsConverter.toSmallCaps(insertedText)
            if (convertedText != insertedText.toString()) {
                isModifying = true
                try {
                    content.replace(startLine, startColumn, endLine, endColumn, convertedText)
                } catch (ignored: Throwable) {
                    // Safe guard against out-of-bounds or content replacement errors
                } finally {
                    isModifying = false
                }
            }
        }
    }

    private val contentListener = SmallCapsContentListener()

    /**
     * Initializes event subscriptions to listen for newly created editor instances.
     */
    fun initialize() {
        if (eventSubscription != null) return
        eventSubscription = Events.subscribe<EditorEvent.InstanceCreated> { event ->
            attachToEditor(event.editor)
        }
    }

    /**
     * Attach real-time input conversion listener to a [CodeEditor] instance.
     */
    fun attachToEditor(editor: Editor?) {
        if (editor == null) return
        synchronized(attachedEditors) {
            if (!attachedEditors.contains(editor)) {
                attachedEditors.add(editor)
                try {
                    editor.text.addContentListener(contentListener)
                } catch (ignored: Throwable) {}
            }
        }
    }

    /**
     * Cleanup and unsubscribe listeners on extension teardown.
     */
    fun dispose() {
        eventSubscription?.unsubscribe()
        eventSubscription = null

        synchronized(attachedEditors) {
            for (editor in attachedEditors) {
                try {
                    editor.text.removeContentListener(contentListener)
                } catch (ignored: Throwable) {}
            }
            attachedEditors.clear()
        }
    }
}
