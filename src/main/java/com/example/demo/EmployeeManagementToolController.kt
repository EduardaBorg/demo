package com.example.demo

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.control.TextInputDialog
import javafx.util.Callback
import java.net.URL
import java.util.*

class EmployeeManagementToolController : Initializable {
    @FXML
    private lateinit var employeeListView: ListView<Employee>

    @FXML
    private lateinit var menuItemRegisterEmployee: MenuItem

    @FXML
    private lateinit var menuItemDeleteEmployee: MenuItem

    @FXML
    private lateinit var menuItemExit: MenuItem

    private val employeeManagementTool = EmployeeManagementTool()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        employeeListView.cellFactory = Callback<ListView<Employee>, ListCell<Employee>> { EmployeeCell() }

        menuItemRegisterEmployee.setOnAction { registerEmployee() }
        menuItemDeleteEmployee.setOnAction { deleteEmployee() }
        menuItemExit.setOnAction { exitApplication() }

        updateEmployeeList()
    }

    private fun registerEmployee() {
        val fullNameDialog = TextInputDialog()
        fullNameDialog.title = "Cadastro de funcionário"
        fullNameDialog.headerText = "Nome completo:"
        val fullName = fullNameDialog.showAndWait().orElse(null) ?: return

        val positionDialog = TextInputDialog()
        positionDialog.title = "Cadastro de funcionário"
        positionDialog.headerText = "Cargo:"
        val position = positionDialog.showAndWait().orElse(null) ?: return

        val salaryDialog = TextInputDialog()
        salaryDialog.title = "Cadastro de funcionário"
        salaryDialog.headerText = "Salário:"
        val salary = salaryDialog.showAndWait().orElse(null)?.toDoubleOrNull() ?: return

        employeeManagementTool.registerEmployee(fullName, position, salary)

        updateEmployeeList()
    }

    private fun deleteEmployee() {
        val deleteDialog = TextInputDialog()
        deleteDialog.title = "Excluir funcionário"
        deleteDialog.headerText = "Digite o nome do funcionário a ser excluído:"
        val name = deleteDialog.showAndWait().orElse(null) ?: return

        employeeManagementTool.deleteEmployee(name)

        updateEmployeeList()
    }

    private fun exitApplication() {
        System.exit(0)
    }

    private fun updateEmployeeList() {
        employeeListView.items.clear()
        employeeListView.items.addAll(employeeManagementTool.listEmployees())
    }
}
