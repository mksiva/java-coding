# solutions
solutions to some day to day developer's problems. Multithreading, Executor Service


References: http://java.dzone.com/articles/java-concurrency-%E2%80%93-part-7 

Problem: A download excel link should be able to download an excel with any number of records.(usually the total count would vary from 5000 to 500000 or even more.)

Solution: I have decided to use multithreading concept using ExecutorService framework of java 1.7.

I will create thread pool and giving each thread a task , which needs to generate an excel file.
