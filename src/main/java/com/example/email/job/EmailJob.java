package com.example.email.job;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailJob extends QuartzJobBean {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Scheduler scheduler;
    private String sender = "";
    JobExecutionContext jobExecutionContext;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = jobDataMap.getString("subject");
        String to = jobDataMap.getString("to");
        String product = jobDataMap.getString("product");
        String price = jobDataMap.getString("price");
        String creation = jobDataMap.getString("creation");
        sendMail(to, subject, product, price, creation);
    }

    private void sendMail(String toEmail, String subject, String product, String price, String creation) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            String body = HtmlContent(product, price, creation);
            messageHelper.setText(body, true);
            messageHelper.setFrom(sender);
            messageHelper.setTo(toEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            scheduleRetryJob(jobExecutionContext);
        }
    }

    private void scheduleRetryJob(JobExecutionContext jobExecutionContext) {
        try {
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            String to = jobDataMap.getString("to");
            String subject = jobDataMap.getString("subject");
            String product = jobDataMap.getString("product");
            String price = jobDataMap.getString("price");
            String creation = jobDataMap.getString("creation");

            // Define the retry job
            JobDetail retryJob = JobBuilder.newJob(EmailJob.class)
                    .withIdentity("emailRetryJob_" + System.currentTimeMillis()) // Unique identity for the retry job
                    .usingJobData("to", to)
                    .usingJobData("subject", subject)
                    .usingJobData("product", product)
                    .usingJobData("price", price)
                    .usingJobData("creation", creation)
                    .build();

            Trigger retryTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("emailRetryTrigger_" + System.currentTimeMillis())
                    .startNow()
                    .build();

            scheduler.scheduleJob(retryJob, retryTrigger);

        } catch (SchedulerException ex) {
            ex.printStackTrace();

        }

    }


    private String HtmlContent(String product, String price, String creation) {
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
                + "<p>Dear Customer " + ",</p>"
                + "<p>Created at: " + creation + "</p>"
                + "<p>Order item: " + product + "</p>"
                + "<p>Price: " + price + "</p>"
                + "<p class='thank-you'>Thanks for your order!</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        return emailContent;
    }
}