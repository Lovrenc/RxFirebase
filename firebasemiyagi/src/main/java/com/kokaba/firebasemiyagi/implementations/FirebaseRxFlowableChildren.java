package com.kokaba.firebasemiyagi.implementations;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.kokaba.firebasemiyagi.base.FirebaseEvent;
import com.kokaba.firebasemiyagi.base.FirebaseRxBaseFlowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.MainThreadDisposable;


public class FirebaseRxFlowableChildren<DataModel> extends FirebaseRxBaseFlowable<DataModel> {

    public FirebaseRxFlowableChildren(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        super(mReference, dataModelClass);
    }


    /**
     * Subscribes to value events and returns FirebaseEvents on changes in the database.
     * The function will remove itself from the active firebase listeners
     * on dispise.
     * @param backpressureStrategy what backpressure strategy to use
     * @return
     */
    public Flowable<FirebaseEvent<DataModel>> toRx(BackpressureStrategy backpressureStrategy) {
        Flowable<FirebaseEvent<DataModel>> flowable =  Flowable.create(e -> {

            ChildEventListener listener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    e.onNext(new FirebaseEvent<DataModel>(FirebaseEvent.ADDED, dataSnapshot.getValue(mDataModelClass)));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    e.onNext(new FirebaseEvent<DataModel> (FirebaseEvent.CHANGED, dataSnapshot.getValue(mDataModelClass)));
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    e.onNext(new FirebaseEvent<DataModel> (FirebaseEvent.REMOVED, dataSnapshot.getValue(mDataModelClass)));
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onError(new Exception(databaseError.getMessage()));
                }
            };

            e.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    mReference.removeEventListener(listener);
                }
            });

            mReference.addChildEventListener(listener);
        }, backpressureStrategy);

        return flowable;
    }

    /**
     * Default backpressure strategy is DROP.
     * @return
     */
    @Override
    public Flowable<FirebaseEvent<DataModel>> toRx() {
        return toRx(BackpressureStrategy.DROP);
    }
}
