package peaksoft.dto.request;

import jakarta.persistence.Column;
import peaksoft.validation.EmailValid;
import peaksoft.validation.PasswordValid;

public record SignInRequest(
        
        @Column(unique = true)
        @EmailValid
        String email,
        
        @PasswordValid
        String password
) {
}
