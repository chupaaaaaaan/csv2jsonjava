package chupaaaaaaan.csv2jsonjava;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonPropertyOrder({"userId","username","comment"})
public class SampleCsv {

    @JsonProperty
    private String userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String comment;

}
