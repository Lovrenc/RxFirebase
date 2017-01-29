package com.kokaba.firebasemiyagi.base;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;


public abstract class FirebaseRxBase<DataModel, RxSource>  {

    //Reference to the 'database/part of database' on which we operate and all the modifiers (sorts, filters)
    protected Query mReference;

    //Class that describes data objects our Firebase queries will return
    protected Class<DataModel> mDataModelClass;


    public FirebaseRxBase(DatabaseReference mReference, Class<DataModel> dataModelClass) {
        this.mReference = mReference;
        this.mDataModelClass = dataModelClass;
    }

    /**
     * Gives you Rx source that will emit firebase data/events
     * @return
     */
    public abstract RxSource toRx();

    /**
     * Order data by key.
     * @return
     */
    public FirebaseRxBase<DataModel, RxSource> orderByKey() {
        mReference = this.mReference.orderByKey();
        return this;
    }

    /**
     * Orders data set (ASC) by values in child field.
     * @param key to child propery
     * @return
     */
    public FirebaseRxBase<DataModel, RxSource> orderByChild(String key) {
        mReference = this.mReference.orderByChild(key);
        return this;
    }

    /**
     * Limits data set returned. Will return the data that comes after given key.
     * IMPORTANT: To use this you HAVE to first order the data set.
     * @param key - relative to what you ordered by (be it key, child value, ...)
     * @return
     */
    public FirebaseRxBase<DataModel, RxSource> startAt(String key) {
        mReference = this.mReference.startAt(key);
        return this;
    }

    /**
     * Limits data set returned and will not return anything after given key.
     * IMPORTANT: Make sure you order the data set first.
     * @param key - relative to what you ordered by (be it key, child value, ...)
     * @return
     */
    public FirebaseRxBase<DataModel, RxSource> endAt(String key) {
        mReference = this.mReference.endAt(key);
        return this;
    }

    /**
     * Limits data returned to last N (noElements) objects
     * @param noElements
     * @return
     */
    public FirebaseRxBase<DataModel, RxSource> limitToLast(int noElements) {
        mReference = this.mReference.limitToLast(noElements);
        return this;
    }


    /**
     * Limits data returned to first N (noElements) objects
     * @param noElements
     * @return
     */
    public FirebaseRxBase<DataModel, RxSource> limitToFirst(int noElements) {
        mReference = this.mReference.limitToFirst(noElements);
        return this;
    }
}
