# solutions
solutions to some day to day developer's problems. Multithreading, Executor Service


References: http://java.dzone.com/articles/java-concurrency-%E2%80%93-part-7 

Problem: A download excel link should be able to download an excel with any number of records.(usually the total count would vary from 5000 to 500000 or even more.)

Solution: I have decided to use multithreading concept using ExecutorService framework of java 1.7.

I will create thread pool and giving each thread a task , which needs to generate an excel file.


# I am trying to make non-modular java application work in java 11 with the help of jlink and jdeps.

jlink --no-header-files --no-man-pages --add-modules java.base,java.datatransfer,java.desktop,java.logging,java.management,java.naming,java.prefs,java.scripting,java.sql,java.sql.rowset,	java.transaction.xa,java.xml,jdk.jsobject,jdk.unsupported,jdk.unsupported.desktop,jdk.xml.dom,jdk.localedata --output java-runtime
	
java --module-path C:\\javafx-sdk-11.0.2\lib --add-modules=javafx.controls -jar myjar.jar

Jdeps --module-path C:\\javafx-sdk-11.0.2\lib --add-modules=javafx.controls  myjar.jar

Jdeps --module-path C:\javafx-sdk-11.0.2\lib --print-module-deps --add-modules=javafx.controls  myjar.jar
java.base,java.desktop,java.prefs,java.sql

# To create runtime

jlink --no-header-files --no-man-pages --add-modules java.base,java.desktop,java.prefs,java.sql --output java-runtime

jdeps --module-path C:\javafx-sdk-11.0.2\lib --add-modules=javafx.controls --list-deps myjar.jar lib/*


java-runtime\bin\java  -Duser.language=en-US --module-path C:\tools\javafx-sdk-11.0.2\lib --add-modules=javafx.controls,javafx.fxml -jar myjar.jar


jdeps --module-path C:\tools\javafx-sdk-11.0.2\lib --add-modules=ALL-MODULE-PATH --generate-module-info out controlsfx-8.40.12.jar

jdeps --add-modules=ALL-MODULE-PATH --generate-module-info out controlsfx-8.40.12.jar

jdeps --module-path C:\tools\javafx-sdk-11.0.2\lib --add-modules=ALL-MODULE-PATH --print-module-deps controlsfx-8.40.12.jar
