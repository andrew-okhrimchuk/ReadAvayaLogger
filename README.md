**Read Avaya Logger**

###### Приложение для прослушки порта и записи полученных данных в файл.

_Инструкция по установке:_
1. Скачать и разархивировать файл в любой удобный каталог.
2. Проверить наличие установленной Java.
3. Скомпилировать файлы. (не умеете: могу выслать готовые, это сложно, дорого потому и платно ;))
4. Зайти в файл \target\classes\common.config и настроить порт который Вы будете слушать, ну и хост заодно.
5. Дважды кликнуть мышкой по файлу  readAvayaLogger.JAR
6. Если у Вас ОС Винда, то в каталоге... \apps\readAvayaLogger\logs должны появиться логи работающей программы и сообщения об ошибках.
   Просьба их переслать по адресу a0972103356@gmail.com
7. Если у Вас линух, то Вам не повезло. Пишите по адресу, я допилю проект под мультиплатформу и вышлю скомиленные файлы  в ответ.
8. Что в итоге Вы получите: приложение слушает порт и пишет каждую строку результата в файл по каталогу указанному в common.config. Количество строк в файле можно настраивать. Сама запись в файл это конечно мрак, но для теста годится. Если требуется запись в базу, то все будет сделано на Doker-e, тогда с установкой и настройкой вообще не будет проблем. Тогда получение  любых web-отчётов в любом виде и за любой период будет ну ооочень удобным, это факт.
9. Приложение запустится в фоновом режиме. Убить программу тогда можно через запуск Диспетчера задач, поиск работающей проги и ее килл. (у меня имя программы выглядит так: "Java(TM) Platform SE binary Oracle Corporation". C:\Program Files\Java\jdk-11.0.5\bin\javaw.exe)

З.Ы. Серёжа Шв., главное вышли мне лог файлы что б знать где ошибка, я конечно могу сделать автоотправку этих логов себе на емейл, но это будет не правильно с точки зрения безопасности :).
