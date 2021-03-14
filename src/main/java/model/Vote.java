package model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class Vote extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime localDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "grade")
    @NotBlank
    private int grade;

    public Vote(Integer id, @NotNull User user, @NotNull LocalDateTime localDateTime, @NotNull Restaurant restaurant, @NotBlank int grade) {
        super(id);
        this.user = user;
        this.localDateTime = localDateTime;
        this.restaurant = restaurant;
        this.grade = grade;
    }

    public Vote() {

    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getUser(), vote.getLocalDateTime(),
                vote.getRestaurant(), vote.getGrade());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "HistoryOfVote{" +
                "user=" + user +
                ", localDateTime=" + localDateTime +
                ", restaurant=" + restaurant +
                ", grade=" + grade +
                '}';
    }
}
