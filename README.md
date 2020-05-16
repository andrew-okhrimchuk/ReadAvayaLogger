**Read Avaya Logger**

###### Приложение для прослушки 9000 порта и записи полученных данных в файл.

_Инструкция по установке:_
1. Скачать и разархивировать файл в любой удобный каталог.
2. Проверить наличие установленной Java.
3. Скомпилировать файлы. (не умеете: могу выслать готовые, это сложно, дорого потому и платно ;))
4. **Установить https://www.mongodb.com/download-center/community** .Запустить сервер.
5. Дважды кликнуть мышкой по файлу  nonBlockingEchoServer.JAR

5.1 если не запустился или работает с проблемами, попробуйте запустить классически, как это делаю я в консоли CMD:

"C:\Program Files\Java\jdk1.8.0_144\bin\java.exe" -Dfile.encoding=windows-1251 -jar D:\Stajirovka\ReadAvayaLogger\target\nonBlockingEchoServer.jar

или  
 
java -Dfile.encoding=windows-1251 -jar D:\Stajirovka\ReadAvayaLogger\target\nonBlockingEchoServer.jar

где "C:\Program Files\Java\jdk1.8.0_144\bin\java.exe" - путь к Вашей джаве.(кавычки тоже тут нужны!)

ну а - D:\Stajirovka\ReadAvayaLogger\target\nonBlockingEchoServer.jar (тут без кавычек) соответственно путь к приложению.
остальное просто повторяем как есть. 

6. Добавлена возможность запуска в докер-контейнере. (написал /DOCKER/docker-compose.yml). Запуск всего приложения+база всего одной командой. Смотрим commands.txt

7. Программа работает как в винде так и в линухе (ей поф все равно крутится на VM :)

8. Что в итоге Вы получите: приложение слушает порт 9000 по ТСР-протоколу и пишет каждую строку результата в базу. Посмотреть результат можно по ссылке: **http://localhost:9001/**

9. Приложение запустится в фоновом режиме. Убить программу тогда можно через запуск Диспетчера задач, поиск работающей проги и ее килл. (у меня имя программы выглядит так: "Java(TM) Platform SE binary Oracle Corporation". C:\Program Files\Java\jdk-11.0.5\bin\javaw.exe)

10. Если не коннектится:
- проверяем не блокирует ли фаервол порт - https://vynesimozg.com/kak-blokirovat-ili-razreshit-port-v-brandmauere-windows-10/
ну и добавляем правило на нужный порт.
- если сервера в локальной сети, то проверяем статическую маршрутизацию - https://27sysday.ru/setevoe-oborudovanie/kak-propisat-staticheskij-marshrut-na-routere-tp-link
- слушается ли IP сервера АТС на машине где стоит тарификатор(запуск cmd): ping 192.168.1.47
- слушается ли порт сервера АТС на машине где стоит тарификатор: telnet 192.168.1.47 137 
- на машине где стоит сервер АТС преверьте ваш порт: netstat -a | find /I "LISTENING" 
З.Ы. Серёжа, главное вышли мне лог файлы что б знать где ошибка, я конечно могу сделать автоотправку этих логов себе на емейл, но это будет не правильно с точки зрения безопасности :).


https://javadeveloperzone.com/spring-boot/spring-boot-as-windows-service/
Для запуска приложения как сервиса виндовс:
11. Запустить cmd   
12. cd D:\target\    
13. avaya-logger-server-service.exe install    
14. cmd Services.msc     
15. avaya-logger-server-service.exe uninstall      
        avaya-logger-server-service.exe start   
        avaya-logger-server-service.exe stop
    


