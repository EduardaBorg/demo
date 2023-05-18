package com.example.demo

import javafx.scene.control.ChoiceDialog

class CustomChoiceDialog<T>(defaultChoice: T, choices: List<T>) : ChoiceDialog<T>(defaultChoice, choices)
