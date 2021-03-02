package model;

import java.time.LocalDateTime;

public class HistoryOfVote extends BaseEntity{

    private User user;
    private LocalDateTime localDateTime;
    private Restaurant restaurant;
    private int grade;

}
