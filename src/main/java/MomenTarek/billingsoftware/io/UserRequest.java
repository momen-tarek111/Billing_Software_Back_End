package MomenTarek.billingsoftware.io;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String role;
}
