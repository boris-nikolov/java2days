package com.bnikolov.java2daysdemo.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bnikolov.java2daysdemo.data.util.RealmLiveData
import com.bnikolov.java2daysdemo.db.model.RepositoryRealm
import com.bnikolov.java2daysdemo.extension.asLiveData
import com.bnikolov.java2daysdemo.network.model.Repository
import io.realm.Realm
import javax.inject.Inject

class LocalRepositoryDataSource @Inject constructor() {

    fun getRepositories(): LiveData<List<RepositoryRealm>> {
        return Transformations.map(getLocalRepositories()) {
            it as List<RepositoryRealm>
        }
    }

    private fun getLocalRepositories(): RealmLiveData<RepositoryRealm> {
        Log.e("MY_TEST_LOG", "Taking repositories from local source")
        return Realm.getDefaultInstance().where(RepositoryRealm::class.java).findAllAsync()
            .asLiveData()
    }

    fun saveRepositories(repositories: List<Repository>?) {
        Log.e("MY_TEST_LOG", "Saving ${repositories?.size ?: 0} repositories")
        Realm.getDefaultInstance().executeTransactionAsync {
            for (repository in repositories ?: return@executeTransactionAsync) {
                it.copyToRealmOrUpdate(repository.toLocalModel())
            }
        }
    }
}