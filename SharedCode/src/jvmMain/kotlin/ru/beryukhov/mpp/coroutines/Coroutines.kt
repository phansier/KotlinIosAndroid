package ru.beryukhov.mpp.coroutines

import kotlinx.coroutines.*

actual val uiScope: CoroutineScope = MainScope()
actual val processScope: CoroutineScope = GlobalScope
actual val netScope: CoroutineScope = GlobalScope
