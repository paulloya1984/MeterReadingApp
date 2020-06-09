package tz.co.ubunifusolutions.screens.models;
import com.google.gson.annotations.SerializedName;

public class SyncResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("message")
    private String msg;

    public SyncResponse(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}
