package digital.store.api.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto<T> {

    private int status;
    private T body;
    private String error;

    public ResponseDto(int status, T body) {
        this.status = status;
        this.body = body;
        this.error = null;
    }

    public ResponseDto(int status, String error) {
        this.status = status;
        this.body = null;
        this.error = error;
    }

}
