package com.reactivespring.kafkademo.reactivespringkafka.Entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class count implements Serializable {

    private String countId;

    private String count;

    public String getCountId() {
        return countId;
    }

    public void setCountId(String countId) {
        this.countId = countId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public count() {

    }

    public count(String countId, String count) {
        this.countId = countId;
        this.count = count;
    }

    @Override
    public String toString() {
        return count + " " + countId;
    }

}
