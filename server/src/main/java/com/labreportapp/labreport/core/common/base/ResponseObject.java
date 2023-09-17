package com.labreportapp.labreport.core.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class ResponseObject {

    private boolean isSuccess = false;
    private Object data;
    private String message;

    public <T> ResponseObject(T obj) {
        processReponseObject(obj);
    }

    public void processReponseObject(Object obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
        }
    }
}
