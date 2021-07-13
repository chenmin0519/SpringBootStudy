package com.sentinel.test.chenmin;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sentinel.test.chenmin.service.TestAaaa;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@SpringBootApplication
public class CmSentinelApplication {

    public static void main(String[] args) {
        ApplicationContext ctx =  (ApplicationContext) SpringApplication.run(CmSentinelApplication.class, args);

    }
    @RestController
    @RequestMapping("/test")
    public class TestController {
        @Autowired
        private TestAaaa testAaaa;

        @GetMapping(value = "/pdf")
        public String pdf() throws Exception {
            return testAaaa.test("这是我");
        }

        @GetMapping(value = "/test")
        @SentinelResource("hhh")
        public String hhh(HttpServletResponse response,String name) throws Exception {
//            name = "hehe";
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment;fileName="+new String(name.getBytes("utf-8"),"ISO-8859-1" )+".docx");
//            TestModel testModel = new TestModel();
//            WordUtil<TestModel> wordUtil = new WordUtil<>();
//            XWPFDocument document = wordUtil.generateWord(response,"hahahha",TestModel.class,testModel);
//            OutputStream out = null;
//            try {
//                out = response.getOutputStream();
//                document.write(out);
//            }catch (Exception e){
//
//            }finally {
//                out.close();
//            }
            return "caonima"+name;
        }
/**
 ./mysqld --initialize --user=mysql --datadir=/home/appyw/data/file/mysql --basedir=/home/appyw/data/file/mysql

 */

    }


}
