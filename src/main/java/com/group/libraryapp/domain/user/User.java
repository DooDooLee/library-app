package com.group.libraryapp.domain.user;


import jakarta.persistence.*;
import org.hibernate.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    protected User(){} //ORM을 하기위해서는 빈 생성자를 만들어 줘야한다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20) // name varChar(20)
    private String name;

    private Integer age; // @Column은 만약 자바의 엔티티 멤버랑 DB상의 컬럼의 속성이 같으면 생략해도 된다.

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();


  public User(String name, Integer age) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
    }
    this.name = name;
    this.age = age;
  }

  public void updateName(String name){
      this.name = name;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public Long getId() {
    return id;
  }



  public void returnBook(String bookName){
      UserLoanHistory targetHistory = this.userLoanHistories.stream()
              .filter(history->history.getBookName().equals(bookName))
              .findFirst()
              .orElseThrow(IllegalArgumentException::new);
      targetHistory.doReturn();
  }
  public void loanBook(String bookName){
    this.userLoanHistories.add(new UserLoanHistory(this,bookName));
  } //객체지향적으로 해보다(다대일 관계를 사용)



}
