package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemDTO {



    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "solution is required")
    private String solution;

    @NotBlank(message = "category is required")
    private Integer categoryId;

    @NotBlank(message = "user is required")
    private Integer userId;
}
