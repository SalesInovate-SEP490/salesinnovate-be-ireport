package fpt.capstone.iReport.dto.response;
public class ResponseError extends ResponseData {

    public ResponseError(int code,int status, String message) {
        super(status, message, code);
    }
}
