package com.firebase.newsapp
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.TestWorkerBuilder
import com.google.common.base.CharMatcher.`is`
import domain.workers.EmailVerificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import domain.workers.EmailVerificationWorker as EmailVer

@RunWith(AndroidJUnit4::class)
class WorkerTesting {
    private lateinit var context:Context
    private lateinit var executor: Executor
    val testScope = CoroutineScope(Dispatchers.IO)

@Before
fun setUpWorkerTest(){
    context = ApplicationProvider.getApplicationContext()
    executor = Executors.newSingleThreadExecutor()
}



    @Test
    fun testing_worker_expected_result_success() {
        testScope.launch {
          val worker = TestListenableWorkerBuilder<EmailVerificationWorker>(
              context = context
          ).build()
            worker.doWork()
        }
    }



}