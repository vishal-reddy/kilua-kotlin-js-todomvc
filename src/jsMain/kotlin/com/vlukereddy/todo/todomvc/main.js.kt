package com.vlukereddy.todo.todomvc

import dev.kilua.Hot

actual fun webpackHot(): Hot? {
    return js("import.meta.webpackHot").unsafeCast<Hot?>()
}
