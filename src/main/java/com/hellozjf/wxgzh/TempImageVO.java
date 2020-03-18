package com.hellozjf.wxgzh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TempImageVO {

    @JsonProperty("type")
    private String type;

    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("item")
    private Object item;
}
