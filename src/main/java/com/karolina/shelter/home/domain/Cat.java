package com.karolina.shelter.home.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

@Document
public class Cat {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;
    private String name;
    private CoatColor coatColor;
    private CoatType coatType;
    private Age age;
    private boolean requiresTreatment;
    private boolean isUsesCuvette;
    private String timestamp;
    
    {
        timestamp =ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).format(RFC_1123_DATE_TIME);
    }
    
    public Cat() {
    }

    public Cat(String name, CoatColor coatColor, CoatType coatType, Age age, boolean requiresTreatment, boolean isUsesCuvette) {
        this.name = name;
        this.coatColor = coatColor;
        this.coatType = coatType;
        this.age = age;
        this.requiresTreatment = requiresTreatment;
        this.isUsesCuvette = isUsesCuvette;
    }
    public Cat(long id, String name, CoatColor coatColor, CoatType coatType, Age age, boolean requiresTreatment, boolean isUsesCuvette) {
        this(name, coatColor, coatType, age, requiresTreatment, isUsesCuvette);
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoatColor getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(CoatColor coatColor) {
        this.coatColor = coatColor;
    }

    public CoatType getCoatType() {
        return coatType;
    }

    public void setCoatType(CoatType coatType) {
        this.coatType = coatType;
    }

    public boolean isRequiresTreatment() {
        return requiresTreatment;
    }

    public void setRequiresTreatment(boolean requiresTreatment) {
        this.requiresTreatment = requiresTreatment;
    }

    public boolean isUsesCuvette() {
        return isUsesCuvette;
    }

    public void setUsesCuvette(boolean usesCuvette) {
        isUsesCuvette = usesCuvette;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public static String getSequenceName() {
        return SEQUENCE_NAME;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", coatColor=" + coatColor +
                ", coatType=" + coatType +
                ", age=" + age +
                ", requiresTreatment=" + requiresTreatment +
                ", isUsesCuvette=" + isUsesCuvette +
                ", timestamp=" + timestamp +
                '}';
    }
}
