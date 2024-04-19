package com.dictionary.config;

import com.dictionary.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MySecurityUser extends User {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String fullname;
    private String emailaddress;
    private LocalDate birthdate;


    public MySecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                          String firstName, String lastName, String emailAddress, LocalDate birthdate) {
        super(username, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullname = firstName + " " + lastName;
        this.emailaddress = emailAddress;
        this.birthdate = birthdate;
    }

    public static MySecurityUser fromUserDto(UserDto userDto) {
        List<GrantedAuthority> authorities = userDto.roles().stream()
                .map(roleDto -> (GrantedAuthority) () -> "ROLE_" + roleDto.role())
                .collect(Collectors.toList());

        // Pass placeholder values or empty strings for password and birthdate
        return new MySecurityUser(userDto.username(), "", authorities,
                userDto.firstName(), userDto.lastName(), userDto.emailAddress(), LocalDate.now());
    }


    @Override
    public String toString() {
        return "MySecurityUser firstName=" + firstName + ", lastName=" + lastName + ", name=" + fullname + ", emailaddress=" + emailaddress + ", birthdate=" + birthdate
                + "] " + super.toString();
    }

}
