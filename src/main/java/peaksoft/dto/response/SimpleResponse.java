package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class SimpleResponse {

    HttpStatus status;
    String message;

    public SimpleResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
