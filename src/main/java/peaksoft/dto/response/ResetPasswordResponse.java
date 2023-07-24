package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Role;

@Builder
public record ResetPasswordResponse(Long userId,
                                    String firstName,
                                    String lastName,
                                    String email,
                                    Role role,
                                    String jwtToken,
                                    String message) {
}