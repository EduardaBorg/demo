package com.example.demo;

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.TextFieldListCell
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.util.StringConverter
import java.net.URL
import java.util.*
import kotlin.system.exitProcess

class EmployeeManagementToolController : Initializable {

    @FXML
    private lateinit var errorLabel: Label

    @FXML
    private lateinit var employeeListView: ListView<Employee>

    @FXML
    private lateinit var searchTextField: TextField

    @FXML
    private lateinit var nameTextField: TextField

    @FXML
    private lateinit var jobTextField: TextField

    @FXML
    private lateinit var salaryTextField: TextField

    @FXML
    private lateinit var buttonDeleteEmployee: Button

    private val employeeManagementTool = EmployeeManagementTool()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        employeeListView.cellFactory = TextFieldListCell.forListView(object : StringConverter<Employee>() {
            override fun toString(employee: Employee?): String {
                return if (employee != null) {
                    "${employee.name}, ${employee.position}, ${employee.salary}"
                } else {
                    ""
                }
            }

            override fun fromString(string: String?): Employee? {
                return null
            }
        })




        searchTextField.textProperty().addListener { _, _, newValue ->
            filterEmployeeList(newValue)
        }

        buttonDeleteEmployee.setOnAction { deleteSelectedEmployee() }

        updateEmployeeList()
    }

    fun registerEmployee() {
        val fullName = nameTextField.text
        val position = jobTextField.text
        val salary = salaryTextField.text.toDoubleOrNull()

        if (fullName.isNullOrBlank() || position.isNullOrBlank() || salary == null) {
            errorLabel.text = "Preencha todos os campos corretamente."
            return
        }

        employeeManagementTool.registerEmployee(fullName, position, salary)

        clearFields()
        updateEmployeeList()
    }

    private fun filterEmployeeList(searchTerm: String) {
        val filteredEmployees = employeeManagementTool.listEmployees()
            .filter { it.name.startsWith(searchTerm, ignoreCase = true) }
        employeeListView.items.clear()
        employeeListView.items.addAll(filteredEmployees)
    }

    fun deleteSelectedEmployee() {
        val selectedEmployee = employeeListView.selectionModel.selectedItem

        if (selectedEmployee == null) {
            val alert = Alert(AlertType.WARNING)
            alert.title = "Nenhum usuário selecionado"
            alert.headerText = "Selecione um usuário para excluí-lo."
            alert.showAndWait()
            return
        }

        val confirmationAlert = Alert(AlertType.CONFIRMATION)
        confirmationAlert.title = "Confirmar exclusão"
        confirmationAlert.headerText = "Deseja realmente excluir o funcionário selecionado?"
        confirmationAlert.contentText = selectedEmployee.name

        val result = confirmationAlert.showAndWait()
        if (result.orElse(null) == ButtonType.OK) {
            employeeManagementTool.deleteEmployee(selectedEmployee)
            updateEmployeeList()
        }
    }


    private fun updateEmployeeList() {
        employeeListView.items.clear()
        employeeListView.items.addAll(employeeManagementTool.listEmployees())
    }

    private fun clearFields() {
        nameTextField.clear()
        jobTextField.clear()
        salaryTextField.clear()
        errorLabel.text = ""
    }
}
