package com.karolina.shelter.home.repository;

import com.karolina.shelter.home.domain.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class CatModelListener extends AbstractMongoEventListener<Cat> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public CatModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Cat> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Cat.SEQUENCE_NAME));
        }
    }
}
