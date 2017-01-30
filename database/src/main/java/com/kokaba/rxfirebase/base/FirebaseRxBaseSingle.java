package com.kokaba.rxfirebase.base;

import com.google.firebase.database.DatabaseReference;

import io.reactivex.Single;


public abstract class FirebaseRxBaseSingle<DataModel, RxSource> extends FirebaseRxBase<DataModel, Single<RxSource>> {

    public FirebaseRxBaseSingle(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        super(mReference, dataModelClass);
    }


}
