# Решения домашних заданий курса «Парадигмы программирования»
[Условия домашних заданий](https://www.kgeorgiy.info/courses/paradigms/homeworks.html)

## Домашнее задание 15. [Разбор выражений на Prolog](https://github.com/iskbaga/paradigms-itmo/blob/main/prolog-solutions/expression.pl)

Модификации
 * *VarBoolean* (36, 37). Сделать модификацию *Variables* и дополнительно реализовать поддержку:
    * Булевских операций
        * Аргументы: число больше 0 → `true`, иначе → `false`
        * Результат: `true` → 1, `false` → 0
        * `op_not` (`!`) - отрицание: `!5` равно 0
        * `op_and` (`&&`) – и: `5 & -6` равно 0
        * `op_or`  (`||`) - или: `5 & -6` равно 1
        * `op_xor` (`^^`) - исключающее или: `5 ^ -6` равно 1


## Домашнее задание 14. [Дерево поиска на Prolog](https://github.com/iskbaga/paradigms-itmo/blob/main/prolog-solutions/tree-map.pl)

Модификации
 * *PutIfAbsent* (36, 37)
    * Добавьте правило `map_putIfAbsent(Map, Key, Value, Result)`,
        добавляющее новый ключ и значение.


## Домашнее задание 13. [Простые числа на Prolog](https://github.com/iskbaga/paradigms-itmo/blob/main/prolog-solutions/primes.pl)

Модификации
 * *Compact* (36, 37)
    * Добавьте правило `compact_prime_divisors(N, CDs)`,
      где `CDs` — список пар (простое, степень):
        `compact_prime_divisors(120, [(2, 3), (3, 1), (5, 1)])`.


## Домашнее задание 11. [Объектные выражения на Clojure](https://github.com/iskbaga/paradigms-itmo/blob/main/clojure-solutions/expression.clj)

Модификации
 * *MeansqRMS* (36, 37). Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `Meansq` (`meansq`) – среднее квадратов, `(meansq 2 10 22)` равно 196;
        * `RMS` (`rms`) – [Root mean square](https://en.wikipedia.org/wiki/Root_mean_square), `(rms 2 10 22)` равно 14;


## Домашнее задание 10. [Функциональные выражения на Clojure](https://github.com/iskbaga/paradigms-itmo/blob/main/clojure-solutions/expression.clj)

Модификации
 * *SumexpLSE* (36, 37). Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `sumexp` – сумма экспонент, `(sumexp 2 3 16)` примерно равно 8886137;
        * `lse` – [LogSumExp](https://en.wikipedia.org/wiki/LogSumExp), `(lse 2 3 16)` примерно равно 16;


## Домашнее задание 9. [Линейная алгебра на Clojure](https://github.com/iskbaga/paradigms-itmo/blob/main/clojure-solutions/linear.clj)

Модификации
 * *Tensor* (36, 37)
    * Назовем _тензором_ многомерную прямоугольную таблицу чисел.
    * Добавьте операции поэлементного
        сложения (`t+`), вычитания (`t-`), умножения (`t*`) и деления (`td`)
        тензоров.
        Например, `(t+ [[1 2] [3 4]] [[5 6] [7 8]])`
        должно быть равно `[[6 8] [10 12]]`.


## Домашнее задание 8. [Обработка ошибок на JavaScript](https://github.com/iskbaga/paradigms-itmo/blob/main/javascript-solutions/objectExpression.js)

Модификации
 * *Postfix* (36-39). Дополнительно реализовать поддержку:
    * Выражений в постфиксной записи: 
        * `(2 3 +)` равно 5
        * функция `parsePostfix`
        * метод `postfix`
    * [Исходный код тестов](javascript/jstest/prefix/PostfixTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *SumexpLSE* (36, 37). Дополнительно реализовать поддержку:
    * Операций произвольного числа аргументов:
        * `Sumexp` (`sumexp`) – сумма экспонент, `(2 3 16 sumexp)` примерно равно 8886137;
        * `LSE` (`lse`) – [LogSumExp](https://en.wikipedia.org/wiki/LogSumExp), `(2 3 16 lse)` примерно равно 16;


## Домашнее задание 7. [Объектные выражения на JavaScript](https://github.com/iskbaga/paradigms-itmo/blob/main/javascript-solutions/objectExpression.js)

Модификации
 * *Distance* (36, 37). Дополнительно реализовать поддержку:
    * функций от `N` аргументов для `N=2..5`:
        * `SumSqN` (`sumsqN`) – сумма квадратов, `3 4 sumsq2` равно 25;
        * `DistanceN` (`distanceN`) – длина вектора, `3 4 distance2` равно 5.


## Домашнее задание 6. [Функциональные выражения на JavaScript](https://github.com/iskbaga/paradigms-itmo/blob/main/javascript-solutions/functionalExpression.js)

Модификации
 * *OneTwo* (32-39). Дополнительно реализовать поддержку:
    * констант:
        * `one` – 1;
        * `two` – 2;
 * *FP* (36, 37). Дополнительно реализовать поддержку:
    * операций:
        * `*+` (`madd`) – тернарный оператор произведение-сумма, `2 3 4 *+` равно 10;
        * `_` (`floor`) – округление вниз `2.7 _` равно 2;
        * `^` (`ceil`) – округление вверх `2.7 ^` равно 3.


## Домашнее задание 5. [Вычисление в различных типах](https://github.com/iskbaga/paradigms-itmo/tree/main/java-solutions/expression)

Модификации
 * *Asm* (36-39)
    * Дополнительно реализуйте унарные операции:
        * `abs` – модуль числа, `abs -5` равно 5;
        * `square` – возведение в квадрат, `square 5` равно 25.
    * Дополнительно реализуйте бинарную операцию (максимальный приоритет):
        * `mod` – взятие по модулю, приоритет как у умножения (`1 + 5 mod 3` равно `1 + (5 mod 3)` равно `3`).
 * *Uls* (36, 37)
    * Дополнительно реализуйте поддержку режимов:
        * `u` – вычисления в `int` без проверки на переполнение;
        * `l` – вычисления в `long` без проверки на переполнение;
        * `s` – вычисления в `short` без проверки на переполнение.


## Домашнее задание 4. [Очереди](https://github.com/iskbaga/paradigms-itmo/tree/main/java-solutions/queue)

Модификации
 * *Contains* (36, 37)
    * Добавить в интерфейс очереди и реализовать методы
        * `contains(element)` – проверяет, содержится ли элемент в очереди
        * `removeFirstOccurrence(element)` – удаляет первое вхождение элемента в очередь
            и возвращает было ли такое
    * Дублирования кода быть не должно


## Домашнее задание 3. [Очередь на массиве](https://github.com/iskbaga/paradigms-itmo/tree/main/java-solutions/queue)

Модификации
 * *Deque*
    * Дополнительно реализовать методы
        * `push` – добавить элемент в начало очереди;
        * `peek` – вернуть последний элемент в очереди;
        * `remove` – вернуть и удалить последний элемент из очереди.
 * *DequeToArray* (36, 37)
    * Реализовать модификацию *Deque*;
    * Реализовать метод `toArray`, возвращающий массив,
      содержащий элементы, лежащие в очереди в порядке
      от головы к хвосту.


## Домашнее задание 2. [Бинарный поиск](https://github.com/iskbaga/paradigms-itmo/tree/main/java-solutions/search)

Модификации
 * *Oddity* (32 - 37)
    * Если сумма всех чисел во входе чётная, то должна быть использоваться
      рекурсивная версия, иначе — итеративная.
 * *Uni* (36, 37)
    * На вход подается массив полученный приписыванием
      в конец массива отсортированного (строго) по возрастанию,
      массива отсортированного (строго) по убыванию.
      Требуется найти минимальную возможную длину первого массива.
    * Класс должен иметь имя `BinarySearchUni`


## Домашнее задание 1. [Обработка ошибок](https://github.com/iskbaga/paradigms-itmo/tree/main/java-solutions/expression)

Модификации
 * *SetClear* (32-37)
    * Дополнительно реализуйте бинарные операции (минимальный приоритет):
        * `set` – установка бита, `2 set 3` равно 10;
        * `clear` – сброс бита, `10 clear 3` равно 2.
 * *Count* (32-37)
    * Дополнительно реализуйте унарную операцию
      `count` – число установленных битов, `count -5` равно 31.
