@file:UseExperimental(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)

package ru.beryukhov.mpp.coroutines

/**
 * Implementation taken as is from https://github.com/chris-hatton/kotlin-multiplatform-template/blob/master/Code/Client/Shared/src/iosMain/kotlin/org/chrishatton/example/Coroutines.kt
 */
import kotlinx.coroutines.*
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

actual val uiScope = createMainScope()

// TODO: Use background Dispatcher when K/N Coroutines implementation can support it.
// See https://github.com/Kotlin/kotlinx.coroutines/issues/462

actual val processScope = createMainScope()

// TODO: Use background Dispatcher when K/N Coroutines implementation can support it.
// See https://github.com/Kotlin/kotlinx.coroutines/issues/462

actual val netScope = createMainScope()

private fun createMainScope() = object : CoroutineScope {
    private val dispatcher = MainDispatcher
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}

/**
 * Implementation inspired by:
 * https://github.com/Kotlin/kotlinx.coroutines/issues/462
 */
private object MainDispatcher : CoroutineDispatcher(), Delay {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run()
            } catch (err: Throwable) {
                //logError("UNCAUGHT", err.message ?: "", err)
                throw err
            }
        }
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000), dispatch_get_main_queue()) {
            try {
                with(continuation) {

                    resumeUndispatched(Unit)
                }
            } catch (err: Throwable) {
                //logError("UNCAUGHT", err.message ?: "", err)
                throw err
            }
        }
    }

    override fun invokeOnTimeout(timeMillis: Long, block: Runnable): DisposableHandle {
        val handle = object : DisposableHandle {
            var disposed = false
                private set

            override fun dispose() {
                disposed = true
            }
        }
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000), dispatch_get_main_queue()) {
            try {
                if (!handle.disposed) {
                    block.run()
                }
            } catch (err: Throwable) {
                //logError("UNCAUGHT", err.message ?: "", err)
                throw err
            }
        }

        return handle
    }
}
