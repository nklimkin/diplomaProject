package repository;

import model.HistoryOfVote;

import java.time.LocalDateTime;

public class HistoryOfVoteTestData {

    public static HistoryOfVote vote1 = new HistoryOfVote(100016, UserTestData.user1, LocalDateTime.now()
            , RestaurantTestData.restaurant1, 4);

    public static HistoryOfVote vote2 = new HistoryOfVote(100017, UserTestData.user2, LocalDateTime.now()
            , RestaurantTestData.restaurant2, 5);

    public static HistoryOfVote vote3 = new HistoryOfVote(100018, UserTestData.admin1, LocalDateTime.now()
            , RestaurantTestData.restaurant3, 2);

    public static HistoryOfVote newVote = new HistoryOfVote(100019, UserTestData.user1, LocalDateTime.MAX
            , RestaurantTestData.restaurant1, 3);

    public static HistoryOfVote getNew(){
        return new HistoryOfVote(null, UserTestData.user1, LocalDateTime.MAX,
                RestaurantTestData.restaurant1, 3);
    }

    public static HistoryOfVote getUpdated() {
        HistoryOfVote updated = new HistoryOfVote(vote1);
        updated.setGrade(5);
        updated.setLocalDateTime(LocalDateTime.MAX);
        return updated;
    }

}
