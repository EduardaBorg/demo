package com.example.demo

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Employee Management Tool"

        val loader = FXMLLoader(Main::class.java.getResource("com/example/demo/EmployeeManagementTool.fxml"))
        primaryStage.scene = Scene(loader.load())

        primaryStage.show()
    }
}

fun main() {
    Application.launch(Main::class.java)
}
