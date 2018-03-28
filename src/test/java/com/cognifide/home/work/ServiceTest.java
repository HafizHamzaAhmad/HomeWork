package com.cognifide.home.work;

import com.cognifide.home.work.model.BookUIRecord;
import com.cognifide.home.work.service.AuthorApiService;
import com.cognifide.home.work.service.BookApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private AuthorApiService authorApiService;

    @Autowired
    private BookApiService bookApiService;

    @Test
    public void authorRatingsTest() {
        assertThat(authorApiService.authorRatings().size()).isEqualTo(15);
    }



    @Test
    public void bookSearchTest2() {
        BookUIRecord bookUIRecord=new BookUIRecord("9780763715014","A Laboratory Course in Java",null,
                "Jones & Bartlett Learning",978303600L,
                "CS1/C101 Introduction to Programming in Java Programming in Java Object-Oriented Programming.",
                315,"http://books.google.com/books/content?id=FqRxixxvLwIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "en","http://books.google.pl/books?id=FqRxixxvLwIC&printsec=frontcover&dq=java&hl=&cd=11&source=gbs_api",
                0.0, Arrays.asList("Nell B. Dale"),Arrays.asList("Computers"));


        assertThat(bookApiService.getBookByIsbn("9780763715014")).isEqualTo(bookUIRecord);

    }

}
