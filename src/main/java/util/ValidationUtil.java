package util;

import model.BaseEntity;
import model.User;
import model.Vote;
import org.springframework.util.Assert;
import to.BaseTo;
import to.UserTo;
import util.Exception.NotFoundException;
import util.Exception.TimeForVoteException;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidationUtil {

    public static void checkNew(BaseEntity be) {
        if (be == null)
            throw new IllegalArgumentException("argument must be not null");
        if (!be.isNew())
            throw new IllegalArgumentException(be + " must have id=null");
    }

    public static void checkUpdated(BaseEntity be) {
        if (be == null)
            throw new IllegalArgumentException("argument must be not null");
        if (be.isNew())
            throw new IllegalArgumentException(be + " is a new entity");
    }

    public static void checkUpdated(BaseTo b) {
        if (b == null)
            throw new IllegalArgumentException("argument must be not null");
        if (b.getId() == null)
            throw new IllegalArgumentException(b + " is a new entity");
    }

    public static <T> T checkNotFoundWithId(T be, int id) {
        if (be == null)
            throw new NotFoundException("Entity with id=" + id + " not found");
        return be;
    }

    public static void checkNotFoundWithId(boolean b, int id) {
        if (!b)
            throw new NotFoundException("Entity with id=" + id + " not found");
    }

    public static <T> T checkNotFountWithSomeAttribute(T be, Object attr) {
        if (be == null)
            throw new NotFoundException("Entity with attribute=" + attr + " not found");
        return be;
    }

    public static <T> T checkNotFound(T be){
        if (be == null)
            throw new NotFoundException("Entity not found");
        return be;
    }

    public static void checkTimeForUpdateVote(Vote vote){
        if (LocalTime.now().compareTo(LocalTime.of(11, 0)) > 0) {
            throw new TimeForVoteException("you can change your vote only before 11:00 AM!");
        }
        if (!vote.getLocalDate().equals(LocalDate.now())) {
            throw  new TimeForVoteException("you can change you vote only before 11:00 AM and only on the same day!");
        }
    }

    public static void assureIdConsistent(BaseEntity b, int id) {
        if (b.getId() != id)
            throw new IllegalArgumentException("user must be with id = " + id);
    }

    public static void assureIdConsistent(BaseTo b, int id) {
        if (b.getId() != id)
            throw new IllegalArgumentException("user must be with id = " + id);
    }

    public static void checkPossibilityOfUpdateVote(Vote vote, int userId) {
        Assert.notNull(vote.getUser().getId(), "user of vote must not have id=null");
        if (vote.getUser().getId() != userId)
            throw new IllegalArgumentException("you cant change foreign vote");
    }
}
