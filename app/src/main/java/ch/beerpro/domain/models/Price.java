package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price implements Entity {
    public static final String COLLECTION = "prices";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String userId;
    private float price;
    private Date creationDate;

}
