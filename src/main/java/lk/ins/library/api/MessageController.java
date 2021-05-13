package lk.ins.library.api;

import lk.ins.library.dto.Message;
import lk.ins.library.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-05-12
 **/
@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages/common")
    public ResponseMessage getMessage(final Message message) throws InterruptedException {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
