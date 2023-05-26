package com.example.myapp.polish

class Stack {
    var arrayOfChars = mutableListOf<Char>()  // Массив символов для хранения стека

    private var operatorPriority = mutableMapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '%' to 2, '(' to -1, ')' to -1)
    // Операторы и их приоритеты. Чем больше значение приоритета, тем выше приоритет оператора.

    fun isEmpty(): Boolean {
        return arrayOfChars.isEmpty()  // Проверка, пуст ли стек
    }

    // Метод для добавления символа в стек и формирования строки вывода
    fun push(element: Char): String {
        var output = ""  // Строка для хранения результата

        if (element == '(') {
            arrayOfChars.add(element)  // Открывающая скобка добавляется в стек
            return output
        }

        while (arrayOfChars.size > 0) {
            // Если приоритет текущего оператора меньше или равен приоритету последнего оператора в стеке,
            // последний оператор удаляется из стека и добавляется в результат с запятой
            if (operatorPriority[element]!! <= operatorPriority[arrayOfChars.last()]!!) {
                output += arrayOfChars.removeAt(arrayOfChars.size - 1)
                output += ","
            } else {
                break  // Прекращаем цикл, если приоритет текущего оператора больше приоритета последнего оператора в стеке
            }
        }

        arrayOfChars.add(element)  // Текущий оператор добавляется в стек
        return output  // Возвращается сформированная строка вывода
    }

    // Метод для обработки закрывающей скобки и формирования строки вывода
    fun closeBracket(): String {
        var output = ""  // Строка для хранения результата

        while (arrayOfChars.isNotEmpty() && arrayOfChars.last() != '(') {
            // Пока стек не пуст и последний символ в стеке не является открывающей скобкой,
            // удаляем последний символ из стека и добавляем его в результат
            output += arrayOfChars.removeAt(arrayOfChars.size - 1)
        }

        if (arrayOfChars.size > 0) {
            arrayOfChars.removeAt(arrayOfChars.size - 1)  // Удаляем открывающую скобку из стека
        }

        output += ','  // Добавляем запятую в результат
        return output  // Возвращается сформированная строка вывода
    }
}