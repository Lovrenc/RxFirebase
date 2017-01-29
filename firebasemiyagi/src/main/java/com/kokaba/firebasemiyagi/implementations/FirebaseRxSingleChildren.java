package com.kokaba.firebasemiyagi.implementations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kokaba.firebasemiyagi.base.FirebaseRxBaseSingle;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;


public class FirebaseRxSingleChildren<DataModel> extends FirebaseRxBaseSingle<DataModel, List<DataModel>> {


    public FirebaseRxSingleChildren(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        super(mReference, dataModelClass);
    }


    @Override
    public Single<List<DataModel>> toRx() {
        return Single.<List<DataModel>>create(e -> {
            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<DataModel> elements = new ArrayList<>();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for(DataSnapshot data: children) {
                        elements.add(data.getValue(mDataModelClass));
                    }
                    e.onSuccess(elements);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onError(new Exception(databaseError.getMessage()));
                }
            });
        });
    }
}
