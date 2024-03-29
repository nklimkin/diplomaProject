package util;

import model.Vote;
import to.VoteTo;

public class VoteUtil {

    public static Vote createNewVoteFomToByUserIdAndRestaurantId(VoteTo voteTo) {
        return new Vote(null, null, null, voteTo.getGrade());
    }

    public static Vote createVoteFromVoteTo(VoteTo voteTo) {
        return new Vote(voteTo.getId(), null, null, voteTo.getGrade());
    }
}
