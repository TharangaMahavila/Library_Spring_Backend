package lk.ins.library.api;

import lk.ins.library.business.custom.impl.WSService;
import lk.ins.library.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-05-12
 **/
@RestController
@RequestMapping("/api/v1/messages")
public class WSController {
    @Autowired
    private WSService service;

    @PostMapping("/send-common-message")
    public void sendMessage(@RequestBody final Message message) {
        service.notifyFrontendCommon(message.getMessageContent());
    }
}
