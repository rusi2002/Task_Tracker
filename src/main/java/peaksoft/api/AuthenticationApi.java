package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ResetPasswordRequest;
import peaksoft.dto.request.SignInRequest;
import peaksoft.dto.request.SignUpRequest;
import peaksoft.dto.response.AuthenticationResponse;
import peaksoft.dto.response.ResetPasswordResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.services.AuthenticationService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Api",description = "API - Handles user authentication and access control")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    @Operation(summary = "SignUp",description = "Register new users")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    @Operation(summary = "SignIn",description = "Only registered users can login")
    public  AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Enables password recovery for forgotten accounts via email verification")
    public SimpleResponse forgotPassword(@RequestParam String email, @RequestParam String link) throws MessagingException {
        return authenticationService.forgotPassword(email, link);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Here you can reset your password")
    public ResetPasswordResponse resetPassword(@RequestBody ResetPasswordRequest passwordRequest) {
        return authenticationService.resetPassword(passwordRequest);
    }

    @PostMapping("/google")
    @Operation(summary = "Google authentication", description = "All users can login with Google")
    public AuthenticationResponse authWithGoogleAccount(@RequestParam String tokenId) throws FirebaseAuthException {
        return authenticationService.authWithGoogle(tokenId);
    }
}
