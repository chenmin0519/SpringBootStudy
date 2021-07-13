package com.sentinel.test.chenmin.generate;

import com.alibaba.fastjson.JSONObject;
import com.sentinel.test.chenmin.anno.ChenminGetMapping;
import com.sentinel.test.chenmin.anno.ChenminPostMapping;
import com.sentinel.test.chenmin.anno.ChenminProxy;
import com.sentinel.test.chenmin.anno.ChenminRequestParam;
import com.sentinel.test.chenmin.cglib.ChenminClassLoder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateJava {

    private String url = "127.0.0.1";
    private String port = ":8031";
    private String packName = "com.sentinel.test.chenmin.service";

    List<String> classPaths = new ArrayList<String>();

    public static void main(String[] args) throws ClassNotFoundException {
        GenerateJava generateJava = new GenerateJava();
        generateJava.generateClasses(null);
    }
    public void generateClasses(DefaultListableBeanFactory defaultListableBeanFactory) {
        //包名

        String basePack = "";
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = GenerateJava.class.getResource("/").getPath();
        //然后把我们的包名basPach转换为路径名
        basePack = packName.replace(".", File.separator);
        //然后把classpath和basePack合并
        String searchPath = classpath + basePack;
        doPath(new File(searchPath));
        int count = 1;//计数器
        //这个时候我们已经得到了指定包下所有的类的绝对路径了。我们现在利用这些绝对路径和java的反射机制得到他们的类对象
        for (String s : classPaths) {
            //把 D:\work\code\20170401\search-class\target\classes\com\baibin\search\a\A.class 这样的绝对路径转换为全类名com.baibin.search.a.A
            s = s.replace(classpath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
            ClassModel classModel = new ClassModel();
            try {
                Class cls = Class.forName(s);
                classModel.setClassName("Chenmin$Proxy"+count);
                classModel.setPackName(packName);
                classModel.setInterfaceName(cls.getName());
                classModel.setInterfaceSimpleName(cls.getSimpleName());
                classModel.setClaz(cls);
                String classByte = generateClass(classModel);
                loadClass(defaultListableBeanFactory,classByte,classModel);
            }catch (ClassNotFoundException e){
                continue;
            }
        }
    }

    /**
     * 加载进内存
     * @param defaultListableBeanFactory
     * @param classByte
     */
    private void loadClass(DefaultListableBeanFactory defaultListableBeanFactory, String classByte,ClassModel classModel) {
        ChenminClassLoder loader = ChenminClassLoder.getInstrance();
        loader.registerJava(classModel.getClassName(), classByte);
        try {
            Class testClass = loader.findClass(classModel.getPackName() + ".impl." + classModel.getClassName());
            Object obj = testClass.newInstance();
            String temp = classModel.getInterfaceSimpleName().substring(0).toLowerCase();
            defaultListableBeanFactory.registerSingleton(temp + classModel.getInterfaceSimpleName().substring(1,classModel.getInterfaceSimpleName().length()), obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 生成class
     * @param classModel
     */
    private String generateClass(ClassModel classModel) {
        String classStr = "";
        System.out.println("加载到class："+classModel.getClaz());
        ChenminProxy anno = classModel.getClaz().getAnnotation(ChenminProxy.class);
        classStr += generateHeard(classModel);
        if(anno != null){
            Method[] methods = classModel.getClaz().getMethods();
            if(methods != null) {
                for (Method method : methods) {
                    MethodModel methodModel = new MethodModel();
                    ChenminPostMapping postAnno = method.getAnnotation(ChenminPostMapping.class);
                    if (postAnno != null) {
                        methodModel.setRequestType("post");
                        methodModel.setUrl(anno.name()+postAnno.value());
                    }else {
                        ChenminGetMapping getAnno = method.getAnnotation(ChenminGetMapping.class);
                        if (getAnno != null) {
                            methodModel.setRequestType("get");
                            methodModel.setUrl(anno.name()+getAnno.value());
                        }
                    }
                    Type t = method.getAnnotatedReturnType().getType();
                    if (t != null) {
                        methodModel.setResultType(t.getTypeName());
                    }else{
                        methodModel.setResultType(" void ");
                    }
                    methodModel.setParamsTypes(method.getParameterTypes());
                    methodModel.setParameters(method.getParameters());
                    methodModel.setParameterAnnotations(method.getParameterAnnotations());
                    methodModel.setParameterCount(method.getParameterCount());
                    methodModel.setMethodName(method.getName());
                    classStr += generateMethod(methodModel);
                }
            }
        }
        return classStr + "}";
    }

    /**
     * 生成方法文件
     * @return
     */
    private String generateMethod(MethodModel methodModel) {
        String classStr = " @Override \n";
        classStr += "public ";
        classStr += methodModel.getResultType() + " ";
        classStr += methodModel.getMethodName() +"(";
        Map<String,Type> params = new HashMap<>();
        //参数
        if(methodModel.getParameterCount() > 0){
            for(int i = 0 ; i < methodModel.getParameterCount() ; i ++ ){
                classStr += methodModel.getParamsTypes()[i].getName() + " ";
                String paramName = "";
                if(methodModel.getParameterAnnotations()[i].length > 0){
                    for(Annotation annotation : methodModel.getParameterAnnotations()[i]){
                        if (annotation instanceof ChenminRequestParam) {
                            ChenminRequestParam chenminRequestParam = (ChenminRequestParam) annotation;
                            if(chenminRequestParam.value() != null && !"".equals(chenminRequestParam.value())) {
                                paramName = chenminRequestParam.value();
                            }
                        }
                    }
                }
                if(!"".equals(paramName)){
                    classStr += paramName +",";
                    params.put(paramName,methodModel.getParamsTypes()[i]);
                }else{
                    classStr +=  methodModel.getParameters()[i].getName() +",";
                    params.put(methodModel.getParameters()[i].getName(),methodModel.getParamsTypes()[i]);
                }
            }
            classStr = classStr.substring(0,classStr.length() - 1);
        }
        classStr += ") throws java.lang.Exception {\n";
        if("post".equals(methodModel.getRequestType())){
            classStr += postMethod(methodModel,params);
        }else{
            //get
            classStr += getMethod(methodModel,params);
        }
        return classStr + "}";
    }

    /**
     * get
     * @param methodModel
     * @param params
     * @return
     */
    private String getMethod(MethodModel methodModel, Map<String, Type> params) {
        String classStr = "";
        String param = "";
        String getUrl = url + port + methodModel.getUrl();
        if(params != null){
            getUrl += "?";
            for(String  key : params.keySet()){
                //暂时不处理对象参数 TODO
                getUrl += key+"=%s&";
                param += key + ",";
            }
            getUrl = getUrl.substring(0,getUrl.length()-1);
            param = param.substring(0,param.length()-1);
        }
        classStr += "org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();\n";
        classStr += methodModel.getResultType() + " result = " +
                "restTemplate.getForObject(java.lang.String.format(\"http://"+getUrl+"\", "+param+")," +
               methodModel.getResultType() +  ".class);\n";
        classStr += "return result;\n";
        return classStr;
    }

    /**
     * post
     * @param methodModel
     * @return
     */
    private String postMethod(MethodModel methodModel, Map<String,Type> params) {
        String classStr = "";
        classStr += "java.util.Map <java.lang.String,java.lang.String> f = new java.util.HashMap<>();\n";
        if(params != null){
            for(String  key : params.keySet()){
                //暂时不处理对象参数 TODO
                classStr += "f.put("+key+","+key+");\n";
            }
        }
        classStr += "org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();\n";
        classStr += "org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();\n";
        classStr += "headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);\n";
        classStr += "org.springframework.http.HttpEntity<java.lang.String> request = new org.springframework.http.HttpEntity<>(com.alibaba.fastjson.JSONObject.toJSONString(f), headers);\n";
        classStr += "org.springframework.http.ResponseEntity<java.lang.String> response = restTemplate.postForEntity( http://"+url + port + methodModel.getUrl()+", request , java.lang.String.class );\n";
        classStr += methodModel.getResultType() + " payReturn = com.alibaba.fastjson.JSONObject.parseObject(response.getBody(),"+methodModel.getResultType()+".class);\n";
        classStr += "return payReturn;\n";
        return classStr;
    }

    /**
     * 生成文件头部
     * @return
     */
    private String generateHeard(ClassModel classModel) {
        String classStr = "package " + classModel.getPackName() + ".impl;\n";
        classStr += "import "+classModel.getInterfaceName()+";\n";
        classStr += "public class ";
        classStr += classModel.getClassName();
        classStr += " implements ";
        classStr += classModel.getInterfaceSimpleName();
        classStr += "{\n";
        return classStr;
    }

    /**
     * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
     * @param file
     */
    private void doPath(File file) {
        if (file.isDirectory()) {//文件夹
            //文件夹我们就递归
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1);
            }
        } else {//标准文件
            //标准文件我们就判断是否是class文件
            if (file.getName().endsWith(".class")) {
                //如果是class文件我们就放入我们的集合中。
                classPaths.add(file.getPath());
            }
        }
    }
}
