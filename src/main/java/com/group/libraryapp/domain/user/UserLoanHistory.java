package com.group.libraryapp.domain.user;

import jakarta.persistence.*;

@Entity
public class UserLoanHistory {

@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;

@ManyToOne
private User user;

private String bookName;

private boolean isReturn;

public UserLoanHistory(User user, String name) {
    this.user = user;
    this.bookName = name;

}


    public String getBookName() {
        return bookName;
    }

    public void doReturn(){
    this.isReturn = true;
}


protected UserLoanHistory() {}

}
