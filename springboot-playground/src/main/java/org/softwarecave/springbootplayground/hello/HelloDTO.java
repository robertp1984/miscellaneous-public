package org.softwarecave.springbootplayground.hello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloDTO {
    private String message;
    private LocalDateTime date;
}
