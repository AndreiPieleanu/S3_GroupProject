package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.configuration.security.isauthenticated.IsAuthenticated;
import s3.algorithalliance.domain.reqresp.user.*;
import s3.algorithalliance.servicelayer.IUserService;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private IUserService userService;
    @GetMapping("{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable(name =
            "userId")int userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @PostMapping
    public ResponseEntity<CreateUserResponse> createNewUser(@RequestBody CreateUserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PutMapping("{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable(name =
            "userId")int userId, @RequestBody UpdateUserRequest request){
        request.setId(userId);
        return ResponseEntity.ok(userService.updateUser(request));
    }
}
