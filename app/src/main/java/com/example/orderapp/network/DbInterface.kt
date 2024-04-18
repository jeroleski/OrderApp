package com.example.orderapp.network

import android.util.Log
import com.example.orderapp.types.Inbox
import com.example.orderapp.types.Menu
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class DbInterface {
    private val db = Firebase.firestore

    companion object {
        const val MENU_COLLECTION = "Menu"
        const val MENU_DOCUMENT = "0"
        const val INBOX_COLLECTION = "Inbox"
        const val INBOX_DOCUMENT = "100"
    }

//    fun readMenu(callback: (Menu) -> Unit) {
//        val tag = "DbInterface.readMenu()"
//        db.collection(MENU_COLLECTION)
//            .document(MENU_DOCUMENT)
//            .get()
//            .addOnSuccessListener { document ->
//                document
//                    ?.toObject<Menu>()
//                    ?.let { menu -> callback(menu) }
//                    ?: Log.d(tag, "No such document")
//            }
//            .addOnFailureListener { e ->
//                Log.d(tag, "get failed with ", e)
//            }
//    }

    private inline fun <reified DATA> readDocument(collection: String, documentId: String, crossinline callback: (DATA) -> Unit, tag: String) {
        db.collection(collection)
            .document(documentId)
            .get()
            .addOnSuccessListener { document ->
                document
                    ?.toObject<DATA>()
                    ?.let { data -> callback(data) }
                    ?: Log.d(tag, "No such document")
            }
            .addOnFailureListener { e ->
                Log.d(tag, "get failed with ", e)
            }
    }

    fun readMenu(callback: (Menu) -> Unit) =
        readDocument<Menu>(MENU_COLLECTION, MENU_DOCUMENT, callback, "DbInterface.readMenu()")

    private fun addDocument(collection: String, documentId: String, data: Any, tag: String) {
        db.collection(collection)
            .document(documentId)
            .set(data)
            .addOnSuccessListener {
                Log.d(tag, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Error writing document", e)
            }
    }

    fun addMenu(menu: Menu) =
        addDocument(MENU_COLLECTION, MENU_DOCUMENT, menu, "DbInterface.addMenu()")

    fun addInbox(inbox: Inbox) =
        addDocument(INBOX_COLLECTION, INBOX_DOCUMENT, inbox, "DbInterface.addIndox()")
}