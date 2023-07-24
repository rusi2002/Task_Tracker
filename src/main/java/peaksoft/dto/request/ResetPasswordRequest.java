package peaksoft.dto.request;

import peaksoft.validation.PasswordValid;

public record ResetPasswordRequest(Long userId,
                                   @PasswordValid
                                   String newPassword,
                                   @PasswordValid
                                   String repeatPassword) {
}
