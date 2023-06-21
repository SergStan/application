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

    override suspend fun insertBoot(boot: Boot): Boolean {
        bootDao.insert(boot)
        return true
    }


}