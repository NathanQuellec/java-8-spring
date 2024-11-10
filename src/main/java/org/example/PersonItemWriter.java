package org.example;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PersonItemWriter implements ItemWriter<Person> {

    @Override
    public void write(List list) throws Exception {
        list.forEach(System.out::println);
    }
}
