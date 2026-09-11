package com.lunarenzo.smallcaps

import android.os.Handler
import android.os.Looper
import com.rk.editor.Editor
import com.rk.events.EditorEvent
import com.rk.events.EventSubscription
import com.rk.events.Events
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import java.util.Collections
import java.util.WeakHashMap

/**
 * High-performance, non-blocking manager handling real-time Small Caps text conversion
 * during active editor typing passes without UI thread locks or backspace delays.
 */
object SmallCapsInputManager {

    @Volatile
    var isModeEnabled: Boolean = false

    private val attachedEditors = Collections.newSetFromMap(WeakHashMap<Editor, Boolean>())
    private var eventSubscription: EventSubscription? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    /**
     * Non-blocking listener implementation that transforms newly inserted ASCII text
     * into Unicode Small Caps asynchronously on the main looper pass.
     */
    private class SmallCapsContentListener(private val editor: Editor) : ContentListener {
        @Volatile
        private var isModifying = false

        override fun beforeReplace(
            content: Content,
            startLine: Int,
            startColumn: Int,
            endLine: Int,
            endColumn: Int,
            replacement: CharSequence
        ) {}

        override fun afterInsert(
            content: Content,
            startLine: Int,
            startColumn: Int,
            endLine: Int,
            endColumn: Int,
            insertedText: CharSequence
        ) {
            // Fast O(1) return path if mode is disabled, reentrant, or inserted string is empty
            if (isModifying || !isModeEnabled || insertedText.isEmpty()) return

            val convertedText = SmallCapsConverter.toSmallCaps(insertedText)
            if (convertedText == insertedText.toString()) return

            // Post conversion to main looper to prevent frame lock and backspace lag
            mainHandler.post {
                if (!isModeEnabled || isModifying) return@post
                isModifying = true
                try {
                    val currentContent = editor.text
                    currentContent.replace(startLine, startColumn, endLine, endColumn, convertedText)
                } catch (ignored: Throwable) {
                    // Safe guard against position changes
                } finally {
                    isModifying = false
                }
            }
        }

        override fun afterDelete(
            content: Content,
            startLine: Int,
            startColumn: Int,
            endLine: Int,
            endColumn: Int,
            deletedText: CharSequence
        ) {
            // Pure empty no-op pass guaranteeing 0% delay on backspace/deletion
        }
    }

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
     * Attach real-time input conversion listener to an [Editor] instance.
     */
    fun attachToEditor(editor: Editor?) {
        if (editor == null) return
        synchronized(attachedEditors) {
            if (!attachedEditors.contains(editor)) {
                attachedEditors.add(editor)
                try {
                    val listener = SmallCapsContentListener(editor)
                    editor.text.addContentListener(listener)
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
            attachedEditors.clear()
        }
    }
}
