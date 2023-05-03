package com.sorinbratosin.RTX3080NOTIFICATOR.Service;

import com.sorinbratosin.RTX3080NOTIFICATOR.Database.GPU;
import com.sorinbratosin.RTX3080NOTIFICATOR.Database.GPUDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GPUService {

    private static final double PRICE_TARGET = 4500D;

    @Autowired
    private GPUDao gpuDao;

    @Autowired
    private RTX3080EmailSenderService emailSender;

    public void save(GPU gpu) {
        gpuDao.save(gpu);

        if(gpu.getPrice() < PRICE_TARGET) {
            sendEmail(gpu);
        }
    }

    private void sendEmail(GPU gpu) {
        String to = "sorinbratosin93@gmail.com";
        String subject = "Price dropped below " + PRICE_TARGET + " LEI for the RTX 3080 graphics card";
        String message = "The price on the " + gpu.getName() + " is " + gpu.getPrice() + " LEI. \nCheck the card at the link below: " + "\n" + gpu.getUrl();
        emailSender.sendMail(to,subject,message);
    }


}
