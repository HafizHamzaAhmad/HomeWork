package com.cognifide.home.work;

import com.cognifide.home.work.model.AuthorUIModel;
import com.cognifide.home.work.model.BookUIRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void givenRating()  {

        List<AuthorUIModel> authorUIModels = restTemplate.getForObject("/api/rating", List.class);

        assertThat(authorUIModels.size()).isEqualTo(15);

    }


    @Test
    public void bookTest2()  {

        BookUIRecord bookUIRecord=new BookUIRecord("9780763715014","A Laboratory Course in Java",null,
                "Jones & Bartlett Learning",978303600L,
                "CS1/C101 Introduction to Programming in Java Programming in Java Object-Oriented Programming.",
                315,"http://books.google.com/books/content?id=FqRxixxvLwIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "en","http://books.google.pl/books?id=FqRxixxvLwIC&printsec=frontcover&dq=java&hl=&cd=11&source=gbs_api",
                0.0, Arrays.asList("Nell B. Dale"),Arrays.asList("Computers"));


        BookUIRecord object = restTemplate.getForObject("/api/book/9780763715014", BookUIRecord.class);



        assertThat(object).isEqualTo(bookUIRecord);

    }
}
