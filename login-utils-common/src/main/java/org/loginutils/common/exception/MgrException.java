package org.loginutils.common.exception;

import org.loginutils.common.enums.MgrResponseCode;

public class MgrException extends Exception {
    private MgrResponseCode code;

    public MgrException(MgrResponseCode code, Object message, Throwable cause) {
        super(code.getMessage() + " -> " + message, cause);
        this.code = code;
    }

    public MgrException(MgrResponseCode code, Object message) {
        super(code.getMessage() + " -> " + message);
        this.code = code;
    }

    public MgrException(MgrResponseCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.code = code;
    }

    public MgrException(MgrResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public MgrResponseCode getCode() {
        return this.code;
    }
}
