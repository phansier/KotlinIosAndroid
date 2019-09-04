package ru.beryukhov.mpp.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope

actual val uiScope: CoroutineScope = MainScope()
actual val processScope: CoroutineScope = GlobalScope
actual val netScope: CoroutineScope = GlobalScope
