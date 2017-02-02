package com.kokaba.rxfirebase.implementations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kokaba.rxfirebase.base.FirebaseRxBaseSingle;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;


public class FirebaseRxSingleChildren<DataModel> extends FirebaseRxBaseSingle<DataModel, List<DataModel>> {


    public FirebaseRxSingleChildren(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        super(mReference, dataModelClass);
    }


    @Override
    public Single<List<DataModel>> toRx() {
        return Single.<List<DataModel>>create(
            new SingleOnSubscribe<List<DataModel>>() {
                @Override
                public void subscribe(final SingleEmitter<List<DataModel>> e) throws Exception {
                    mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<DataModel> elements = new ArrayList<>();
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            for (DataSnapshot data : children) {
                                elements.add(data.getValue(mDataModelClass));
                            }
                            e.onSuccess(elements);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            e.onError(new Exception(databaseError.getMessage()));
                        }
                    });
                }
            });
    }
}
