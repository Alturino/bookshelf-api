package com.example.learnspringboot.book.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;

public class FindBookDto {
    private String name;
    @Min(0)
    @Max(1)
    @Null
    private Integer reading;
    @Min(0)
    @Max(1)
    @Null
    private Integer finished;

    public FindBookDto(String name) {
        this.name = name;
    }

    public FindBookDto(Integer reading) {
        this.reading = reading;
    }


    public FindBookDto(String name, Integer reading, Integer finished) {
        this.name = name;
        this.reading = reading;
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public static class Builder {
        private String name;
        private Integer reading;
        private Integer finished;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setReading(Integer reading) {
            this.reading = reading;
            return this;
        }

        public Builder setFinished(Integer finished) {
            this.finished = finished;
            return this;
        }

        public FindBookDto createFindBookDto() {
            return new FindBookDto(name, reading, finished);
        }
    }
}
