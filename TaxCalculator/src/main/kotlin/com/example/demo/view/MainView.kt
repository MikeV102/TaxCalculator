package com.example.demo.view

import com.example.demo.app.Styles
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import tornadofx.*
import java.lang.NumberFormatException

class MainView : View("TestingOnEditListener") {

private var editedTextFiled: TextField by singleAssign()
private var myLabel = SimpleStringProperty("0")
var comboBox: ComboBox<Int> by singleAssign()
var comboBoxValue = SimpleIntegerProperty()


    override val root = vbox {

        hbox {
            label {
                text = "Value with tax: "
                addClass(Styles.heading)
            }
            label(myLabel) {
                addClass(Styles.heading)
                textProperty().cleanBind(myLabel)
            }
        }

        form {
            fieldset {

                field {
                    label {
                        text = "Tax value (%) : "
                    }

                    comboBox = combobox(values = listOf(5,8,23)) {
                        prefWidth = 135.0
                        value = 5
                    }
                    comboBox.valueProperty().onChange {
                        calculateValueWithTax()
                    }

                }

                field {
                    label{
                        text = "good price: "
                    }
                }
                field {
                    editedTextFiled = textfield("0")
                    editedTextFiled.filterInput {
                        it.controlNewText.isDouble() || it.controlNewText.isInt()
                    }
                   editedTextFiled.textProperty().addListener { _, oldValue, newValue ->
                       run {
                           println("textfield " +
                                   "changed from $oldValue to $newValue")
                           if(!(newValue.isNullOrBlank() || newValue.isNullOrBlank()))
                            calculateValueWithTax()
                       }
                   }

                }
            }
        }
    }

    fun calculateValueWithTax()
    {

            val intValueOfParameter = editedTextFiled.text.trim().toInt()
            val withTax = intValueOfParameter +  (intValueOfParameter * comboBox.value/100 )
            myLabel.set(withTax.toString())

//        catch (ex: NumberFormatException)
//        {
//            val alert = Alert(Alert.AlertType.ERROR,"You have input an illegal character(s), please delete all " +
//                    "non-digit characters")
//            alert.show()
//
//        }
    }
}