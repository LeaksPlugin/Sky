package com.talesdev.copsandcrims.arena.data;

/**
 * Cmd Result
 *
 * @author MoKunz
 */
public class CmdResult {
    private String resultMessage;
    private boolean success;

    public CmdResult(String resultMessage) {
        this(resultMessage, true);
    }

    public CmdResult(String resultMessage, boolean success) {
        this.resultMessage = resultMessage;
        this.success = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public boolean isSuccess() {
        return success;
    }
}
