package sgms.ugc.dto;

import jakarta.validation.constraints.NotNull;
import sgms.ugc.enums.Gender;

import java.time.LocalDate;

public record CreateUserWithTelPw(
        @NotNull
        String tel,
        @NotNull
        String password,

        LocalDate birthday,

        @NotNull
        Gender gender
) { }
