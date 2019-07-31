package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-configs/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepoImplTest {

    @Autowired
    ApplicationContext ctx;

    @PersistenceContext
    private EntityManager emTest;

    @Resource
    UserRepoImpl userRepo;

    private static Logger logger = LoggerFactory.getLogger(UserRepoImplTest.class);

    @Test
    @Transactional (isolation = Isolation.READ_COMMITTED)
    @Rollback
    public void checkThatCorrectUserWasCreated() {
        String LOGIN_TEST = "test@test.com";
        User user = new User();
        user.setLogin(LOGIN_TEST);
        user.setName("Test name");
        user.setPassword("test");
        user.setAddress("test address");
        try {
            emTest.persist(user);
            emTest.flush();
            User userTest = userRepo.getUserByLogin(LOGIN_TEST);
            logger.debug("userTest = " + userTest);
            Assert.assertNotNull(userTest);
            Assert.assertTrue(user.equals(userTest));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
