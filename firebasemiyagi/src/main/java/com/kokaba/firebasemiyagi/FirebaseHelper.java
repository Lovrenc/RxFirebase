package com.kokaba.firebasemiyagi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kokaba.firebasemiyagi.implementations.FirebaseRxFlowableChildren;
import com.kokaba.firebasemiyagi.implementations.FirebaseRxSingle;
import com.kokaba.firebasemiyagi.implementations.FirebaseRxSingleChildren;

public class FirebaseHelper {

    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    /**
     * Gives you observable that is listening to values and value changes on given part of firebase
     * database and sends you events.
     * @param dataModelClass Class reference to data model you want to use to cast FB data
     * @param key key of the child you want to listen to
     * @param subKeys additional keys that enable you to further pinpoint the part of database you want to listen to
     * @param <T> Class of the Model
     * @return
     */
    public <T> FirebaseRxFlowableChildren<T> listenToValues(Class<T> dataModelClass, String key, String... subKeys) {
        DatabaseReference reference = mDatabase.child(key);

        for(int i = 0; i < subKeys.length; i++) {
            reference = reference.child(subKeys[i]);
        }

        return new FirebaseRxFlowableChildren<T>(reference, dataModelClass);
    }

    /**
     * SingleValueEventListener that returns you some data and terminates.
     * @param dataModelClass
     * @param key
     * @param subKeys
     * @param <T>
     * @return
     */
    public <T> FirebaseRxSingle<T> getValues(final Class<T> dataModelClass, String key, String... subKeys) {
        DatabaseReference reference = mDatabase.child(key);

        for(int i = 0; i < subKeys.length; i++) {
            reference = reference.child(subKeys[i]);
        }

        return new FirebaseRxSingle<T>(reference, dataModelClass);
    }

    /**
     * SingleValueEventListener that returns you children of the reference you passed and terminates
     * @param dataModelClass
     * @param key
     * @param subKeys
     * @param <T>
     * @return
     */
    public <T> FirebaseRxSingleChildren<T> getChildren(final Class<T> dataModelClass, String key, String... subKeys) {
        DatabaseReference reference = mDatabase.child(key);

        for(int i = 0; i < subKeys.length; i++) {
            reference = reference.child(subKeys[i]);
        }

        return new FirebaseRxSingleChildren<T>(reference, dataModelClass);
    }


}
