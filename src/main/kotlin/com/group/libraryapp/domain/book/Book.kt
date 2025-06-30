package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Book (

    val name: String,

    @Enumerated(value = EnumType.STRING)
    val type: BookType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init {
        if(name.isBlank()) {
            throw IllegalArgumentException("Name cannot be blank")
        }
    }

    companion object {
        fun fixture(name: String = "책 이름", type: BookType = BookType.COMPUTER, id: Long? = null) : Book {
            return Book(name,type,id)
        }
    }

}
