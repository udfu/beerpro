package ch.beerpro.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.*;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeEntry;
import ch.beerpro.domain.models.Notice;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class DetailsViewModel extends ViewModel implements CurrentUser {

    private final MutableLiveData<String> beerId = new MutableLiveData<>();
    private final LiveData<Beer> beer;
    private final LiveData<List<Rating>> ratings;
    private final LiveData<List<Notice>> notices;
    private final LiveData<Wish> wish;
    private final LiveData<FridgeEntry> fridgeEntry;

    private final RatingsRepository ratingsRepository;
    private final LikesRepository likesRepository;
    private final WishlistRepository wishlistRepository;
    private final FridgeRepository fridgeRepository;

    private final MutableLiveData<String> currentUserId;

    public DetailsViewModel() {
        // TODO We should really be injecting these!
        BeersRepository beersRepository = new BeersRepository();
        ratingsRepository = new RatingsRepository();
        likesRepository = new LikesRepository();
        wishlistRepository = new WishlistRepository();
        fridgeRepository = new FridgeRepository();

        currentUserId = new MutableLiveData<>();
        beer = beersRepository.getBeer(beerId);
        wish = wishlistRepository.getMyWishForBeer(currentUserId, getBeer());
        fridgeEntry = fridgeRepository.getMyFrigdeForBeer(currentUserId, getBeer());
        ratings = ratingsRepository.getRatingsForBeer(beerId);
        notices = ratingsRepository.getNoticesForBeer(beerId);
        currentUserId.setValue(getCurrentUser().getUid());
    }

    public LiveData<Beer> getBeer() {
        return beer;
    }

    public LiveData<Wish> getWish() {
        return wish;
    }

    public LiveData<FridgeEntry> getFridgeEntry() {
        return fridgeEntry;
    }

    public LiveData<List<Rating>> getRatings() {
        return ratings;
    }

    public LiveData<List<Rating>> getMyRatings() {
        return ratingsRepository.getMyRatingsForBeer(currentUserId, beerId);
    }

    public LiveData<List<Notice>> getNotices() {
        return notices;
    }

    public void setBeerId(String beerId) {
        this.beerId.setValue(beerId);
    }

    public void toggleLike(Rating rating) {
        likesRepository.toggleLike(rating);
    }

    public Task<Void> toggleItemInWishlist(String itemId) {
        return wishlistRepository.toggleUserWishlistItem(getCurrentUser().getUid(), itemId);
    }

    public Task<Void> toggleBeerInFridge(String itemId) {
        return fridgeRepository.toggleUserFridgeItem(getCurrentUser().getUid(), itemId);
    }
}
