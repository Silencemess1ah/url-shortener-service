package faang.school.urlshortenerservice;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UrlDto {
    @NotBlank
    private String url;
}
