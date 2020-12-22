package com.bnikolov.java2daysdemo.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bnikolov.java2daysdemo.databinding.ActivityMainBinding
import com.bnikolov.java2daysdemo.viewmodel.RepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repositoryViewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Handler(Looper.getMainLooper()).postDelayed({
//            fetchRepos()
//        }, 10000L)
    }

    private fun fetchRepos() {
        Handler(Looper.getMainLooper()).postDelayed({
            Log.e("MY_TEST_LOG", "Starting repos fetch")
            repositoryViewModel.getRepositories()
            fetchRepos()
        }, 10000L)
    }
}