package com.coderhglee.batch.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoDto {

    private Object id;
    private String kind;
    private String etag;
    private Object snippet;

}
