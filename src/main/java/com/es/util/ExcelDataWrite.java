//package com.es.util;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.springframework.util.StringUtils;
//
//import java.util.*;
//
///**
// * Created
// */
//public class ExcelDataWrite {
//    /**
//     * 写数据到excel
//     * @param data  要输入的数据
//     * @param header 表头的中英文字段
//     */
//    public static SXSSFWorkbook writeData( List<Map<String,Object>> data, Map<String,String> header,Sheet sheet,SXSSFWorkbook workbookb) {
//
//        Row row = null;
//        Cell cell;
//        //sheet.createFreezePane(1,0,1,10);//冻结第一行
//        //设置颜色
//        CellStyle style = setExcelStyle(workbookb,sheet);
//        //创建表头
//        Iterator<String> fileFieldsMapIterator = header.keySet().iterator();
//        row = sheet.createRow(0);
//        int n = 0;
//
//        while (fileFieldsMapIterator.hasNext()) {
//            String fileHeader = fileFieldsMapIterator.next();
//            cell = row.createCell(n);
//            cell.setCellStyle(style);
//            cell.setCellValue(header.get(fileHeader));
//            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            sheet.setColumnWidth(n, 6000);
//            n++;
//        }
//        CellStyle errorstyle = workbookb.createCellStyle();
//        errorstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//字体居中
//        errorstyle.setFillBackgroundColor(HSSFColor.DARK_YELLOW.index);//背景颜色
//
//        for (int i = 0; i < data.size(); i++) {
//            Iterator<String> fileFieldsMap = header.keySet().iterator();
//            int m = 0;
//            row = sheet.createRow(i + 1);
//            while (fileFieldsMap.hasNext()) {
//                String fileHeader = fileFieldsMap.next();
//                cell = row.createCell(m);
//                if (fileHeader.equals("错误原因")) {
//                    cell.setCellStyle(errorstyle);
//                }
//                String A = fileHeader;
//                Object B = data.get(i).get(A) == null ? "" : data.get(i).get(A);
//                cell.setCellValue(B.toString());
//                //cell.setCellValue(data.get(i).get(header.get(fileHeader)));
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                sheet.setColumnWidth(m, 6000);
//                m++;
//            }
//
//        }
//        return workbookb;
//    }
//    /**
//     * 写数据到excel
//     * @param data  要输入的数据
//     * @param header 表头的中英文字段
//     */
//    public static SXSSFWorkbook writeSubjectData( List<Map<String,Object>> data, Map<String,String> header,Map<String,Map<String,String>> entityHeader,  Sheet sheet,SXSSFWorkbook workbookb) {
//
//        Row row = null;
//        Cell cell;
//        //sheet.createFreezePane(1,0,1,10);//冻结第一行
//        CellStyle style = setExcelStyle(workbookb,sheet);
//        //创建表头
//        Iterator<String> fileFieldsMapIterator = header.keySet().iterator();
//        row = sheet.createRow(0);
//        int n = 0;
//        while (fileFieldsMapIterator.hasNext()) {
//            String fileHeader = header.get(fileFieldsMapIterator.next());
//            cell = row.createCell(n);
//            cell.setCellStyle(style);
//            cell.setCellValue(fileHeader);
//            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            sheet.setColumnWidth(n, 6000);
//            n++;
//        }
//
//
//        for (int i = 0; i < data.size(); i++) {
//            Iterator<String> fileFieldsMap = header.keySet().iterator();
//            int m = 0;
//            row = sheet.createRow(i + 1);
//            Object entity_id = data.get(i).get("ENTITY_ID");
//            Map<String, String> dicFieldMapping  = entityHeader.get(entity_id);
//            while (fileFieldsMap.hasNext()) {
//                String fileHeader = fileFieldsMap.next();
//                cell = row.createCell(m);
//                String A = dicFieldMapping.get(fileHeader);
//
//                Object B = data.get(i).get(A) == null ? "" : data.get(i).get(A);
//                cell.setCellValue(B.toString());
//                //cell.setCellValue(data.get(i).get(header.get(fileHeader)));
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                sheet.setColumnWidth(m, 6000);
//                m++;
//            }
//
//        }
//        return workbookb;
//    }
//
//
//    /**
//     * 解析用户上传的excel文件
//     * @param workbook
//     * @return
//     */
//    public static List<Set<String>> analysisUploadExcelData(Workbook workbook){
//        //解析的结果集
//        List<Set<String>> datas = new ArrayList<>();
//        //最大的行数
//        Sheet sheet = workbook.getSheetAt(0);
//        int rownum = sheet.getPhysicalNumberOfRows();
//        //如果是0行，则直接返回
//        if (rownum == 0)
//            return datas;
//        rownum = Math.min(rownum,201);
//
//        for (int i = 1; i < rownum; i++) {
//            Row row = sheet.getRow(i);
//            if (row == null){
//                continue;
//            }
//            //每行的数据
//            Set<String> keys = new HashSet<>();
//            //解析excle当前行的前五列数据
//            for (int j = 0; j < 5; j++) {
//                //数据值
//                String  cellValue = getCellFormatValue(row.getCell(j));
//                if ( ! StringUtils.isEmpty(cellValue)){
//                    keys.add(cellValue);
//                }
//            }
//            datas.add(keys);
//        }
//        //返回结果集
//        return datas;
//    }
//
//
//    /**
//     * 解析excelcell 的值，返回字符值
//     * @param cell
//     * @return
//     */
//    public static String getCellFormatValue(Cell cell){
//        String cellValue = "";
//        if (cell != null){
//            switch (cell.getCellType()){
//                //数字类型
//                case Cell.CELL_TYPE_NUMERIC :{
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                //数字或者日期类型
//                case Cell.CELL_TYPE_FORMULA :{
//
//                    if (DateUtil.isCellDateFormatted(cell)){
//                        cellValue = cell.getDateCellValue().toString();
//                    }else{
//                        cellValue = String.valueOf(cell.getNumericCellValue());
//                    }
//                    break;
//                }
//                //字符类型
//                case Cell.CELL_TYPE_STRING : {
//                    cellValue = cell.getRichStringCellValue().getString();
//                    break;
//                }
//                default:
//                    cellValue = "";
//            }
//        }
//        return cellValue;
//    }
//
//
//    public static CellStyle setExcelStyle(SXSSFWorkbook workbook,Sheet sheet){
//        Font font = workbook.createFont();
//        font.setFontHeightInPoints((short) 12);//字体大小
//        font.setFontName("仿宋_GB2312");
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
//        //font.setItalic(true);
//        //font.setColor(HSSFColor.BRIGHT_GREEN.index);
//        //设置样式
//        CellStyle style = workbook.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//字体居中
//        style.setFillBackgroundColor(HSSFColor.DARK_YELLOW.index);//背景颜色
//        style.setFont(font);
//        sheet.createFreezePane(0, 1, 0, 1);//冻结第一行
//        return style;
//    }
//}
