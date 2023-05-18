package com.example.demo

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
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

    fun registerEmployee() {
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

    fun deleteEmployee() {
        val deleteDialog = TextInputDialog()
        deleteDialog.title = "Excluir funcionário"
        deleteDialog.headerText = "Digite o nome do funcionário a ser excluído:"
        val name = deleteDialog.showAndWait().orElse(null) ?: return

        val foundEmployees = employeeManagementTool.listEmployees().filter {
            it.name.equals(name, ignoreCase = true)
        }

        if (foundEmployees.isNotEmpty()) {
            val selectedEmployee = selectEmployeeToDelete(foundEmployees)
            if (selectedEmployee != null) {
                employeeManagementTool.deleteSelectedEmployees(listOf(selectedEmployee))
            } else {
                println("Nenhum funcionário selecionado para exclusão.")
            }
        } else {
            println("Nenhum funcionário encontrado com o nome especificado.")
        }

        updateEmployeeList()
    }


    private fun selectEmployeeToDelete(employees: List<Employee>): Employee? {
        val employeeNames = employees.map { it.name }.distinct()
        val selectionDialog = ChoiceDialog<String>(employeeNames.first(), employeeNames)
        selectionDialog.title = "Excluir funcionário"
        selectionDialog.headerText = "Foram encontrados vários funcionários com o mesmo nome. Selecione o funcionário para excluir:"

        val result = selectionDialog.showAndWait()
        if (result.isPresent) {
            val selectedName = result.get()
            return employees.find { it.name == selectedName }
        }

        return null
    }

    fun exitApplication() {
        System.exit(0)
    }

    fun updateEmployeeList() {
        employeeListView.items.clear()
        employeeListView.items.addAll(employeeManagementTool.listEmployees())
    }
}
