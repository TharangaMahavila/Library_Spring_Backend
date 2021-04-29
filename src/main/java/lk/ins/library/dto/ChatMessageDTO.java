package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-10
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class ChatMessageDTO {

    private String content;
    private String sender;
}
