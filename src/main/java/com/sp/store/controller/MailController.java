package com.sp.store.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.sp.store.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    private String verifyHtml;
    private String date;
    private String verifyCode;
    private Long timeStamp;
    private void setVerifyHtml(){
        Date experiTime=DateUtil.offsetMinute(DateUtil.date(),1);
        timeStamp=experiTime.getTime();
        date=DateUtil.formatDateTime(experiTime);
        verifyCode= RandomUtil.randomNumbers(6);
        FileReader fileReader = new FileReader("D:\\Project\\store\\src\\main\\resources\\templates\\mail.html");
        verifyHtml = fileReader.readString();
        verifyHtml= StrUtil.format(verifyHtml,verifyCode,date);
    }


    public String sendTestMail(String sender, String receiver, String subject, String text) {
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送方
        mainMessage.setFrom(sender);
        //接收方
        mainMessage.setTo(receiver);
        //发送的标题
        mainMessage.setSubject(subject);
        //发送的内容
        mainMessage.setText(text);
        //发送邮件
        javaMailSender.send(mainMessage);
        return "success";
    }
    @GetMapping("/sendText")
    public String sendText() {
        //这个是接受人的邮箱
        String receiver = "1742378275@qq.com";
        //标题
        String subject = "邮箱标题(主题)";
        //内容
        String text = "这个是邮箱内容";
        //文本邮箱
        return sendTestMail(sender, receiver, subject, text);
    }
    @GetMapping("/sendVerifyCode")
    public Result<?> sendVerifyCode(@RequestParam String receiver) {
        //这个是接受人的邮箱
        //String receiver = "1742378275qq.com";
        //标题
        String subject = "Online Book Store 验证邮件";
        //内容
        setVerifyHtml();
        //文本邮箱
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(verifyHtml,true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            System.out.println("发送邮件失败:" + e.getMessage());
            return Result.error("-1","验证码发送失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("verifyCode", verifyCode);
        jsonObject.set("date",timeStamp);
        return Result.success(jsonObject);
    }
}
