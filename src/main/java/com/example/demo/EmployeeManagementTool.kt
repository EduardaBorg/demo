package com.example.demo

import java.io.File
import java.util.*

data class Employee(val name: String, val position: String, val salary: Double)

class EmployeeManagementTool {
    private val employees = mutableListOf<Employee>()
    private val FILE_PATH = "employees.txt"

    init {
        loadEmployeesFromFile()
    }

    fun registerEmployee(fullName: String, position: String, salary: Double): Boolean {
        val employee = Employee(fullName, position, salary)
        employees.add(employee)
        saveEmployeesToFile()
        return true
    }

    fun listEmployees(): List<Employee> {
        return employees
    }

    fun deleteEmployee(name: String): Boolean {
        val foundEmployee = employees.find {
            it.name.split(" ")[0].equals(name, ignoreCase = true) ||
                    it.name.equals(name, ignoreCase = true)
        }
        return if (foundEmployee != null) {
            employees.remove(foundEmployee)
            saveEmployeesToFile()
            true
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
}
