package ru.beryukhov.mpp

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.w3c.dom.asList
import ru.beryukhov.mpp.domain.BaseTimeSheetRepositoryImpl
import ru.beryukhov.mpp.domain.DateModel
import ru.beryukhov.mpp.presenter.TimeSheetPresenter
import ru.beryukhov.mpp.view.TimeSheetView
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    JsView.init()
}

private const val divClass = "div"
private const val spanClass = "span"
private const val defaultButtonStyle =
    "border: none; color: white; padding: 15px 32px; margin: 18px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px;"
private const val greenButtonStyle = "background-color: #4CAF50;\n" + defaultButtonStyle
private const val redButtonStyle = "background-color: #f44336;\n" + defaultButtonStyle
private const val divStyle = "padding: 8px 16px; border-bottom: 2px solid #7B5427;"


object JsView : TimeSheetView {
    fun init() {
        window.onload = {
            //create presenter
            val presenter = TimeSheetPresenter(this, BaseTimeSheetRepositoryImpl())
            presenter.onCreateView()
            //create buttons
            document.body!!.append.div {
                button {
                    onClickFunction = {
                        println("Fix start")
                        presenter.onFixStart()
                    }
                    style = greenButtonStyle
                    +"Fix start"
                }
                button {
                    onClickFunction = {
                        println("Fix end")
                        presenter.onFixEnd()
                    }
                    style = redButtonStyle
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
        println("addAll")
        list.forEach {
            document.body?.append?.div(classes = divClass) {
                style = divStyle
                p(classes = spanClass) {
                    +"Date: ${it.date}"
                }
                p(classes = spanClass) {
                    +"Time: ${it.startTime} - ${it.endTime}"
                }
                p(classes = spanClass) {
                    +"Duration: ${it.hours}"
                }
            }
        }
    }

    override fun clear() {
        println("clear")
        document.body
            ?.getElementsByClassName(divClass)
            ?.asList()
            ?.forEach { it.remove() }
    }

    override fun showError(message: String) {
        window.alert(message)
    }

}