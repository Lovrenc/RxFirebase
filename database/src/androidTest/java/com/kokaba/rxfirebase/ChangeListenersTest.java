package com.kokaba.rxfirebase;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kokaba.rxfirebase.base.FirebaseEvent;
import com.kokaba.rxfirebase.helpers.SampleDataModel;
import com.kokaba.rxfirebase.helpers.TestingConstants;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChangeListenersTest {

    private static FirebaseHelper mHelper;
    private static List<DatabaseReference> cleanupList = new ArrayList<>();

    @BeforeClass
    public static void initialize() {
        FirebaseApp.initializeApp(InstrumentationRegistry.getTargetContext());
        mHelper = new FirebaseHelper(FirebaseDatabase.getInstance().getReference());
    }

    @AfterClass
    public static void cleanup() {
        Observable.fromArray(cleanupList.toArray(new DatabaseReference[cleanupList.size()]))
            .subscribe(new Consumer<DatabaseReference>() {
                @Override
                public void accept(DatabaseReference databaseReference) throws Exception {
                    databaseReference.removeValue();
                }
            });
    }

    private DatabaseReference getNewSubkey() {
        DatabaseReference res = FirebaseDatabase.getInstance().getReference().child(TestingConstants.DYNAMIC_TESTING_DATA_KEY).push();
        cleanupList.add(res);
        return res;
    }


    @Test
    public void shouldGetAddedFirebaseEvent() throws Throwable {
        DatabaseReference subkeyReference = getNewSubkey();
        TestSubscriber<FirebaseEvent<SampleDataModel>> testSub = mHelper.listenToValues(SampleDataModel.class, TestingConstants.DYNAMIC_TESTING_DATA_KEY, subkeyReference.getKey())
            .toRx()
            .test();

        subkeyReference.push().setValue(TestingConstants.SAMPLE_DATA_MODEL);

        testSub.await(3, TimeUnit.SECONDS);
        testSub.assertValue(new Predicate<FirebaseEvent<SampleDataModel>>() {
            @Override
            public boolean test(FirebaseEvent<SampleDataModel> sampleDataModelFirebaseEvent) throws Exception {
                boolean result = true;
                result = sampleDataModelFirebaseEvent.getType() == FirebaseEvent.ADDED;
                result = result && sampleDataModelFirebaseEvent.getValue().equals(TestingConstants.SAMPLE_DATA_MODEL);
                return result;
            }
        });
    }


    @Test
    public void shouldGetRemovedFirebaseEvent() throws Throwable {
        DatabaseReference subkeyReference = getNewSubkey();
        subkeyReference.setValue(TestingConstants.SAMPLE_DATA_MODEL);

        TestSubscriber<FirebaseEvent<SampleDataModel>> testSub = mHelper.listenToValues(SampleDataModel.class, TestingConstants.DYNAMIC_TESTING_DATA_KEY)
            .toRx()
            .test();
        testSub.await(3, TimeUnit.SECONDS);

        subkeyReference.removeValue();
        testSub.await(3, TimeUnit.SECONDS);

        testSub.assertValueAt(testSub.values().size()-1, new Predicate<FirebaseEvent<SampleDataModel>>() {
            @Override
            public boolean test(FirebaseEvent<SampleDataModel> sampleDataModelFirebaseEvent) throws Exception {
                boolean result = sampleDataModelFirebaseEvent.getType() == FirebaseEvent.REMOVED;
                return result && sampleDataModelFirebaseEvent.getValue().equals(TestingConstants.SAMPLE_DATA_MODEL);
            }
        });
    }


    @Test
    public void shouldGetUpdatedFirebaseEvent() throws Throwable {
        DatabaseReference subkeyReference = getNewSubkey();
        subkeyReference.setValue(TestingConstants.SAMPLE_DATA_MODEL);
        TestSubscriber<FirebaseEvent<SampleDataModel>> testSub = mHelper.listenToValues(SampleDataModel.class, TestingConstants.DYNAMIC_TESTING_DATA_KEY)
            .toRx()
            .test();
        testSub.await(3, TimeUnit.SECONDS);

        subkeyReference.setValue(TestingConstants.SAMPLE_DATA_MODEL2);
        testSub.await(3, TimeUnit.SECONDS);

        testSub.assertValueAt(testSub.values().size()-1, new Predicate<FirebaseEvent<SampleDataModel>>() {
            @Override
            public boolean test(FirebaseEvent<SampleDataModel> sampleDataModelFirebaseEvent) throws Exception {
                boolean result = sampleDataModelFirebaseEvent.getType() == FirebaseEvent.CHANGED;
                return result && sampleDataModelFirebaseEvent.getValue().equals(TestingConstants.SAMPLE_DATA_MODEL2);
            }
        });
    }
}
