package ch.beerpro.data.repositories;

import android.util.Pair;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Fridge;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class FridgeRepository {


    private static LiveData<List<Fridge>> getFridgeByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Fridge.COLLECTION)
                .orderBy(Fridge.FIELD_ADDED_AT, Query.Direction.DESCENDING).whereEqualTo(Fridge.FIELD_USER_ID, userId),
                Fridge.class);
    }

    /*
    private static LiveData<Wish> getUserWishListFor(Pair<String, Beer> input) {
        String userId = input.first;
        Beer beer = input.second;
        DocumentReference document = FirebaseFirestore.getInstance().collection(Wish.COLLECTION)
                .document(Wish.generateId(userId, beer.getId()));
        return new FirestoreQueryLiveData<>(document, Wish.class);
    }*/
/*
    public void toggleLike(Fridge fridge, Beer beer) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference beerRef = db.collection(Fridge.COLLECTION).document(beer.getId());

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            Rating currentRating = parser.parseSnapshot(transaction.get(ratingRef));
            Map<String, Boolean> likes = currentRating.getLikes();
            String currentUserUid = currentUser.getUid();
            if (likes.containsKey(currentUserUid)) {
                likes.remove(currentUserUid);
            } else {
                likes.put(currentUserUid, true);
            }
            transaction.update(ratingRef, Rating.FIELD_LIKES, likes);
            return null;
        });
    }*/

    public Task<Void> toggleBeerToFridge(String userId, String itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fridgeBeerId = Fridge.generateId(userId, itemId);

        DocumentReference fridgeEntryQuery = db.collection(Fridge.COLLECTION).document(fridgeBeerId);

        return fridgeEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return fridgeEntryQuery.delete();
            } else if (task.isSuccessful()) {
                return fridgeEntryQuery.set(new Fridge(userId, itemId, 1,new Date()));
            } else {
                throw task.getException();
            }
        });
    }

    /*

    public LiveData<List<Pair<Wish, Beer>>> getMyWishlistWithBeers(LiveData<String> currentUserId,
                                                                   LiveData<List<Beer>> allBeers) {
        return map(combineLatest(getMyWishlist(currentUserId), map(allBeers, Entity::entitiesById)), input -> {
            List<Wish> wishes = input.first;
            HashMap<String, Beer> beersById = input.second;

            ArrayList<Pair<Wish, Beer>> result = new ArrayList<>();
            for (Wish wish : wishes) {
                Beer beer = beersById.get(wish.getBeerId());
                result.add(Pair.create(wish, beer));
            }
            return result;
        });
    }

    public LiveData<List<Wish>> getMyWishlist(LiveData<String> currentUserId) {
        return switchMap(currentUserId, FridgeRepository::getWishesByUser);
    }


    public LiveData<Wish> getMyWishForBeer(LiveData<String> currentUserId, LiveData<Beer> beer) {


        return switchMap(combineLatest(currentUserId, beer), FridgeRepository::getUserWishListFor);
    }*/


}
