package com.kokaba.rxfirebase.implementations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kokaba.rxfirebase.base.FirebaseRxBaseSingle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Single;

public class FirebaseRxSingle<DataModel> extends FirebaseRxBaseSingle<DataModel, Map<String, DataModel>> {


    public FirebaseRxSingle(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        super(mReference, dataModelClass);
    }


    @Override
    public Single<Map<String, DataModel>> toRx() {
        return Single.create(e -> mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    HashMap<String, DataModel> result = new HashMap<String, DataModel>();

                    while (iterator.hasNext()) {
                        DataSnapshot fbChild = iterator.next();
                        result.put(fbChild.getKey(), fbChild.getValue(mDataModelClass));
                    }
                    e.onSuccess(result);
                } catch (Exception ex) {
                    e.onError(ex);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                e.onError(new Exception(databaseError.getMessage()));
            }
        }));
    }
}
