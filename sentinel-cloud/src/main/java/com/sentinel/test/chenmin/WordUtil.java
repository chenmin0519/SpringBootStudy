//package com.sentinel.test.chenmin;
//
//import com.sentinel.test.chenmin.anno.WordTable;
//import org.apache.poi.xwpf.usermodel.*;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.util.*;
//
//public class WordUtil<T> {
//
//
//    public XWPFDocument generateWord(HttpServletResponse response, String title,Class<T> claz,T data) throws Exception {
//        Map<Integer,List<WordModel>> tableName = getTableAttributes(data);
//        // new 一个空白文档
//        XWPFDocument document = new XWPFDocument();
//        //添加标题
//        XWPFParagraph titleParagraph = document.createParagraph();
//        //设置段落居中
//        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
//        XWPFRun titleParagraphRun = titleParagraph.createRun();
//        titleParagraphRun.setText(title);
////        titleParagraphRun.setColor("000000");
//        titleParagraphRun.setFontSize(20);
//        //换行
//        XWPFParagraph paragraph1 = document.createParagraph();
//        XWPFRun paragraphRun1 = paragraph1.createRun();
//        paragraphRun1.setText("\r");
//        // 创建表
//        XWPFTable table = document.createTable();
//        // 创建第一行
//        Map<Integer,List<WordModel>> map = getTableAttributes(data);
//        List<WordModel> data0 =map.get(1);
//        XWPFTableRow tableRowOne = table.createRow();
//        for(int i = 0 ; i < data0.size() ; i ++){
//            if(i == 0){
//                tableRowOne.getCell(i).setText(data0.get(i).getName());
//            }else{
//                tableRowOne.addNewTableCell().setText(data0.get(i).getName());
//            }
//            tableRowOne.addNewTableCell().setText(data0.get(i).getValue());
//        }
//        for(Integer key : map.keySet()){
//            if(key == 1){
//                continue;
//            }
//            for(int i = 0 ; i < map.get(key).size() ; i ++){
//                XWPFTableRow tableRow = table.createRow();
//                if(data0.get(i).getMerge()){
//                    for(int j = 1 ; j <= data0.get(i).getMergeNum().length ; j ++){
//                        mergeCellsHorizontal(table,key-1,data0.get(i).getMergeNum()[0],data0.get(i).getMergeNum()[0]+1);
//                    }
//                }else{
//
//                }
//            }
//        }
//
//        // new 一个空白文档
//        XWPFDocument document = new XWPFDocument();
//        // 创建表
//        XWPFTable table = document.createTable();
//
//        table.setWidth(1000);
//
//        // 创建第一行
//        XWPFTableRow tableRowOne = table.getRow(0);
//
//        tableRowOne.getCell(0).setText("1 x 1");
//        tableRowOne.addNewTableCell().setText("2 x 1");
//        tableRowOne.addNewTableCell().setText("3 x 1");
//
//        // 创建第二行
//        XWPFTableRow tableRowTwo = table.createRow();
//
//        tableRowTwo.getCell(0).setText("1 x 2");
//        tableRowTwo.getCell(1).setText("2 x 2");
//        tableRowTwo.getCell(2).setText("3 x 2");
//
//        // 创建第三行
//        XWPFTableRow tableRowThree = table.createRow();
//
//        tableRowThree.getCell(0).setText("1 x 3");
//        tableRowThree.getCell(1).setText("2 x 3");
//        tableRowThree.getCell(2).setText("3 x 3");
//
//
//        return document;
//    }
//
//    public Map<Integer,List<WordModel>> getTableAttributes(T data) throws IllegalAccessException {
//        Map<Integer,List<WordModel>> result = new TreeMap<>();
//        Class claz = data.getClass();
//        Field[] fields = claz.getDeclaredFields();
//        for(Field field : fields){
//            WordTable wordTable = field.getAnnotation(WordTable.class);
//            if(wordTable != null){
//                List<WordModel> list = result.get(wordTable.row());
//                if(result.get(wordTable.row()) == null){
//                    list = new ArrayList<>();
//                }
//                WordModel wordModel = new WordModel();
//                wordModel.setRow(wordTable.row());
//                wordModel.setErect(wordTable.erect());
//                wordModel.setMerge(wordTable.merge());
//                wordModel.setMergeNum(wordTable.mergeNum());
//                wordModel.setName(wordTable.name());
//                wordModel.setValue(Optional.ofNullable(field.get(data)).orElse(new Object()).toString());
//                list.add(wordModel);
//            }
//        }
//        return result;
//    }
//
//    // word跨列合并单元格
//    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
//        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
//            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
//            if ( cellIndex == fromCell ) {
//                // The first merged cell is set with RESTART merge value
//                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
//            } else {
//                // Cells which join (merge) the first one, are set with CONTINUE
//                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
//            }
//        }
//    }
//    // word跨行并单元格
//    public void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
//        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
//            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
//            if ( rowIndex == fromRow ) {
//                // The first merged cell is set with RESTART merge value
//                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
//            } else {
//                // Cells which join (merge) the first one, are set with CONTINUE
//                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
//            }
//        }
//    }
//}
