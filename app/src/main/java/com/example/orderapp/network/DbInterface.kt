package com.example.orderapp.network

import android.util.Log
import com.example.orderapp.types.Inbox
import com.example.orderapp.types.Menu
import com.example.orderapp.types.Order
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class DbInterface {
    private val db = Firebase.firestore
    //TODO save instances to cache or here

    companion object {
        const val MENU_COLLECTION = "Menu"
        const val MENU_DOCUMENT = "0"
        const val INBOX_COLLECTION = "Inbox"
        const val INBOX_DOCUMENT = "100"
    }

    private inline fun <reified DATA> readDocument(collection: String, documentId: String, crossinline callback: (DATA) -> Unit, tag: String) {
        db.collection(collection)
            .document(documentId)
            .get()
            .addOnSuccessListener { document ->
                document
                    ?.toObject<DATA>()
                    ?.let { data ->
                        Log.d(tag, "DocumentSnapshot data: ${document.data}")
                        callback(data)
                    }
                    ?: Log.d(tag, "No such document")
            }
            .addOnFailureListener { e ->
                Log.d(tag, "get failed with ", e)
            }
    }

    fun readMenu(callback: (Menu) -> Unit) =
        readDocument<Menu>(MENU_COLLECTION, MENU_DOCUMENT, callback, "DbInterface.readMenu()")

    fun readInbox(callback: (Inbox) -> Unit) =
        readDocument<Inbox>(INBOX_COLLECTION, INBOX_DOCUMENT, callback, "DbInterface.readInbox()")

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
        addDocument(INBOX_COLLECTION, INBOX_DOCUMENT, inbox, "DbInterface.addInbox()")

    fun addOrder(order: Order) =
        readInbox { inbox ->
            inbox.orders.add(order)
            addInbox(inbox)
        }

    fun removeOrder(order: Order) =
        readInbox { inbox ->
            inbox.orders.removeIf { o -> o.documentId == order.documentId }
            addInbox(inbox)
        }

}