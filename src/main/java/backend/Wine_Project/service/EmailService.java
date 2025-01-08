package backend.Wine_Project.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

        public void sendEmailWithAttachment(String recipientEmail, String attachmentPath, String fileName, String costumerName) {
            try {

                MultiPartEmail email = new MultiPartEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(587);
                email.setAuthenticator(new DefaultAuthenticator("joaquim.verde@gmail.com", "bwqa sxyu bkcp lqca"));
                email.setStartTLSRequired(true);
                email.setFrom("Wine_Project@gmail.com");
                email.addTo(recipientEmail);
                email.setSubject("Order Invoice: " + fileName);
                email.setMsg("Dear " + costumerName + ",\n\n\n I hope this email finds you well.\n\n Sending your order invoice in the attachment.\n\n\n" +
                        "Kind Regards,\n" +
                        "Wine Team.");

                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(attachmentPath);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("PDF Attachment");
                attachment.setName(fileName);
                email.attach(attachment);


                email.send();
                System.out.println("Email sent successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            public void sendEmailRequestPayment(String recipientEmail, String costumerName, String items, double totalPrice,Long orderNumber) {
                try {

                    MultiPartEmail email = new MultiPartEmail();
                    email.setHostName("smtp.gmail.com");
                    email.setSmtpPort(587);
                    email.setAuthenticator(new DefaultAuthenticator("joaquim.verde@gmail.com", "bwqa sxyu bkcp lqca"));
                    email.setStartTLSRequired(true);
                    email.setFrom("Wine_Project@gmail.com");
                    email.addTo(recipientEmail);
                    email.setSubject("Order number: "+ orderNumber + " placed.");
                    email.setMsg("Dear "+costumerName+",\n\n\n I hope this email finds you well.\n\n You have placed an order for the following items:\n" + items
                            + "\n Please proceed with payment, as to the following details:\n\n" + "[details of payment]\n"+"Total Amount: "+totalPrice+"\n\n"+
                            "Kind Regards,\n" +
                            "Wine Team.");


                    email.send();
                    System.out.println("Email sent successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
}

