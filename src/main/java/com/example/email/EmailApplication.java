package com.example.email;

import com.example.email.service.EmailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailApplication {

    @Autowired
    private EmailServiceImp emailSender;

    public static void main(String[] args) {
   SpringApplication.run(EmailApplication.class, args);
  /*      try {
            ApplicationContext context = SpringApplication.run(EmailApplication.class, args);
            EmailSenderImp emailSender = context.getBean(EmailSenderImp.class);
//tarksame7@gmail.com
            // impl for customer
            String customerName="customerName";
            int customerId=1005;
            String creation="11/3/2024";
            String order="product 1";
            int price=50;
          /*   String subject="Hello "+customername+"\n Your order id is : "+customerId+"\n created at : "+creation
                     +"\n order item : "+order+"\n price : "+price+
                     "\n                                                                                                                                                  Thanks for you!";


            String emailContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; }"
                    + ".email-container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f4f4f4; }"
                    + ".header { background-color: #4CAF50; color: white; text-align: center; padding: 10px; }"
                    + ".content { padding: 20px; }"
                    + ".thank-you { font-size: 18px; font-weight: bold; color: #4CAF50; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='email-container'>"
                    + "<div class='header'><h2>Order Confirmation</h2></div>"
                    + "<div class='content'>"
                    + "<p>Hello " + customerName + ",</p>"
                    + "<p>Your order ID is: " + customerId + "</p>"
                    + "<p>Created at: " + creation + "</p>"
                    + "<p>Order item: " + order + "</p>"
                    + "<p>Price: " + price + "</p>"
                    + "<p class='thank-you'>Thanks for your order!</p>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            Email mail = new Email();
            mail.setFrom("esraamabrouk126@gmail.com");
           // mail.setTo("esraamabrouk321@gmail.com");
            mail.setTo("tarksame7@gmail.com");
            mail.setSubject("Test");
            mail.setContent(emailContent);
            emailSender.sendEmail(mail);
            System.out.println("Mail send successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    */
}}