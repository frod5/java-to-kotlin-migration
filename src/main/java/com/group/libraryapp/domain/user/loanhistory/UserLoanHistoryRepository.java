package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.libraryapp.user.loanhistory.UserLoanHistory;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

  UserLoanHistory findByBookNameAndIsReturn(String bookName, boolean isReturn);

}
