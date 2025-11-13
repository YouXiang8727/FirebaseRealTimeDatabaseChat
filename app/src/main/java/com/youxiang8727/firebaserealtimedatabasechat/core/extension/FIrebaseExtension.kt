package com.youxiang8727.firebaserealtimedatabasechat.core.extension

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun DatabaseReference.runTransactionAwait(
    action: (MutableData) -> Transaction.Result,
    onSuccess: () -> Unit = {}
): Unit = suspendCancellableCoroutine { coroutine ->
    this.runTransaction(object: Transaction.Handler {
        override fun doTransaction(currentData: MutableData): Transaction.Result {
            return action(currentData)
        }

        override fun onComplete(
            error: DatabaseError?,
            committed: Boolean,
            currentData: DataSnapshot?
        ) {
            Log.d("runTransactionAwait", "onComplete: $error, $committed, $currentData")
            if (error != null) coroutine.resumeWithException(RuntimeException(error.message))

            if (committed.not()) coroutine.resumeWithException(RuntimeException("Transaction failed"))

            onSuccess()

            coroutine.resume(Unit)
        }
    })
}