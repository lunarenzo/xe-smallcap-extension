package com.rk.events

import com.rk.editor.Editor
import kotlin.reflect.KClass

interface Event

sealed interface EditorEvent : Event {
    data class InstanceCreated(val editor: Editor) : EditorEvent
    data class InstanceDestroyed(val editor: Editor) : EditorEvent
}

interface EventSubscription {
    fun unsubscribe()
}

object Events {
    @PublishedApi internal val listeners = mutableMapOf<KClass<out Event>, MutableList<suspend (Event) -> Unit>>()

    inline fun <reified T : Event> subscribe(noinline listener: suspend (T) -> Unit): EventSubscription {
        return object : EventSubscription {
            override fun unsubscribe() {}
        }
    }

    suspend fun publish(event: Event) {}
}
