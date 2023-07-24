package peaksoft.dto.request;

public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password

) {
}
