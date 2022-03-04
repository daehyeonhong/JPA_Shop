package book.shop.service;

import book.shop.domain.item.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;

@SpringBootTest
public class ItemUpdateTests {
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName(value = "영속성 업데이트 테스트")
    public void updateTest() throws Exception {
        //given
        final Book book = entityManager.find(Book.class, 1L);
        //TX
        book.setName("asdasd");
        //변경 감지 == Dirty Checking
        //TX Commit
        //when

        //then
    }
}
