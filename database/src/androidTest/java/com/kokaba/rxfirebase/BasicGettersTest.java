package com.kokaba.rxfirebase;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.kokaba.rxfirebase.base.FirebaseEvent;
import com.kokaba.rxfirebase.helpers.SampleDataModel;
import com.kokaba.rxfirebase.helpers.TestingConstants;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class BasicGettersTest {

    static FirebaseHelper helper;
    static FirebaseApp firebaseApp;

    @BeforeClass
    public static void beforeAll() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        firebaseApp = FirebaseApp.initializeApp(appContext);
        helper = new FirebaseHelper(FirebaseDatabase.getInstance(firebaseApp).getReference());
    }

    @Test
    public void getValuesFromDatabase() throws Exception {
        TestObserver<Map<String, SampleDataModel>> testObs = helper
            .getValues(SampleDataModel.class, TestingConstants.FIXED_TESTING_DATA_KEY)
            .toRx()
            .test();

        testObs.awaitDone(5, TimeUnit.SECONDS);
        assertEquals(testObs.values().get(0).keySet().size(), TestingConstants.NUMBER_OF_ELEMENTS);
    }

    @Test
    public void getChildrenFromDatabase() throws Exception {
        TestObserver<List<SampleDataModel>> testObs = helper
            .getChildren(SampleDataModel.class, TestingConstants.FIXED_TESTING_DATA_KEY)
            .toRx()
            .test()
            .awaitDone(5, TimeUnit.SECONDS);

        testObs.assertTerminated();
        assertEquals(testObs.values().get(0).size(), TestingConstants. NUMBER_OF_ELEMENTS);
    }

    @Test
    public void getValuesByListeningToChanges() throws Exception {
        TestSubscriber<FirebaseEvent<SampleDataModel>> testObs = helper
            .listenToValues(SampleDataModel.class, TestingConstants.FIXED_TESTING_DATA_KEY)
            .toRx()
            .test()
            .awaitDone(5, TimeUnit.SECONDS);


        testObs.assertNotTerminated();
        testObs.assertValueAt(0, new Predicate<FirebaseEvent<SampleDataModel>>() {
            @Override
            public boolean test(FirebaseEvent<SampleDataModel> sampleDataModelFirebaseEvent) throws Exception {
                return sampleDataModelFirebaseEvent.getType() == FirebaseEvent.ADDED;
            }
        });
    }

}
