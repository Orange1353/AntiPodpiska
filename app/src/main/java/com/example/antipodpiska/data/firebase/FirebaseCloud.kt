package com.example.antipodpiska.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseCloud {
/*

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    fun addUser(email: String, password: String, nickname: String) = Completable.create { emitter ->

        val userItem = HashMap<String, String>()
        userItem.put("email", email)
        userItem.put("password", password)
        userItem.put("nickname", nickname)


        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(userItem)

      /*  firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }*/
    }

    fun addUser(email: String, password: String, nickname: String, phone: String) = Completable.create { emitter ->

        val userItem = HashMap<String, String>()

        userItem.put("email", email)
        userItem.put("password", password)
        userItem.put("nickname", nickname)
        userItem.put("phone", phone)

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set("iii")


    }
*/
}