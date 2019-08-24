package ru.beryukhov.mpp

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.w3c.dom.get
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.domain.TimeSheetRepositoryImpl
import ru.beryukhov.mpp.presenter.TimeSheetPresenter
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
            val presenter = TimeSheetPresenter(this, TimeSheetRepositoryImpl())
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
        setSpanText(text)
    }

    override fun clear() {
        setSpanText("")
    }

    private fun setSpanText(text: String) {
        val div = document.body
                ?.getElementsByClassName(divClass)?.get(0)
                ?.getElementsByClassName(spanClass)?.get(0)
        if (div is SPAN) {
            div.text(text)
        }
    }

    override fun showError(message: String) {
        window.alert(message)
    }

}