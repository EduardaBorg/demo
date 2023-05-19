package com.example.demo

import java.io.File
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType


class EmployeeManagementTool {
    private val employees = mutableListOf<Employee>()
    private val FILE_PATH = "employees.txt"

    init {
        try {
            loadEmployeesFromFile()
        } catch (e: Exception) {
            Alert(AlertType.ERROR, "Error while loading employees: ${e.message}").showAndWait()
        }
    }

    fun registerEmployee(fullName: String, position: String, salary: Double): Boolean {
        val employee = Employee(fullName, position, salary)
        employees.add(employee)
        try {
            saveEmployeesToFile()
        } catch (e: Exception) {
            Alert(AlertType.ERROR, "Error while saving employees: ${e.message}").showAndWait()
            return false
        }
        return true
    }

    fun listEmployees(): List<Employee> {
        return employees
    }

    fun deleteEmployee(name: Employee): Boolean {
        val foundEmployee = employees.find {
            it.name.startsWith(name.name, ignoreCase = true)
        }

        return if (foundEmployee != null) {
            employees.remove(foundEmployee)
            try {
                saveEmployeesToFile()
                true
            } catch (e: Exception) {
                Alert(AlertType.ERROR, "Error while saving employees: ${e.message}").showAndWait()
                false
            }
        } else {
            false
        }
    }

    private fun loadEmployeesFromFile() {
        val file = File(FILE_PATH)
        if (file.exists()) {
            file.useLines { lines ->
                lines.forEach { line ->
                    val parts = line.split(",")
                    if (parts.size == 3) {
                        val name = parts[0].trim()
                        val position = parts[1].trim()
                        val salary = parts[2].trim().toDoubleOrNull()
                        if (salary != null) {
                            val employee = Employee(name, position, salary)
                            employees.add(employee)
                        }
                    }
                }
            }
        }
    }

    private fun saveEmployeesToFile() {
        val file = File(FILE_PATH)
        file.bufferedWriter().use { writer ->
            employees.forEach { employee ->
                writer.write("${employee.name},${employee.position},${employee.salary}")
                writer.newLine()
            }
        }
    }

    fun deleteSelectedEmployees(selectedEmployees: List<Employee>): Boolean {
        employees.removeAll(selectedEmployees)
        return try {
            saveEmployeesToFile()
            true
        } catch (e: Exception) {
            Alert(AlertType.ERROR, "Error while saving employees: ${e.message}").showAndWait()
            false
        }

    }

}
