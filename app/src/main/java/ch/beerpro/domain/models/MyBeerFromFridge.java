package ch.beerpro.domain.models;

import java.util.Date;

import lombok.Data;

@Data
public class MyBeerFromFridge implements MyBeer {

    private FridgeEntry fridgeEntry;
    private Beer beer;

    public MyBeerFromFridge(FridgeEntry fridgeEntry, Beer beer) {
        this.fridgeEntry = fridgeEntry;
        this.beer = beer;
    }


    @Override
    public String getBeerId() { return fridgeEntry.getBeerId(); }

    @Override
    public Date getDate() {
        return fridgeEntry.getAddedAt();
    }

    @Override
    public Beer getBeer() {
        return beer;
    }
}
