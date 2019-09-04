package ru.beryukhov.mpp.coroutines

import kotlinx.coroutines.CoroutineScope

expect val uiScope: CoroutineScope

expect val processScope: CoroutineScope

expect val netScope: CoroutineScope