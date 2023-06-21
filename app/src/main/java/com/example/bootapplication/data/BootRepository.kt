package com.example.bootapplication.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BootRepository(
    private val bootDao: BootDao,
    private val ioCoroutineDispatcher: CoroutineDispatcher
) : AppRepository {

    override suspend fun getAll(): BootResult {
        return withContext(ioCoroutineDispatcher) {
            try {
                val result = bootDao.getAll()
                BootResult.Content(result)
            } catch (throwable: Throwable) {
                BootResult.Error(throwable)
            }
        }
    }

    override suspend fun insertBoot(): Boolean {
        return when (val countResult = getCount()) {
            is CountResult.Content -> {
                bootDao.insert(Boot(countResult.count + 1L))
                true
            }

            is CountResult.Error -> {
                false
            }
        }
    }

    override suspend fun getCount(): CountResult {
        return withContext(ioCoroutineDispatcher) {
            try {
                val result = bootDao.getCount()
                CountResult.Content(result)
            } catch (throwable: Throwable) {
                CountResult.Error(throwable)
            }
        }
    }
}