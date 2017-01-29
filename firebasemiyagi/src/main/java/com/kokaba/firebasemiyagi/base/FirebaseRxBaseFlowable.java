package com.kokaba.firebasemiyagi.base;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;

import io.reactivex.Flowable;


public abstract class FirebaseRxBaseFlowable<DataModel> extends FirebaseRxBase<DataModel, Flowable<FirebaseEvent<DataModel>>> {


    public FirebaseRxBaseFlowable(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        super(mReference, dataModelClass);
    }


}
