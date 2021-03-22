package testData;

import model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteTestData {

    public static int VOTE_1 = 100006;
    public static int VOTE_2 = 100007;
    public static int VOTE_3 = 100008;
    public static int VOTE_NEW = 100014;
    public static int NEW_VOTE_FOR_REST = 100014;

    public static Vote vote1 = new Vote(100006, UserTestData.user1, LocalDate.now(), LocalTime.now(), RestaurantTestData.restaurant1, 4);
    public static Vote vote2 = new Vote(100007, UserTestData.user2, LocalDate.now(), LocalTime.now(), RestaurantTestData.restaurant2, 5);

    public static Vote newVote = new Vote(100014, UserTestData.user1, LocalDate.now(), LocalTime.now(), RestaurantTestData.restaurant1, 1);
    public static Vote updatedVoteWithoutChangingRestaurant = new Vote(100006, UserTestData.user1, LocalDate.now(), LocalTime.now(), RestaurantTestData.restaurant1, 1);
    public static Vote updatedVoteWithChangingRestaurant = new Vote(100006, UserTestData.user1, LocalDate.now(), LocalTime.now(), RestaurantTestData.restaurant2, 1);

    public static Vote getNew() {
         return new Vote(null, null, LocalDate.now(), LocalTime.now(), null, 1);
    }

    public static Vote getUpdatedDontChangeRestaurant() {
        Vote updated = new Vote(vote1);
        updated.setGrade(1);
        return updated;
    }

    public static Vote getUpdatedChangeRestaurant() {
        Vote updated = new Vote(vote1);
        updated.setGrade(1);
        updated.setRestaurant(RestaurantTestData.restaurant2);
        return updated;
    }


}
