package com.example.demo

import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback

class EmployeeCell : ListCell<Employee>() {
    override fun updateItem(item: Employee?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item != null && !empty) {
            text = "Nome: ${item.name}, Cargo: ${item.position}, Salário: ${item.salary}"
        } else {
            text = null
        }
    }
}
