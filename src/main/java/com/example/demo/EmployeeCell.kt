package com.example.demo

import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback

class EmployeeCell : ListCell<Employee>() {
    override fun updateItem(item: Employee?, empty: Boolean) {
        super.updateItem(item, empty)
        text = item?.let { "Nome: ${it.name}, Cargo: ${it.position}, Salário: ${it.salary}" } ?: null
    }
}
