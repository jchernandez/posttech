package com.android.rojox.posttechhold.di

import android.content.Context
import androidx.room.Room
import com.android.rojox.core.local.DbClientImpl
import com.android.rojox.core.local.LocalDb
import com.android.rojox.core.local.db.impl.DbClient
import com.android.rojox.core.remote.PostService
import com.android.rojox.core.remote.impl.PostServiceImpl
import com.android.rojox.core.remote.impl.RemoteClientImpl
import com.android.rojox.core.repository.PostRepository
import com.android.rojox.core.repository.PostRepositoryImpl
import com.android.rojox.posttechhold.viewmodel.PostViewModelImpl
import org.kodein.di.*


class AppBusiness(private val context: Context) {

    private val diContainer = DI.lazy {
        importAll(
            post
        )
    }
    private val post = DI.Module("post") {

        bind<LocalDb>() with singleton {
            Room.databaseBuilder(this@AppBusiness.context, LocalDb::class.java, "post_db")
                .build()
        }
        bind<DbClient>() with singleton {
            val localDb = instance<LocalDb>()
            DbClientImpl(localDb.postDao)
        }
        bind<PostService>() with singleton {
            PostServiceImpl(RemoteClientImpl())
        }
        bind<PostRepository>() with singleton {
            PostRepositoryImpl(instance(), instance())
        }
    }

    fun getPostViewModel() = diContainer.direct.newInstance {
        PostViewModelImpl(instance())
    }
}