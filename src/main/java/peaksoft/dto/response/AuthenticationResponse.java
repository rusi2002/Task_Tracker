package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.house.tasktrackerb9.enums.Role;

@Builder
public record AuthenticationResponse(
        String token,
        String email,
        Role role

) {
}
