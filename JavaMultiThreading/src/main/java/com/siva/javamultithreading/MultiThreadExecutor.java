/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.javamultithreading;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author siva
 */
public class MultiThreadExecutor {

    public MultiThreadExecutor() {
        
    }

    public static void main(String[] args) throws ExecutionException, IOException {
                        
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
      
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        threadPool.shutdown();
    }
}
