package Fabio.Reis.Scholarship.model.Commons;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdsList {
    Set<Long> ids= new HashSet<>();
}