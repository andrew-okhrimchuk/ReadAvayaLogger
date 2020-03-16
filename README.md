**Read Avaya Logger**

###### Приложение для прослушки порта и записи полученных данных в файл.

_Инструкция по установке:_
1. Скачать и разархивировать файл в любой удобный каталог.
2. Проверить наличие установленной Java.
3. Скомпилировать файлы. (не умеете: могу выслать готовые, это сложно, дорого потому и платно ;))
4. Зайти в файл \target\classes\common.config и настроить порт который Вы будете слушать.
5. Дважды кликнуть мышкой по файлу  nonBlockingEchoServer.JAR

5.1 если не запустился или работает с проблемами, попробуйте запустить классически, как это делаю я в консоли CMD:

"C:\Program Files\Java\jdk1.8.0_144\bin\java.exe" -Dfile.encoding=windows-1251 -jar D:\Stajirovka\ReadAvayaLogger\target\nonBlockingEchoServer.jar
или   
java -Dfile.encoding=windows-1251 -jar D:\Stajirovka\ReadAvayaLogger\target\nonBlockingEchoServer.jar
где "C:\Program Files\Java\jdk1.8.0_144\bin\java.exe" - путь к Вашей джаве.
а  - D:\Stajirovka\ReadAvayaLogger\target\nonBlockingEchoServer.jar  соответственно путь к приложению.
остальное просто повторяем как есть. 
6. Если у Вас ОС Винда, то в каталоге D:\apps\nonBlockingEchoServer\logs должны появиться логи работающей программы и сообщения об ошибках.
   Просьба их переслать по адресу a0972103356@gmail.com
7. Если у Вас линух, то Вам не повезло. Пишите по адресу, я допилю проект под мультиплатформу и вышлю скомиленные файлы  в ответ.
8. Что в итоге Вы получите: приложение слушает порт и пишет каждую строку результата в файл по каталогу указанному в common.config.  Сама запись в файл это конечно мрак, но для теста годится. Если требуется запись в базу, то все будет сделано на Doker-e, тогда с установкой и настройкой вообще не будет проблем. Тогда получение  любых web-отчётов в любом виде и за любой период будет ну ооочень удобным, это факт.
Если АТС-сервер, порт которого слушается отвалится, сама программа через каждые 5 секунд будет пытаться подключиться снова.
9. Приложение запустится в фоновом режиме. Убить программу тогда можно через запуск Диспетчера задач, поиск работающей проги и ее килл. (у меня имя программы выглядит так: "Java(TM) Platform SE binary Oracle Corporation". C:\Program Files\Java\jdk-11.0.5\bin\javaw.exe)
10. Если не коннектится:
- проверяем не блокирует ли фаервол порт - https://vynesimozg.com/kak-blokirovat-ili-razreshit-port-v-brandmauere-windows-10/
ну и добавляем правило на нужный порт.
- если сервера в локальной сети, то проверяем статическую маршрутизацию - https://27sysday.ru/setevoe-oborudovanie/kak-propisat-staticheskij-marshrut-na-routere-tp-link
- слушается ли IP сервера АТС на машине где стоит тарификатор(запуск cmd): ping 192.168.1.47
- слушается ли порт сервера АТС на машине где стоит тарификатор: telnet 192.168.1.47 137 
- на машине где стоит сервер АТС преверьте ваш порт: netstat -a | find /I "LISTENING" 
З.Ы. Серёжа Шв., главное вышли мне лог файлы что б знать где ошибка, я конечно могу сделать автоотправку этих логов себе на емейл, но это будет не правильно с точки зрения безопасности :).
