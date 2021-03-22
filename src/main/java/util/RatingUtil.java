package util;

import model.Vote;

import java.util.List;

public class RatingUtil {

    public static double countNewRating(double oldRating, int numberOfVote, double newGrade) {
        return (oldRating * numberOfVote + newGrade) / (numberOfVote + 1);
    }

    public static double countTotalRating(List<Vote> votes) {
        if (votes.isEmpty())
            return 0;
        int sumOfGrade = votes.stream().mapToInt(Vote::getGrade).sum();
        return (double) sumOfGrade / votes.size();
    }
}
