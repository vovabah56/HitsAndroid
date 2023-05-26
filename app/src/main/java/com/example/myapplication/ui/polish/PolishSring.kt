package com.example.myapp.polish

class PolishString(expression: String) {
    var expression: String  // Поле для хранения исходного выражения
    var isExpressionCorrect: Boolean = true  // Флаг, указывающий на корректность выражения

    init {
        this.expression = expression.filter { !it.isWhitespace() }  // Очистка выражения от пробелов

        this.expression = turnToReversePolish(this.expression)  // Преобразование выражения в обратную польскую запись
    }

    // Метод для преобразования выражения в обратную польскую запись
    private fun turnToReversePolish(str: String): String {
        var reversePolish = ""  // Строка для хранения результата обратной польской записи
        val stack = Stack()  // Стек для операторов

        val letterArray = arrayListOf<Char>()  // Список символов, являющихся операндами
        for (i in 48..57)
            letterArray.add(Char(i))  // Цифры 0-9
        for (i in 65..90)
            letterArray.add(Char(i))  // Заглавные буквы A-Z
        for (i in 97..122)
            letterArray.add(Char(i))  // Строчные буквы a-z
        letterArray.add(Char(95))  // Символ подчеркивания (_)

        for (char in str) {
            if (char in letterArray) {
                reversePolish += char  // Операнд добавляется в конец строки обратной польской записи
            } else if(reversePolish.isNotEmpty()){
                when (char) {
                    '+', '-', '*', '/', '%', '('  -> {
                        if (reversePolish.last() in letterArray)
                            reversePolish += ','  // Если последний символ в обратной польской записи - операнд, добавляем запятую перед оператором
                        reversePolish += stack.push(char)  // Оператор добавляется в стек и в конец строки обратной польской записи
                    }
                    ')' -> {
                        if (reversePolish.last() in letterArray)
                            reversePolish += ','  // Если последний символ в обратной польской записи - операнд, добавляем запятую перед закрывающей скобкой
                        reversePolish += stack.closeBracket()  // Закрывающая скобка добавляется в конец строки обратной польской записи
                    }

                    else -> this.isExpressionCorrect = false  // Встречен недопустимый символ, выражение некорректно
                }
            }
        }
        while (!stack.isEmpty()) {
            reversePolish += ','  // Добавляем запятую перед символом из стека
            reversePolish += stack.arrayOfChars.removeAt(stack.arrayOfChars.size - 1)  // Удаляем символ из стека и добавляем его в конец строки обратной польской записи
        }

        return "$reversePolish,"
    }
}