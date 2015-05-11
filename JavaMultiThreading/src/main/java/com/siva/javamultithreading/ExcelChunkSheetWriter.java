/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siva.javamultithreading;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author siva 
 */
public class ExcelChunkSheetWriter implements Callable<HSSFWorkbook>{

    private int start;
    private int end;
    
    public ExcelChunkSheetWriter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public ExcelChunkSheetWriter() {
        
    }
    /**
     * Writting a excel workbook
     * @return
     * @throws Exception 
     */
    @Override
    public HSSFWorkbook call() throws Exception {
        System.out.println("Test...");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");
        
        Map<String,Object[]> data =  new HashMap<String,Object[]>();        
        data.put(this.start+"1-"+this.end, new Object[] {"Emp No.", "Name", "Salary"});
        for (int i = this.start; i < this.end; i++) {          
           data.put(i+"2-"+this.end, new Object[] {this.start+1d+this.end, "Siva"+i, 1500000d});
        }
         
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof Date)
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }         
        
        return workbook;
    }
    
}
