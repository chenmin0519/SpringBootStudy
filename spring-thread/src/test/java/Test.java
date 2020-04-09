import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args){


        File file = new File( "main//resources//img//222a.png");
        ITesseract instance = new Tesseract();

        /**
         *  获取项目根路径，例如： D:\IDEAWorkSpace\tess4J
         */
//        File directory = new File("tessdata");
//        String lagnguagePath = null;
//        try {
//            lagnguagePath = directory.getCanonicalPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //设置训练库的位置
        instance.setDatapath("tessdata");

        instance.setLanguage("eng");//chi_sim ：简体中文， eng	根据需求选择语言库
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result =  instance.doOCR(file);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        System.out.println("result: ");
        System.out.println(result);
    }

}
