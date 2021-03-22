package util;

import model.BaseEntity;
import model.Vote;
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
}
