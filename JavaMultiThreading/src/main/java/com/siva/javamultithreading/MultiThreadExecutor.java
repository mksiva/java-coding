/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.javamultithreading;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author siva
 */
public class MultiThreadExecutor {

    public MultiThreadExecutor() {

    }

    public static void main(String[] args) throws ExecutionException, IOException {

        //Populate the data
        List<DomainObject> list = new ArrayList<>();
        DomainObject object = null;
        for (int i = 0; i < 230000; i++) {
            object = new DomainObject();
            object.setId("ID" + i);
            object.setName("NAME" + i);
            object.setComment("COMMENT" + i);
            list.add(object);
        }

        int maxNoOfRows = 40000;        
        int noOfThreads = 1;
        int remaining = 0;
        
        if (list.size() > 40000) {
            noOfThreads = list.size() / maxNoOfRows;
            remaining = list.size() % maxNoOfRows;
            if (remaining > 0) {
                noOfThreads++;
            }
        }

        List<List<DomainObject>> dos = ListUtils.partition(list, maxNoOfRows);
        
        ExecutorService threadPool = Executors.newFixedThreadPool(noOfThreads);
        CompletionService<HSSFWorkbook> pool = new ExecutorCompletionService<>(threadPool);
        
        // Excel creation through multiple threads
        long startTime = System.currentTimeMillis();

        for (List<DomainObject> listObj : dos) {            
            pool.submit(new ExcelChunkSheetWriter(listObj));
        }
        
        HSSFWorkbook hSSFWorkbook = null;
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("Report");

        try {
            for (int i = 0; i < 5; i++) {
                hSSFWorkbook = pool.take().get();
                System.out.println("sheet row count : sheet.PhysicalNumberOfRows() = " + sheet.getPhysicalNumberOfRows());
                int currentCount = sheet.getPhysicalNumberOfRows();
                int incomingCount = hSSFWorkbook.getSheetAt(0).getPhysicalNumberOfRows();
                if ((currentCount + incomingCount) > 60000) {
                    sheet = book.createSheet("Report" + i);
                }
                ExcelUtil.copySheets(book, sheet, hSSFWorkbook.getSheetAt(0));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MultiThreadExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MultiThreadExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            writeFile(book, new FileOutputStream("Report.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("No of Threads : " + noOfThreads + " Size : " + list.size() + " remaining : " + remaining);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        threadPool.shutdown();
        
        //startProcess();
    }

    /**
     * This is sample.
     */
    private static void startProcess() {

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        CompletionService<HSSFWorkbook> pool = new ExecutorCompletionService<>(threadPool);
        // Excel creation through multiple threads
        long startTime = System.currentTimeMillis();
        pool.submit(new ExcelChunkSheetWriter(0, 1000));
        pool.submit(new ExcelChunkSheetWriter(1001, 20000));
        pool.submit(new ExcelChunkSheetWriter(2, 3000));
        pool.submit(new ExcelChunkSheetWriter(3, 40000));
        pool.submit(new ExcelChunkSheetWriter(4, 50000));

        HSSFWorkbook hSSFWorkbook = null;
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("Report");

        try {
            for (int i = 0; i < 5; i++) {
                hSSFWorkbook = pool.take().get();
                System.out.println("sheet row count : sheet.PhysicalNumberOfRows() = " + sheet.getPhysicalNumberOfRows());
                int currentCount = sheet.getPhysicalNumberOfRows();
                int incomingCount = hSSFWorkbook.getSheetAt(0).getPhysicalNumberOfRows();
                if ((currentCount + incomingCount) > 60000) {
                    sheet = book.createSheet("Report" + i);
                }
                ExcelUtil.copySheets(book, sheet, hSSFWorkbook.getSheetAt(0));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MultiThreadExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MultiThreadExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            writeFile(book, new FileOutputStream("Report.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         FileOutputStream fos = new FileOutputStream("all.zip");
         ZipOutputStream zos = new ZipOutputStream(fos);
         for (int i = 0; i < 5; i++) {
         try {
         hSSFWorkbook = pool.take().get();                
         ZipEntry ze = new ZipEntry("Excel" + i + ".xls");
         zos.putNextEntry(ze);
         hSSFWorkbook.write(zos);
         zos.closeEntry();
         } catch (InterruptedException ex) {
         Logger.getLogger(MultiThreadExecutor.class.getName()).log(Level.SEVERE, null, ex);
         }           
         }
         zos.close();
         */
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        threadPool.shutdown();
    }

    protected static void writeFile(HSSFWorkbook book, FileOutputStream out) throws Exception {
        // FileOutputStream out = new FileOutputStream(file);
        book.write(out);
        out.close();
    }
}
