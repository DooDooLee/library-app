package com.group.libraryapp.dto.user.request;

import lombok.Getter;

@Getter
public class UserCreateRequest {

  private String name;
  private Integer age;

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
