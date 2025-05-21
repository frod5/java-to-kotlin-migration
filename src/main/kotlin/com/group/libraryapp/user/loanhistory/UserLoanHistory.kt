package com.group.libraryapp.user.loanhistory

import com.group.libraryapp.user.User
import javax.persistence.*

@Entity
class UserLoanHistory (

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    val bookName: String,

    var isReturn: Boolean,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun doReturn() {
        this.isReturn = true
    }
}
