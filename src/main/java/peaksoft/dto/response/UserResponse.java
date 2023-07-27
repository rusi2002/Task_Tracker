package peaksoft.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserResponse {

    private Long memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String image;

}