package esir.dom13.nsoc.management.email;

import org.json.JSONObject;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 16/11/13
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */

/**
 *    Format des données du port sendMail :
 *
 *    {"object":"blablabla","body":"blablabla","receiver":"balbla@bla.ba"}
 *
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "sendMail", type = PortType.MESSAGE)
})
@DictionaryType({
        @DictionaryAttribute(name = "SMTP_HOST_NAME", defaultValue = "smtp.gmail.com", optional = false),
        @DictionaryAttribute(name = "SMTP_HOST_PORT", defaultValue = "465",optional = false),
        @DictionaryAttribute(name = "SMTP_AUTH_USER", defaultValue = "projet.nsoc2013@gmail.com", optional = false),
        @DictionaryAttribute(name = "SMTP_AUTH_PWD", defaultValue = "esir2013", optional = false)
})
@ComponentType
public class SendEmail extends AbstractComponentType {

    private   String SMTP_HOST_NAME = "";
    private   int SMTP_HOST_PORT = 0;
    private   String SMTP_AUTH_USER = "";
    private   String SMTP_AUTH_PWD  = "";

    @Start
    public void start() {
        SMTP_HOST_NAME = getDictionary().get("SMTP_HOST_NAME").toString();
        SMTP_HOST_PORT = Integer.valueOf(getDictionary().get("SMTP_HOST_PORT").toString());
        SMTP_AUTH_USER = getDictionary().get("SMTP_AUTH_USER").toString();
        SMTP_AUTH_PWD = getDictionary().get("SMTP_AUTH_PWD").toString();
        Log.debug("Component ManagementLights_KNX started");
    }

    @Stop
    public void stop() {

        Log.debug("Component ManagementLights_KNX stopped");
    }

    @Update
    public void update() {
        SMTP_HOST_NAME = getDictionary().get("SMTP_HOST_NAME").toString();
        SMTP_HOST_PORT = Integer.valueOf(getDictionary().get("SMTP_HOST_PORT").toString());
        SMTP_AUTH_USER = getDictionary().get("SMTP_AUTH_USER").toString();
        SMTP_AUTH_PWD = getDictionary().get("SMTP_AUTH_PWD").toString();
        Log.debug("Component ManagementLights_KNX updated");
    }

    @Port(name = "sendMail")
    public void sendMail(Object jsonEmail) throws Exception{
        Log.info(jsonEmail.toString());
        JSONObject jMail = new JSONObject(jsonEmail.toString());
        //Todo : condition
        String receiver = jMail.getString("receiver");
        String object =  jMail.getString("object");
        String body = jMail.getString("body");

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        // message subject
        message.setSubject(object);
        // message body
        message.setContent(body, "text/html");

        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(receiver));

        transport.connect
                (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
