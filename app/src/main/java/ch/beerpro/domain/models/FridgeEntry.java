package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FridgeEntry implements Entity {

    public static final String COLLECTION = "fridge";
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_ADDED_AT = "addedAt";
    public static final String AMOUNT_BEER = "amount";


    @Exclude
    private String id;
    @NonNull
    private String userId;
    @NonNull
    private String beerId;

    private int amount;
    @NonNull
    private Date addedAt;

    public FridgeEntry(String userId, String itemId, int i, Date date) {
        this.userId= userId;
        this.beerId = itemId;
        this.amount = i;
        this.addedAt = date;
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * We use a Map instead of an Array to be able to query it.
     *
     * @see <a href="https://firebase.google.com/docs/firestore/solutions/arrays#solution_a_map_of_values"/>
     */



    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }

    public String getBeerId() {
        return beerId;
    }


    public Date getAddedAt() {
        return addedAt;
    }

    @Override
    public String getId() {
        return id;
    }
}
