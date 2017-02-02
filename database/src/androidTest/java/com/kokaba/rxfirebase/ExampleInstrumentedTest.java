package com.kokaba.rxfirebase;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

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
        helper = new FirebaseHelper(FirebaseDatabase.getInstance().getReference());
        TestObserver<Map<String, DataModel>> testObs = helper
            .getValues(DataModel.class, "sample_data")
            .toRx()
            .test();


        testObs.assertNoErrors();
        testObs.awaitDone(5, TimeUnit.SECONDS);
        assertEquals(testObs.values().get(0).keySet().size(), 6);
    }



}
