package repository;

import model.Vote;

import java.time.LocalDateTime;

public class HistoryOfVoteTestData {

    public static final int VOTE_1 = 100012;

    public static final int VOTE_2 = 100013;

    public static final int VOTE_3 = 100014;

    public static final int VOTE_NEW = 100015;

    public static Vote vote1 = new Vote(100012, UserTestData.user1, LocalDateTime.now()
            , RestaurantTestData.restaurant1, 4);

    public static Vote vote2 = new Vote(100013, UserTestData.user2, LocalDateTime.now()
            , RestaurantTestData.restaurant2, 5);

    public static Vote vote3 = new Vote(100014, UserTestData.admin1, LocalDateTime.now()
            , RestaurantTestData.restaurant3, 2);

    public static Vote newVote = new Vote(100015, UserTestData.user1, LocalDateTime.MAX
            , RestaurantTestData.restaurant1, 3);

    public static Vote getNew(){
        return new Vote(null, null, LocalDateTime.MAX,
                null, 3);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(vote1);
        updated.setGrade(5);
        updated.setLocalDateTime(LocalDateTime.MAX);
        return updated;
    }

}
