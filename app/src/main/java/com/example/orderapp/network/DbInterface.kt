package com.example.orderapp.network

import android.util.Log
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Product
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class DbInterface {
    private val db = Firebase.firestore

    fun <T> fetchNestedObjects(docRefList: List<DocumentReference>) {
        // Assuming docRefList is a list of DocumentReference objects

        // Fetch all documents referenced by DocumentReference objects concurrently
        val tasks = docRefList.map { it.get() }

        // Wait for all tasks to complete
        Tasks.whenAllSuccess<QuerySnapshot>(tasks)
            .addOnSuccessListener { querySnapshots ->
                // Process each QuerySnapshot to extract nested objects
                for (querySnapshot in querySnapshots) {
                    for (document in querySnapshot.documents) {
                        // Parse each document to nested object
                        val nestedObject = document.toObject<Product>()
                        // Do something with nestedObject
                    }
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors
            }
    }

    fun readMenu(callback: (Menu) -> Unit) {
        db.collection("Menu")
            .document(DbSchema.Fire.MENU_ID)
            .get()
            .addOnSuccessListener { document ->
//                document?.toObject<Menu>()?.let(callback) ?:
//                    Log.d("readMenu()", "No such document")
                if (document != null) {
                    Log.d("readMenu()", "DocumentSnapshot data: ${document.data}")
                    val q = document.toObject<Menu>()
                    if (q is Menu)
                        callback(q)
                    else
                        Log.d("readMenu()", "cannot be parsed ")
                } else {
                    Log.d("readMenu()", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("readMenu()", "get failed with ", exception)
            }
    }

    fun addMenu(menu: Menu) {
        db.collection("Menu")
            .document(menu.documentId)
            .set(menu)
    }
}