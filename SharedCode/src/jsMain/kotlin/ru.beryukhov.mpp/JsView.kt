package ru.beryukhov.mpp

import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import kotlinx.html.span
import org.w3c.dom.asList
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetRepositoryImpl
import ru.beryukhov.mpp.presenter.TimeSheetPresenterKmp
import ru.beryukhov.mpp.view.TimeSheetView
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    println("Hello JavaScript!")
    JsView.printHello()
}

private const val divClass = "div"
private const val spanClass = "span"

object JsView : TimeSheetView {
    fun printHello() {
        window.onload = {
            //create presenter
            val presenter = TimeSheetPresenterKmp(this, TimeSheetRepositoryImpl())
            presenter.onCreateView()
            //create buttons
            document.body!!.append.div {
                button {
                    onClickFunction = {
                        println("Fix start")
                        presenter.onFixStart()
                    }
                    +"Fix start"
                }
                button {
                    onClickFunction = {
                        println("Fix end")
                        presenter.onFixEnd()
                    }
                    +"Fix end"
                }
            }
            //create list
            document.body?.append?.div(classes = divClass) {
                span(classes = spanClass) {

                }
            }
        }
    }

    override fun addAll(list: List<DateModel>) {
        val text = list.fold("") { text, item -> "$text$item\n" }
        println(text)
        document.body?.append?.div(classes = divClass) {
            span(classes = spanClass) {
                +text
            }
        }
    }

    override fun clear() {
        document.body
                ?.getElementsByClassName(divClass)
                ?.asList()
                ?.forEach { it.remove() }
    }

    override fun showError(message: String) {
        window.alert(message)
    }

}