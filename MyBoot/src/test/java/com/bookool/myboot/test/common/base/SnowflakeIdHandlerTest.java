package com.bookool.myboot.test.common.base;

import com.bookool.myboot.common.base.SnowflakeIdHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnowflakeIdHandlerTest {

    @Test
    public void nextIdTest() {
        Long id = SnowflakeIdHandler.nextId();
        System.out.println(id);
    }

}