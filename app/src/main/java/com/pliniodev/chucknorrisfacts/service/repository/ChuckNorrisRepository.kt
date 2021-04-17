package com.pliniodev.chucknorrisfacts.service.repository

import com.pliniodev.chucknorrisfacts.service.model.FactModel
import com.pliniodev.chucknorrisfacts.service.model.FactsResultModel
import com.pliniodev.chucknorrisfacts.service.utils.AppResult

interface ChuckNorrisRepository {
    suspend fun getFreeQuery(query:String) : AppResult<FactsResultModel>
}