package model;

import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Entity
@NamedEntityGraph(name = "voteWithUserAndRestaurant",
        attributeNodes = {@NamedAttributeNode(value = "restaurant"),
            @NamedAttributeNode(value = "user", subgraph = "user.roles")},
        subgraphs = @NamedSubgraph(name = "user.roles", attributeNodes = @NamedAttributeNode(value = "roles")))
@Table(name = "history_of_vote")
public class Vote extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "date")
    @NotNull
    private LocalDate localDate;

    @Column(name = "time")
    @NotNull
    private LocalTime localTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "grade")
    @NotNull
    private int grade;

    public Vote(Integer id, @NotNull User user, @NotNull LocalDate localDate, @NotNull LocalTime localTime, @NotNull Restaurant restaurant, @NotBlank int grade) {
        super(id);
        this.user = user;
        this.localDate = localDate;
        this.localTime = localTime;
        this.restaurant = restaurant;
        this.grade = grade;
    }

    public Vote() {

    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getUser(), vote.getLocalDate(), vote.getLocalTime(),
                vote.getRestaurant(), vote.getGrade());
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Vote{" +
                "localDate=" + localDate +
                ", localTime=" + localTime +
                ", grade=" + grade +
                '}';
    }
}
