package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    private Long id;
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;

    public VendorDTO( final String name, final String vendorUrl) {
        this.name = name;
        this.vendorUrl = vendorUrl;
    }
}
