package io.wisoft.capstonedesign.global.exception.nullcheck;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class NullBoardReplyException extends RuntimeException {
    public NullBoardReplyException() {
    }

    public NullBoardReplyException(String message) {
        super(message);
    }

    public NullBoardReplyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullBoardReplyException(Throwable cause) {
        super(cause);
    }

    public NullBoardReplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
