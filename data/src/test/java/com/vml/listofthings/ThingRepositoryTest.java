package com.vml.listofthings;

import com.toddway.shelf.Shelf;
import com.vml.listofthings.core.things.ThingEntity;
import com.vml.listofthings.data.things.ThingRepositoryImpl;
import com.vml.listofthings.di.TestDataComponent;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;


public class ThingRepositoryTest {

    @Inject ThingRepositoryImpl thingRepository;
    @Inject Shelf shelf;

    @Before
    public void beforeEach() {
        TestDataComponent.Builder.build().inject(this);
        shelf.clear("");
    }

    @Test
    public void testGetThingList() {
        TestSubscriber<ThingEntity[]> subscriber = new TestSubscriber<>();
        thingRepository.getThingList().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        for (ThingEntity[] thingEntities : subscriber.getOnNextEvents()) {
            System.out.println(thingEntities);
        }
    }

    @Test
    public void testGetThing() {
        TestSubscriber<ThingEntity> subscriber = new TestSubscriber<>();
        ThingEntity[] things = thingRepository.getNewThingList().toBlocking().first();
        thingRepository.getThing(things[3].getId()).subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }
}