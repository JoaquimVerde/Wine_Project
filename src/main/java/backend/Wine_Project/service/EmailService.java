package backend.Wine_Project.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

        public void sendEmailWithAttachment(String recipientEmail, String attachmentPath, String fileName, String costumerName) {
            try {
                // Create the email
                MultiPartEmail email = new MultiPartEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587); // or your SMTP port
                email.setAuthenticator(new DefaultAuthenticator("joaquim.verde@gmail.com", "bwqa sxyu bkcp lqca"));
                email.setStartTLSRequired(true); // If using TLS
                email.setFrom("Wine_Project@gmail.com");
                email.addTo(recipientEmail);
                email.setSubject("Order Invoice: "+ fileName);
                email.setMsg("Dear "+costumerName+",\n\n\n I hope this email finds you well.\n\n Sending your order invoice in the attachment.\n\n\n" +
                                "Kind Regards,\n" +
                                "Wine Team.");

                // Create the attachment
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(attachmentPath);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("PDF Attachment");
                attachment.setName(fileName);

                // Attach the file
                email.attach(attachment);

                // Send the email
                email.send();
                System.out.println("Email sent successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

