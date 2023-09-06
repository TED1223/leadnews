package com.peng.freemarker.controller;

import com.peng.freemarker.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 14:53
 * @description:
 */
@Controller
public class DemoController {

    @GetMapping("/demo1")
    public String demo1(Model model){
        model.addAttribute("name","demo1");

        Student student = new Student();
        student.setName("小民");
        student.setAge(12);
        model.addAttribute("stu",student);

        return "01-basic";
    }

    @GetMapping("/list")
    public String list(Model model){

        //------------------------------------
        Student stu1 = new Student();
        stu1.setName("小强");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());

        //小红对象模型数据
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);

        //将两个对象模型数据存放到List集合中
        List<Student> studentList = new ArrayList<>();
        studentList.add(stu1);
        studentList.add(stu2);

        //向model中存放List集合数据
        model.addAttribute("stuList",studentList);

        //------------------------------------

        //创建Map数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        // 3.1 向model中存放Map数据
        model.addAttribute("stuMap", stuMap);

        return "02-list";
    }
    @GetMapping("/operation")
    public String operation(Model model){
        Date date = new Date();
        model.addAttribute("date1",date);
        model.addAttribute("date2",date);
        return "03-operation";
    }

    @GetMapping("innerFunc")
    public String testInnerFunc(Model model) {
        //1.1 小强对象模型数据
        Student stu1 = new Student();
        stu1.setName("小强");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        //1.2 小红对象模型数据
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        //1.3 将两个对象模型数据存放到List集合中
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        model.addAttribute("stus", stus);
        // 2.1 添加日期
        Date date = new Date();
        model.addAttribute("today", date);
        // 3.1 添加数值
        model.addAttribute("point", 102920122);
        return "04-innerFunc";
    }

}
