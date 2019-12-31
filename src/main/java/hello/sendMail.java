package  hello;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class sendMail implements  Runnable{

    private String interviewerName;
    private String interviewrEmail;
    private Date interviewerTime;
    private String resumeExt="";
    private String resumeUrl="";
    private final static String enterpriseEmail="chn520039379@163.com";
    private final static String enterpriseEmailPassword="chn1998";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "smtp.163.com";
    private final static String PORT="25";
    private final static String IS_AUTH="true";
    private static String IS_ENABLED_DEBUG_MOD = "true";
    private static Properties props=null;
    private Session session;
    private MimeMessage message;
    private MimeMultipart mailContent;

    //private static String sender = "chn520039379@163.com";//chn520039379@163
    //private static String receiver= "520039379@qq.com";  //520039379@qq

    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }

    public sendMail(String interviewerName, String interviewrEmail, Date interviewerTime)  {
        this.interviewerName=interviewerName;
        this.interviewrEmail=interviewrEmail;
        this.interviewerTime=interviewerTime;
    }

    public static boolean mailLegitimate(String email)
    {
        String regEx1 = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(email);
        if(email.isEmpty()||!m.matches()) {
            return false;
        }
        else {
            return true;
        }
    }

    public void setResumePath(String resumeUrl,String fileExt)
    {
        this.resumeExt=fileExt;
        this.resumeUrl=resumeUrl;
    }

    public void emailInitialization()throws MessagingException
    {
        session=Session.getDefaultInstance(props);
        message=new MimeMessage(session);
        message.setFrom(new InternetAddress(enterpriseEmail));
        message.setSubject("58赶集集团面试邀请函-"+interviewerName);
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(this.interviewrEmail));
        message.setSentDate(new Date());
        mailContent=new MimeMultipart("related");
    }

    public void carbonCopy(String carbonCopyName,String carbonCopyEmail) throws Exception
    {
        message.setRecipient(Message.RecipientType.CC,new InternetAddress(carbonCopyEmail,carbonCopyName,"utf-8"));
    }

    public void emailContentText(String s)
    {
        MimeBodyPart attach_text=new MimeBodyPart();
        try {
            attach_text.setText(s);
            mailContent.addBodyPart(attach_text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void emailContentPicture() throws MessagingException {

        MimeBodyPart html = new MimeBodyPart();
        Object sendMessage="<body>&emsp;您好：<strong>";
        sendMessage+=interviewerName;
        sendMessage+="</strong><br><br>";
        Object ss=sendMessage+ "&emsp;&emsp;感谢您对<strong>58赶集集团</strong>的信任与支持，很期待与您的面谈沟通。<br><br>&emsp;&emsp;以下为面试相关信息，请您了解，如果时间有问题可以邮件回复我您可以参加面试的时间即可。<br><br>&emsp;&emsp;面试信息：<br><br>&emsp;&emsp;<strong>时间：</strong>"+setTime()+ "（请从101楼的南门进入，到前台机器人登记面试，填写完应聘登记表后电话联系我去接您）<br><br>&emsp;&emsp;<strong>地点：</strong>北京市朝阳区酒仙桥北路甲10号院101号楼（58赶集集团总部西楼）<br><br>&emsp;&emsp;如果您需要其他帮助，请及时与我们取得联系，联系方式请见下：<br><br>&emsp;&emsp;<strong>联系人：</strong>张月&emsp;&emsp;&emsp;<strong>手机号码：</strong>18601939149&emsp;&emsp;&emsp;<strong>邮箱：</strong>zhangyue@58.com<br><br>&emsp;&emsp;<strong>备份联系人：</strong> 杨全平&emsp;&emsp;&emsp;<strong>手机号码：</strong>18514253428&emsp;&emsp;&emsp;<strong>邮箱：</strong>yangquanping@5ganji.com<strong><br><br>&emsp;乘车路线：</strong><br><br><strong>1、地铁线路</strong><br><br>1） 工作日在地铁13号望京西站下车B口出，出站后即可以看到58集团logo的班车（8:30-10:00），乘班车即可到公司；<br><br> 2） 工作日在地铁10号线亮马桥站C口出，出站后即可以看到58集团logo的班车（8:30-10:00），乘班车即可到公司；<br><br>3） 工作日或者周末在地铁14号线将台站下车A口出， 出站口可看到印有58集团Logo的大巴车（8:30-全天），乘通勤车可到公司。<br><br><strong>2、公交车：</strong>403、593环形铁路站下车即到电子城.IT产业园北门，步行进入园区即可在右侧看到58赶集集团大楼。<br><br><strong>3、自驾车：</strong>朝阳区酒仙桥北路甲10号院101号楼（电子城.IT产业园内），可使用百度地区搜索58赶集集团总部。<br><br></body>";

        html.setContent(ss+"<img src='cid:123456'><br>","text/html;charset=UTF-8");

        MimeBodyPart picture=new MimeBodyPart();
        DataHandler dh=new DataHandler(new FileDataSource("src\\image\\a.png"));
        picture.setDataHandler(dh);
        picture.setContentID("123456");

        mailContent.addBodyPart(html);
        mailContent.addBodyPart(picture);
    }

//    public void emailContentFile() throws Exception
//    {
//        MimeBodyPart attachFile=new MimeBodyPart();
//        DataSource ds=new FileDataSource("D:\\招聘.docx");
//        DataHandler dh = new DataHandler(ds);
//        attachFile.setDataHandler(dh);
//        attachFile.setFileName(MimeUtility.encodeText("招聘.docx"));
//        mailContent.addBodyPart(attachFile);
//    }

    public void emailContentFile() throws Exception {
        if (!resumeUrl.isEmpty()) {
            URL url = new URL(resumeUrl);
            MimeBodyPart attachFile = new MimeBodyPart();
            DataSource ds = new URLDataSource(url);
            DataHandler dh = new DataHandler(ds);
            attachFile.setDataHandler(dh);
            attachFile.setFileName(MimeUtility.encodeText(interviewerName + "." + resumeExt));
            mailContent.addBodyPart(attachFile);
        }
    }

    public void sendEmail() throws Exception
    {
        message.setContent(mailContent);
        message.saveChanges();
        Transport transport=session.getTransport();
        transport.connect(enterpriseEmail,enterpriseEmailPassword);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    @Override
    public void run() {
        try {
            sendEmail();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Object setTime()
    {
        Object formate_time="<strong>";
        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd (E) HH:mm");
        String time=ft.format(interviewerTime);
        String time2="";
        for(int a=0;a<time.length();a++) {
            if (time.charAt(a) != '星' && time.charAt(a) != '期') {
                time2 += time.charAt(a);
            }
            if (time.charAt(a) == '期') {
                time2 += '周';
            }
        }
        formate_time+=time2;
        formate_time += "</strong>";

        return formate_time;
    }
}

